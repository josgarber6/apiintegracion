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

import aiss.api.resources.comparators.ComparatorNameMarket;
import aiss.api.resources.comparators.ComparatorNameMarketReversed;
import aiss.model.Market;
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
			 @QueryParam("name") String name, @QueryParam("isEmpty") Boolean isEmpty)
	{
		List<Market> result = new ArrayList<Market>();
		for (Market market: repository.getAllMarkets()) {
//			if (name == null || Market.getName().equals(name)) {	// filtrado del nombre
//				if (isEmpty == null 	// filtrado de supermercados vacías
//					|| (isEmpty && (Market.getSongs() == null || Market.getSongs().size() == 0))
//					|| (!isEmpty && (Market.getSongs() != null && Market.getSongs().size() > 0))) {
//					
//					result.add(market);
//				}
//					
//			}
			
			if (order != null) {
				if (order.equals("name")) {
					Collections.sort(result, new ComparatorNameMarket());
				}
				else if (order.equals("-name")) {
					Collections.sort(result, new ComparatorNameMarketReversed());
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
			throw new NotFoundException("The Market with id="+ id +" was not found");			
		}
		
		return list;
	}
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response addMarket(@Context UriInfo uriInfo, Market market) {
		if (market.getName() == null || "".equals(market.getName()))
			throw new BadRequestException("The name of the Market must not be null");
		
		if (market.getProducts()!=null)
			throw new BadRequestException("The products property is not editable.");

		repository.addMarket(market);

		// Builds the response. Returns the Market the has just been added.
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
			throw new NotFoundException("The Market with id="+ market.getId() +" was not found");			
		}
		
		if (market.getProducts()!=null)
			throw new BadRequestException("The products property is not editable.");
		
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
		Market toberemoved=repository.getMarket(id);
		if (toberemoved == null)
			throw new NotFoundException("The Market with id="+ id +" was not found");
		else
			repository.deleteMarket(id);
		
		return Response.noContent().build();
	}
	
	
	@POST	
	@Path("/{marketId}/{productId}")
	@Consumes("text/plain")
	@Produces("application/json")
	public Response addProduct(@Context UriInfo uriInfo,@PathParam("marketId") String MarketId, @PathParam("productId") String songId)
	{				
		
		Market market = repository.getMarket(MarketId);
		Product product = repository.getProduct(songId);
		
		if (market==null)
			throw new NotFoundException("The Market with id=" + MarketId + " was not found");
		
		if (product == null)
			throw new NotFoundException("The product with id=" + songId + " was not found");
		
		if (market.getProduct(songId)!=null)
			throw new BadRequestException("The product is already included in the Market.");
			
		repository.addProduct(MarketId, songId);		

		// Builds the response
		UriBuilder ub = uriInfo.getAbsolutePathBuilder().path(this.getClass(), "get");
		URI uri = ub.build(MarketId);
		ResponseBuilder resp = Response.created(uri);
		resp.entity(market);			
		return resp.build();
	}
	
	
	@DELETE
	@Path("/{marketId}/{productId}")
	public Response removeProduct(@PathParam("marketId") String MarketId, @PathParam("productId") String productId) {
		Market market = repository.getMarket(MarketId);
		Product product = repository.getProduct(productId);
		
		if (market==null)
			throw new NotFoundException("The Market with id=" + MarketId + " was not found.");
		
		if (product == null)
			throw new NotFoundException("The product with id=" + productId + " was not found.");
		
		
		repository.removeProduct(MarketId, productId);		
		
		return Response.noContent().build();
	}
}