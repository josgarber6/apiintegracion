package aiss.api.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.time.LocalDate;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.jboss.resteasy.spi.BadRequestException;
import org.jboss.resteasy.spi.NotFoundException;

import aiss.api.resources.comparators.ComparatorIdOrder;
import aiss.api.resources.comparators.ComparatorIdOrderReversed;
import aiss.model.Order;
import aiss.model.Product;
import aiss.model.User;
import aiss.model.repository.MapMarketRepository;
import aiss.model.repository.MarketRepository;

@Path("/orders")
public class OrderResource {
	
	/* Singleton */
	private static OrderResource _instance = null;
	MarketRepository repository;
	
	private OrderResource() {
		repository=MapMarketRepository.getInstance();
	}
	
	public static OrderResource getInstance() 
	{
		if(_instance == null)
			_instance = new OrderResource();
		return _instance;
	}
	
	@GET
	@Produces("application/json")
	public Collection<Order> getAll(@QueryParam("order") String order, @QueryParam("isEmpty") Boolean isEmpty)
	{
		List<Order> result = new ArrayList<Order>();
		for (Order o: repository.getAllOrders()) {
				// filtrado del id
				if (isEmpty == null 	// filtrado de pedidos vacíos
					|| (isEmpty && (o.getProducts() == null || o.getProducts().size() == 0))
					|| (!isEmpty && (o.getProducts() != null && o.getProducts().size() > 0))) {
					
					result.add(o);
				}
					
			}
			
			if (order != null) {
				if (order.equals("id")) {
					Collections.sort(result, new ComparatorIdOrder());
				}
				else if (order.equals("-id")) {
					Collections.sort(result, new ComparatorIdOrderReversed());
				}
				else {
					throw new BadRequestException("The order parameter must be 'id' or '-id'.");
				}
			}
		return result;
	}
	
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Order get(@PathParam("id") String id)	{
		Order list = repository.getOrder(id);
		
		if (list == null) {
			throw new NotFoundException("The order with id="+ id +" was not found");			
		}
		return list;
	}
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response addOrder(@Context UriInfo uriInfo,
			@QueryParam("token") String token,
			Order order) {
		/*
		 * para que una nueva orden sea valida el token enviado en queryParam token
		 * y el token del user dado tienen que ser iguales.
		 * una nueva orden necesita tener los parametros market, shippingCosts, address, user(ya tiene que existir)
		 * todos los otros campos tienen que ser null. Los otros parametros
		 * se van rellenando cada uno por su cuenta, menos dateStart que se llena 
		 * automaticamente con la fecha actual de cuando se crea el objeto order.
		 */
		
		if (order.getIdMarket()==null || "".equals(order.getIdMarket()))
			throw new BadRequestException("The idMarket of the order must not be null");
		
		if(order.getAddress()==null || "".equals(order.getAddress()))
			throw new BadRequestException("The address of the order must not be null.");
		
		
		if (order.getProducts()==null)
			throw new BadRequestException("The products must not be null.");
		
		if(order.getUser()==null)
			throw new BadRequestException("The user must not be null");
		
		/*
		 * COMO EL USUARIO YA EXISTE SOLAMENTE TENGO QUE PREGUNTAR EN MEMORIA PARA QUE TRAIGA ESTE USUARIO CON TODOS SUS 
		 * DATOS Y COMPROBAR QUE LOS DATOS DE SU TOKEN Y EL TOKEN PASADO SON IGUALES. Y LUEGO LE ASIGNO ESE USUARIO AL PEDIDO.
		 */
		User orderUser = repository.getUser(order.getUser().getId());
		
		if( orderUser == null)
			throw new BadRequestException("The user with id="+ order.getUser().getId() +" was not found");
		
		if(!token.equals(orderUser.getToken()))
			throw new BadRequestException("The token is incorrect");
		
		order.setUser(orderUser);
		repository.addOrder(order);

		// Builds the response. Returns the order the has just been added.
		UriBuilder ub = uriInfo.getAbsolutePathBuilder().path(this.getClass(), "get");
		URI uri = ub.build(order.getId());
		ResponseBuilder resp = Response.created(uri);
		resp.entity(order);			
		return resp.build();
	}
	
	@PUT
	@Path("/delivered/{orderId}")
	public Response updateOrderDateDelivery(@PathParam("orderId") String orderId) {
		Order order = repository.getOrder(orderId);
		if (order == null) {
			throw new NotFoundException("The order with id="+ orderId +" was not found");			
		}
		else order.setDateDelivery(""+LocalDate.now());
		return Response.noContent().build();
	}
	
	@PUT
	@Consumes("application/json")
	public Response updateOrder(@QueryParam("token") String token, Order order) {
		/*
		 * se confirma que la orden pertenece al que hace el update comprobando que 
		 * el toke es el del usuario que tiene guardada la orden al crearla con addOrder.
		 */
		if(!token.equals(order.getUser().getToken()))
			throw new BadRequestException("The token is incorrect");
		
		Order oldorder = repository.getOrder(order.getId());
		if (oldorder == null) {
			throw new NotFoundException("The order with id="+ order.getId() +" was not found");			
		}
		
		/*
		 * los parametros que se pueden cambiar son address, market, shippingCosts y 
		 * products
		 */
		
		// Update address
		if (order.getAddress() != null)
			oldorder.setAddress(order.getAddress());
		
		// Update market
		if (order.getIdMarket() != null)
			oldorder.setIdMarket(order.getIdMarket());
		
		// Update shipping costs
		if (order.getShippingCosts() != null)
			oldorder.setShippingCosts(""+order.getShippingCosts());
		
		// Update products
		if (order.getProducts() != null)
			oldorder.setProducts(order.getProducts());
		
		return Response.noContent().build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response removeOrder(@PathParam("id") String id, 
			@QueryParam("token") String token) {
		Order toberemoved = repository.getOrder(id);
		/*
		 * se confirma que la orden pertenece al que hace el delete comprobando que 
		 * el token es el del usuario que tiene guardada la orden al crearla con addOrder.
		 */
		if (toberemoved == null)
			throw new NotFoundException("The order with id="+ id +" was not found");
		else if(!token.equals(toberemoved.getUser().getToken())) 
			throw new BadRequestException("The token is incorrect");
		else
			repository.deleteOrder(id);
		
		return Response.noContent().build();
	}
	
	@POST	
	@Path("/{orderId}/{productId}")
	@Consumes("text/plain")
	@Produces("application/json")
	public Response addProduct(@Context UriInfo uriInfo,
			@PathParam("orderId") String orderId, 
			@PathParam("productId") String productId,
			@QueryParam("token") String token)
	{				
		
		Order order = repository.getOrder(orderId);
		Product product = repository.getProduct(productId);
		
		if (order == null)
			throw new NotFoundException("The order with id=" + orderId + " was not found");
		/*
		 * se confirma que la orden pertenece al que hace el addProduct comprobando que 
		 * el token es el del usuario que tiene guardada la orden al crearla con addOrder.
		 */
		if(!token.equals(order.getUser().getToken())) 
			throw new BadRequestException("The token is incorrect");
		
		if (product == null)
			throw new NotFoundException("The product with id=" + productId + " was not found");
		
		if (order.getProduct(productId) != null)
			throw new BadRequestException("The product is already included in the order.");
			
		repository.addProductToOrder(orderId, productId);

		// Builds the response
		UriBuilder ub = uriInfo.getAbsolutePathBuilder().path(this.getClass(), "get");
		URI uri = ub.build(orderId);
		ResponseBuilder resp = Response.created(uri);
		resp.entity(order);			
		return resp.build();
	}
	
	@DELETE
	@Path("/{orderId}/{productId}")
	public Response removeProduct(@PathParam("orderId") String orderId, 
			@PathParam("productId") String productId,
			@QueryParam("token") String token) {
		Order order = repository.getOrder(orderId);
		Product product = repository.getProduct(productId);
		
		if (order == null)
			throw new NotFoundException("The order with id=" + orderId + " was not found");
		
		/*
		 * se confirma que la orden pertenece al que hace el removeProduct comprobando que 
		 * el token es el del usuario que tiene guardada la orden al crearla con addOrder.
		 */
		if(!token.equals(order.getUser().getToken())) 
			throw new BadRequestException("The token is incorrect");
		
		if (product == null)
			throw new NotFoundException("The product with id=" + productId + " was not found");
		
		
		repository.removeProductOfOrder(orderId, productId);		
		
		return Response.noContent().build();
	}

}
