package aiss.api.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.jboss.resteasy.spi.BadRequestException;
import org.jboss.resteasy.spi.NotFoundException;

import aiss.api.resources.comparators.ComparatorAlbumSong;
import aiss.api.resources.comparators.ComparatorArtistSong;
import aiss.api.resources.comparators.ComparatorYearSong;
import aiss.model.Product;
import aiss.model.repository.SupermarketRepository;



@Path("/products")
public class ProductResource {

	public static ProductResource _instance=null;
	SupermarketRepository repository;
	
	private ProductResource(){
		repository=MapPlaylistRepository.getInstance();
	}
	
	public static ProductResource getInstance()
	{
		if(_instance==null)
			_instance=new ProductResource();
		return _instance; 
	}
	
	@GET
	@Produces("application/json")
	public Collection<Product> getAll(@QueryParam("q") String query, @QueryParam("order") String order, 
			@QueryParam("limit") Integer limit, @QueryParam("offset") Integer offset)
	{
		List<Product> result = new ArrayList<Product>();
		
		for (Product song: repository.getAllSongs()) {
			
			if (query == null || song.getTitle().contains(query)
				|| song.getAlbum().contains(query)
				|| song.getArtist().contains(query)) {
				result.add(song);
			}
			
			if (order != null) {
				if (order.equals("album")) {
					Collections.sort(result, new ComparatorAlbumSong());
				} else if (order.equals("artist")) {
					Collections.sort(result, new ComparatorArtistSong());
				} else if (order.equals("year")) {
					Collections.sort(result, new ComparatorYearSong());
				} else {
					throw new BadRequestException("The order parameter must be 'album', 'artist' or 'year'.");
				}
			}
			
		}
		
		if (limit == null) {
			return result;
		}
		else {
			if (offset == null) {
				result = result.stream().limit(limit).collect(Collectors.toList());
			} else {
				result = result.subList(offset, result.size()).stream().limit(limit).collect(Collectors.toList());
			}
		}
		
		return result;
		
	}
	
	
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Product get(@PathParam("id") String songId)
	{
		Product song = repository.getProduct(songId);
		
		if (song == null) {
			throw new NotFoundException("The song with id="+ songId +" was not found");			
		}
		
		return song;
	}
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response addSong(@Context UriInfo uriInfo, Product song) {
		if (song.getTitle() == null || "".equals(song.getTitle()))
			throw new BadRequestException("The name of the song must not be null");
		
		if (song.getArtist()==null)
			throw new BadRequestException("The artist of the song must not be null");
		
		if (song.getAlbum()==null)
			throw new BadRequestException("The album of the song must not be null");
		
		if (song.getYear()==null)
			throw new BadRequestException("The year of the song must not be null");
		
		repository.addSong(song);

		// Builds the response. Returns the song the has just been added.
		UriBuilder ub = uriInfo.getAbsolutePathBuilder().path(this.getClass(), "get");
		URI uri = ub.build(song.getId());
		ResponseBuilder resp = Response.created(uri);
		resp.entity(song);			
		return resp.build();
	}
	
	
	@PUT
	@Consumes("application/json")
	public Response updateSong(Product song) {
		Product oldSong = repository.getSong(song.getId());
		if (oldSong == null) {
			throw new NotFoundException("The song with id=" + song.getId() + " was not found");
		}
		
		// Update title
		if (song.getTitle()!=null)
			oldSong.setTitle(song.getTitle());
		
		// Update album
		if (song.getAlbum()!=null)
			oldSong.setAlbum(song.getAlbum());
		
		// Update artist
		if (song.getArtist()!=null)
			oldSong.setArtist(song.getArtist());
		
		// Update year
		if (song.getYear()!=null)
			oldSong.setYear(song.getYear());
		
		return Response.noContent().build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response removeSong(@PathParam("id") String songId) {
		Product toberemoved = repository.getSong(songId);
		if (toberemoved==null)
			throw new NotFoundException("The song with id=" + songId + " was not found");
		else
			repository.deleteSong(songId);
		
		return Response.noContent().build();
	}
	
}
