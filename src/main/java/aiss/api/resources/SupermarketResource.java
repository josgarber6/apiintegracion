package aiss.api.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
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

import aiss.api.resources.comparators.ComparatorNameSupermarket;
import aiss.api.resources.comparators.ComparatorNameSupermarketReversed;
import aiss.model.Supermarket;
import aiss.model.Product;
import aiss.model.repository.MapSupermarketRepository;
import aiss.model.repository.SupermarketRepository;





@Path("/lists")
public class SupermarketResource {
	
	/* Singleton */
	private static SupermarketResource _instance=null;
	SupermarketRepository repository;
	
	private SupermarketResource() {
		repository=MapSupermarketRepository.getInstance();

	}
	
	public static SupermarketResource getInstance()
	{
		if(_instance==null)
				_instance=new SupermarketResource();
		return _instance;
	}
	

	@GET
	@Produces("application/json")
	public Collection<Supermarket> getAll(@QueryParam("order") String order,
			 @QueryParam("name") String name, @QueryParam("isEmpty") Boolean isEmpty)
	{
		List<Supermarket> result = new ArrayList<Supermarket>();
		for (Supermarket market: repository.getAllSupermarkets()) {
			
			if (name == null || market.getName().equals(name)) {	// filtrado del nombre
				if (isEmpty == null 	// filtrado de listas vacías
					|| (isEmpty && (market.getProducts() == null || market.getProducts().size() == 0))
					|| (!isEmpty && (market.getProducts() != null && market.getProducts().size() > 0))) {
					
					result.add(market);
				}
					
			}
			
			if (order != null) {
				if (order.equals("name")) {
					Collections.sort(result, new ComparatorNameSupermarket());
				}
				else if (order.equals("-name")) {
					Collections.sort(result, new ComparatorNameSupermarketReversed());
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
	public Supermarket get(@PathParam("id") String id)	{
		Supermarket list = repository.getSupermarket(id);
		
		if (list == null) {
			throw new NotFoundException("The supermarket with id="+ id +" was not found");			
		}
		
		return list;
	}
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response addSupermarket(@Context UriInfo uriInfo, Supermarket playlist) {
		if (playlist.getName() == null || "".equals(playlist.getName()))
			throw new BadRequestException("The name of the supermarket must not be null");
		
		if (playlist.getProducts()!=null)
			throw new BadRequestException("The products property is not editable.");

		repository.addSupermarket(playlist);

		// Builds the response. Returns the supermarket the has just been added.
		UriBuilder ub = uriInfo.getAbsolutePathBuilder().path(this.getClass(), "get");
		URI uri = ub.build(playlist.getId());
		ResponseBuilder resp = Response.created(uri);
		resp.entity(playlist);			
		return resp.build();
	}

	
	@PUT
	@Consumes("application/json")
	public Response updateSupermarket(Supermarket market) {
		Supermarket oldplaylist = repository.getSupermarket(market.getId());
		if (oldplaylist == null) {
			throw new NotFoundException("The supermarket with id="+ market.getId() +" was not found");			
		}
		
		if (market.getProducts()!=null)
			throw new BadRequestException("The products property is not editable.");
		
		// Update name
		if (market.getName()!=null)
			oldplaylist.setName(market.getName());
		
		// Update description
		if (market.getDescription()!=null)
			oldplaylist.setDescription(market.getDescription());
		
		return Response.noContent().build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response removeSupermarket(@PathParam("id") String id) {
		Supermarket toberemoved=repository.getSupermarket(id);
		if (toberemoved == null)
			throw new NotFoundException("The supermarket with id="+ id +" was not found");
		else
			repository.deleteSupermarket(id);
		
		return Response.noContent().build();
	}
	
	
	@POST	
	@Path("/{supermarketId}/{productId}")
	@Consumes("text/plain")
	@Produces("application/json")
	public Response addProduct(@Context UriInfo uriInfo,@PathParam("supermarketId") String supermarketId, @PathParam("productId") String songId)
	{				
		
		Supermarket market = repository.getSupermarket(supermarketId);
		Product product = repository.getProduct(songId);
		
		if (market==null)
			throw new NotFoundException("The supermarket with id=" + supermarketId + " was not found");
		
		if (product == null)
			throw new NotFoundException("The product with id=" + songId + " was not found");
		
		if (market.getProduct(songId)!=null)
			throw new BadRequestException("The product is already included in the supermarket.");
			
		repository.addProduct(supermarketId, songId);		

		// Builds the response
		UriBuilder ub = uriInfo.getAbsolutePathBuilder().path(this.getClass(), "get");
		URI uri = ub.build(supermarketId);
		ResponseBuilder resp = Response.created(uri);
		resp.entity(market);			
		return resp.build();
	}
	
	
	@DELETE
	@Path("/{supermarketId}/{productId}")
	public Response removeProduct(@PathParam("supermarketId") String supermarketId, @PathParam("productId") String productId) {
		Supermarket market = repository.getSupermarket(supermarketId);
		Product product = repository.getProduct(productId);
		
		if (market==null)
			throw new NotFoundException("The supermarket with id=" + supermarketId + " was not found.");
		
		if (product == null)
			throw new NotFoundException("The product with id=" + productId + " was not found.");
		
		
		repository.removeProduct(supermarketId, productId);		
		
		return Response.noContent().build();
	}
}