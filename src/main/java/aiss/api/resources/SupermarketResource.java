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





@Path("/supermarkets")
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
		for (Supermarket Supermarket: repository.getAllSupermarkets()) {
			
			if (name == null || Supermarket.getName().equals(name)) {	// filtrado del nombre
				if (isEmpty == null 	// filtrado de supermercados vacías
					|| (isEmpty && (Supermarket.getSongs() == null || Supermarket.getSongs().size() == 0))
					|| (!isEmpty && (Supermarket.getSongs() != null && Supermarket.getSongs().size() > 0))) {
					
					result.add(Supermarket);
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
			throw new NotFoundException("The Supermarket with id="+ id +" was not found");			
		}
		
		return list;
	}
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response addSupermarket(@Context UriInfo uriInfo, Supermarket Supermarket) {
		if (Supermarket.getName() == null || "".equals(Supermarket.getName()))
			throw new BadRequestException("The name of the Supermarket must not be null");
		
		if (Supermarket.getSongs()!=null)
			throw new BadRequestException("The songs property is not editable.");

		repository.addSupermarket(Supermarket);

		// Builds the response. Returns the Supermarket the has just been added.
		UriBuilder ub = uriInfo.getAbsolutePathBuilder().path(this.getClass(), "get");
		URI uri = ub.build(Supermarket.getId());
		ResponseBuilder resp = Response.created(uri);
		resp.entity(Supermarket);			
		return resp.build();
	}

	
	@PUT
	@Consumes("application/json")
	public Response updateSupermarket(Supermarket Supermarket) {
		Supermarket oldSupermarket = repository.getSupermarket(Supermarket.getId());
		if (oldSupermarket == null) {
			throw new NotFoundException("The Supermarket with id="+ Supermarket.getId() +" was not found");			
		}
		
		if (Supermarket.getSongs()!=null)
			throw new BadRequestException("The songs property is not editable.");
		
		// Update name
		if (Supermarket.getName()!=null)
			oldSupermarket.setName(Supermarket.getName());
		
		// Update description
		if (Supermarket.getDescription()!=null)
			oldSupermarket.setDescription(Supermarket.getDescription());
		
		return Response.noContent().build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response removeSupermarket(@PathParam("id") String id) {
		Supermarket toberemoved=repository.getSupermarket(id);
		if (toberemoved == null)
			throw new NotFoundException("The Supermarket with id="+ id +" was not found");
		else
			repository.deleteSupermarket(id);
		
		return Response.noContent().build();
	}
	
	
	@POST	
	@Path("/{SupermarketId}/{songId}")
	@Consumes("text/plain")
	@Produces("application/json")
	public Response addSong(@Context UriInfo uriInfo,@PathParam("SupermarketId") String SupermarketId, @PathParam("songId") String songId)
	{				
		
		Supermarket Supermarket = repository.getSupermarket(SupermarketId);
		Product song = repository.getSong(songId);
		
		if (Supermarket==null)
			throw new NotFoundException("The Supermarket with id=" + SupermarketId + " was not found");
		
		if (song == null)
			throw new NotFoundException("The song with id=" + songId + " was not found");
		
		if (Supermarket.getSong(songId)!=null)
			throw new BadRequestException("The song is already included in the Supermarket.");
			
		repository.addSong(SupermarketId, songId);		

		// Builds the response
		UriBuilder ub = uriInfo.getAbsolutePathBuilder().path(this.getClass(), "get");
		URI uri = ub.build(SupermarketId);
		ResponseBuilder resp = Response.created(uri);
		resp.entity(Supermarket);			
		return resp.build();
	}
	
	
	@DELETE
	@Path("/{SupermarketId}/{songId}")
	public Response removeSong(@PathParam("SupermarketId") String SupermarketId, @PathParam("songId") String songId) {
		Supermarket Supermarket = repository.getSupermarket(SupermarketId);
		Product song = repository.getSong(songId);
		
		if (Supermarket==null)
			throw new NotFoundException("The Supermarket with id=" + SupermarketId + " was not found");
		
		if (song == null)
			throw new NotFoundException("The song with id=" + songId + " was not found");
		
		
		repository.removeSong(SupermarketId, songId);		
		
		return Response.noContent().build();
	}
}