package aiss.api.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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
import aiss.model.repository.MapSupermarketRepository;
import aiss.model.repository.SupermarketRepository;

@Path("/orders")
public class OrderResource {
	
	/* Singleton */
	private static OrderResource _instance=null;
	SupermarketRepository repository;
	
	private OrderResource() {
		repository=MapSupermarketRepository.getInstance();
	}
	
	public static OrderResource getInstance() {
		if(_instance==null)
			_instance=new OrderResource();
		return _instance;
	}
	
	@GET
	@Produces("application/json")
	public Collection<Order> getAll(@QueryParam("order") String order,
			 @QueryParam("id") String id, @QueryParam("isEmpty") Boolean isEmpty)
	{
		List<Order> result = new ArrayList<Order>();
		for (Order o: repository.getAllOrders()) {
			
			if (id == null || o.getId().equals(id)) {	// filtrado del id
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
	public Response addOrder(@Context UriInfo uriInfo, Order order) {
		if (order.getId() == null || "".equals(order.getId()))
			throw new BadRequestException("The name of the order must not be null");
		
		if (order.getProducts()!=null)
			throw new BadRequestException("The products property is not editable.");

		repository.addOrder(order);

		// Builds the response. Returns the order the has just been added.
		UriBuilder ub = uriInfo.getAbsolutePathBuilder().path(this.getClass(), "get");
		URI uri = ub.build(order.getId());
		ResponseBuilder resp = Response.created(uri);
		resp.entity(order);			
		return resp.build();
	}
	
	@PUT
	@Consumes("application/json")
	public Response updateOrder(Order order) {
		Order oldorder = repository.getOrder(order.getId());
		if (oldorder == null) {
			throw new NotFoundException("The order with id="+ order.getId() +" was not found");			
		}
		
			
		// Update address
		if (order.getAddress()!=null)
			oldorder.setAddress(order.getAddress());
		
		// Update date
		if (order.getDate()!=null)
			oldorder.setDate(order.getDate());
		
		// Update shipping costs
		if (order.getShippingCosts()!=null)
			oldorder.setShippingCosts(order.getShippingCosts());
		
		// Update products
		if (order.getProducts()!=null)
			oldorder.setProducts(order.getProducts());
		
		return Response.noContent().build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response removeOrder(@PathParam("id") String id) {
		Order toberemoved=repository.getOrder(id);
		if (toberemoved == null)
			throw new NotFoundException("The order with id="+ id +" was not found");
		else
			repository.deleteOrder(id);
		
		return Response.noContent().build();
	}
	
	@POST	
	@Path("/{orderId}/{productId}")
	@Consumes("text/plain")
	@Produces("application/json")
	public Response addProduct(@Context UriInfo uriInfo,@PathParam("orderId") String orderId, @PathParam("productId") String productId)
	{				
		
		Order order = repository.getOrder(orderId);
		Product product = repository.getProduct(productId);
		
		if (order==null)
			throw new NotFoundException("The order with id=" + orderId + " was not found");
		
		if (product == null)
			throw new NotFoundException("The product with id=" + productId + " was not found");
		
		if (order.getProduct(productId)!=null)
			throw new BadRequestException("The product is already included in the order.");
			
		repository.addProduct(orderId, productId);

		// Builds the response
		UriBuilder ub = uriInfo.getAbsolutePathBuilder().path(this.getClass(), "get");
		URI uri = ub.build(orderId);
		ResponseBuilder resp = Response.created(uri);
		resp.entity(order);			
		return resp.build();
	}
	
	@DELETE
	@Path("/{orderId}/{productId}")
	public Response removeproduct(@PathParam("orderId") String orderId, @PathParam("productId") String productId) {
		Order order = repository.getOrder(orderId);
		Product product = repository.getProduct(productId);
		
		if (order==null)
			throw new NotFoundException("The order with id=" + orderId + " was not found");
		
		if (product == null)
			throw new NotFoundException("The product with id=" + productId + " was not found");
		
		
		repository.removeProduct(orderId, productId);		
		
		return Response.noContent().build();
	}

}
