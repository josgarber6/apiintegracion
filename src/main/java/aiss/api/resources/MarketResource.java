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
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.jboss.resteasy.spi.BadRequestException;
import org.jboss.resteasy.spi.NotFoundException;

import aiss.api.resources.comparators.ComparatorIdMarket;
import aiss.api.resources.comparators.ComparatorIdMarketReversed;
import aiss.api.resources.comparators.ComparatorNameMarket;
import aiss.api.resources.comparators.ComparatorNameMarketReversed;
import aiss.model.Market;
import aiss.model.Order;
import aiss.model.Product;
import aiss.model.repository.MapMarketRepository;
import aiss.model.repository.MarketRepository;




@Path("/markets")
public class MarketResource {
	
	/* Singleton */
	private static MarketResource _instance=null;
	MarketRepository repository;
	
	private MarketResource() {
		repository=MapMarketRepository.getInstance();
	}
	
	public static MarketResource getInstance()
	{
		if(_instance==null)
				_instance = new MarketResource();
		return _instance;
	}
	

	@GET
	@Produces("application/json")
	public Collection<Market> getAll(@QueryParam("order") String order,
			 @QueryParam("name") String name, 
			 @QueryParam("isEmptyProducts") Boolean isEmptyProducts,
			 @QueryParam("isEmptyOrders") Boolean isEmptyOrders) {
		List<Market> result = new ArrayList<>();
		for (Market market: repository.getAllMarkets()) {
			if (name == null || market.getName().equals(name)) { // filtrado del nombre
				/*
				 * filtrado de supermercados vacíos:
				 * Estan todas las posibles combinaciones 0 = QueryParam = null y
				 * 1 = QueryParam != null 00, 01, 10, 11(como cada parametro puede ser true
				 * y false tiene que haber todas las combinaciones posibles)
				 */
				if (isEmptyProducts == null && isEmptyOrders == null // 00 null && null
					|| isEmptyProducts == null && (isEmptyOrders && (market.getOrders() == null || market.getOrders().isEmpty())) // 01 null  && true 
					|| isEmptyProducts == null && (!isEmptyOrders && (market.getOrders() != null || !market.getOrders().isEmpty())) // 01 null  && false 
					|| (isEmptyProducts && (market.getProducts() == null || market.getProducts().isEmpty())) && isEmptyOrders == null // 10 true  && null 
					|| (!isEmptyProducts && (market.getProducts() != null || !market.getProducts().isEmpty())) && isEmptyOrders == null // 10 false  && null 
					|| (!isEmptyProducts && (market.getProducts() != null || !market.getProducts().isEmpty())) // 11 false  && false
						&& (!isEmptyOrders && (market.getOrders() != null || !market.getOrders().isEmpty()))
					|| (!isEmptyProducts && (market.getProducts() != null || !market.getProducts().isEmpty())) // 11 false  && true 
						&& (isEmptyOrders && (market.getOrders() == null || market.getOrders().isEmpty()))
					|| (isEmptyProducts && (market.getProducts() == null || market.getProducts().isEmpty())) // 11 true  && false
						&& (!isEmptyOrders && (market.getOrders() != null || !market.getOrders().isEmpty()))
					|| (isEmptyProducts && (market.getProducts() == null || market.getProducts().isEmpty())) // 11 true && true 
						&& (isEmptyOrders && (market.getOrders() == null || market.getOrders().isEmpty()))) {
					result.add(market);
				}
					
			}
			
			if (order != null) {
				if (order.equals("name")) {
					Collections.sort(result, new ComparatorNameMarket());
				}
				else if (order.equals("-name")) {
					Collections.sort(result, new ComparatorNameMarketReversed());
				}
				else if(order.equals("id")) {
					Collections.sort(result, new ComparatorIdMarket());
				}
				else if(order.equals("-id")) {
					Collections.sort(result, new ComparatorIdMarketReversed());
				}
				else {
					throw new BadRequestException("The order parameter must be 'name' or '-name'.");
				}
			}
		}
		return result;
	}
	
	
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Market get(@PathParam("id") String id)	{
		Market list = repository.getMarket(id);
		
		if (list == null) {
			throw new NotFoundException("The Market with id=" + id + " was not found");			
		}
		
		return list;
	}
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response addMarket(@Context UriInfo uriInfo, Market market) {
		if (market.getName() == null || "".equals(market.getName()))
			throw new BadRequestException("The name of the Market must not be null.");
	
		repository.addMarket(market);

		// Builds the response. Returns the Market that has just been added.
		UriBuilder ub = uriInfo.getAbsolutePathBuilder().path(this.getClass(), "get");
		URI uri = ub.build(market.getId());
		ResponseBuilder resp = Response.created(uri);
		resp.entity(market);			
		return resp.build();
	}

	
	@PUT
	@Consumes("application/json")
	public Response updateMarket(Market market) {
		Market oldmarket = repository.getMarket(market.getId());
		if (oldmarket == null) {
			throw new NotFoundException("The Market with id= "+ market.getId() +" was not found");			
		}
		
		// Update name
		if (market.getName()!=null)
			oldmarket.setName(market.getName());
		
		// Update description
		if (market.getDescription()!=null)
			oldmarket.setDescription(market.getDescription());
		
		return Response.noContent().build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response removeMarket(@PathParam("id") String id) {
		Market toBeRemoved=repository.getMarket(id);
		Collection<Order> toBeRemovedOrders = repository.getAllOrdersByMarket(id);
		List<Product> toBeRemovedProducts = repository.getAllProductsByMarket(id);
		
		if(toBeRemoved == null) {
			  throw new NotFoundException("The Market with id=" + id + " was not found");
		 } else {
			 for(Order o : toBeRemovedOrders) {
				 repository.deleteOrder(o.getId());
			 }
			 
			 for(Product p : toBeRemovedProducts) {
				 repository.deleteProduct(p.getId());
			 }
			 
			 repository.deleteMarket(id);
		 }
		
		return Response.noContent().build();
	}
}