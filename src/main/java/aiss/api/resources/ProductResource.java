package aiss.api.resources;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
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

import aiss.model.Product;
import aiss.model.repository.MapSupermarketRepository;
import aiss.model.repository.SupermarketRepository;



@Path("/products")
public class ProductResource {

	public static ProductResource _instance=null;
	SupermarketRepository repository;
	
	private ProductResource(){
		repository=MapSupermarketRepository.getInstance();
	}
	
	public static ProductResource getInstance()
	{
		if(_instance==null)
			_instance=new ProductResource();
		return _instance; 
	}
	
	@GET
	@Produces("application/json")
	public Collection<Product> getAll(@QueryParam("q") String query,
			@QueryParam("qAux") String queryAux, 
			@QueryParam("order") String order, 
			@QueryParam("limit") Integer limit, @QueryParam("offset") Integer offset)
	{
		List<Product> result = new ArrayList<Product>();
		
		for (Product product: repository.getAllProducts()) {
			
			Boolean Max_price_rating_quantity = ("price".equals(query) && product.getPrice() > Integer.valueOf(queryAux)) ||
					("rating".equals(query) && product.getRating() > Integer.valueOf(queryAux)) || 
					("quantity".equals(query) && product.getQuantity() > Integer.valueOf(queryAux));
			
			Boolean Min_price_rating_quantity = ("-price".equals(query) && product.getPrice() < Integer.valueOf(queryAux)) ||
					("-rating".equals(query) && product.getRating() < Integer.valueOf(queryAux)) || 
					("-quantity".equals(query) && product.getQuantity() < Integer.valueOf(queryAux));
			
			if (query == null || product.getName().contains(query)
				|| product.getType().name().contains(query)
				|| Max_price_rating_quantity
				|| Min_price_rating_quantity) {
				result.add(product);
			}
			
			if (order != null) {
//				if (order.equals("album")) {
//					Collections.sort(result, new ComparatorAlbumproduct());
//				} else if (order.equals("artist")) {
//					Collections.sort(result, new ComparatorArtistproduct());
//				} else if (order.equals("year")) {
//					Collections.sort(result, new ComparatorYearproduct());
//				} else {
//					throw new BadRequestException("The order parameter must be 'album', 'artist' or 'year'.");
//				}
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
	public Product get(@PathParam("id") String productId)
	{
		Product product = repository.getProduct(productId);
		
		if (product == null) {
			throw new NotFoundException("The product with id="+ productId +" was not found");			
		}
		
		return product;
	}
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response addproduct(@Context UriInfo uriInfo, Product product) {
		if (product.getName() == null || "".equals(product.getName()))
			throw new BadRequestException("The name of the product must not be null");
		
		if (product.getPrice() == null || product.getPrice() <= 0)
			throw new BadRequestException("The price of the product must not be null or lesser or equals to zero");
		
		if (product.getRating() == null || product.getRating() <= 0)
			throw new BadRequestException("The rating of the product must not be null or lesser or equals to zero");
		
		if(product.getQuantity() == null || product.getPrice() <= 0)
			throw new BadRequestException("The quantity of the product must not be null or lesser or equals to zero");
		
		if (product.getExpirationDate() == null || product.getExpirationDate().isBefore(LocalDate.now()) || product.getExpirationDate().isEqual(LocalDate.now()))
			throw new BadRequestException("The expiration date of the prouduct must not be null or lesser or equal than the actual date");
		
		if (product.getType() == null)
			throw new BadRequestException("The type of the proudct must not be null");
		
		repository.addProduct(product);

		// Builds the response. Returns the product the has just been added.
		UriBuilder ub = uriInfo.getAbsolutePathBuilder().path(this.getClass(), "get");
		URI uri = ub.build(product.getId());
		ResponseBuilder resp = Response.created(uri);
		resp.entity(product);			
		return resp.build();
	}
	
	
	@PUT
	@Consumes("application/json")
	public Response updateproduct(Product product) {
		Product oldproduct = repository.getProduct(product.getId());
		if (oldproduct == null) {
			throw new NotFoundException("The product with id=" + product.getId() + " was not found");
		}
		
		// Update price
		if (product.getPrice() != null && product.getPrice() > 0)
			oldproduct.setPrice(""+product.getPrice());
		
		// Update rating
		if (product.getRating() != null && product.getRating() > 0)
			oldproduct.setRating(""+product.getRating());
		
		// Update quantity
		if (product.getQuantity() != null && product.getQuantity() > 0)
			oldproduct.setQuantity(""+product.getQuantity());
		
		// Update expirationDate
		if (product.getExpirationDate() != null || product.getExpirationDate().isAfter(LocalDate.now()))
			oldproduct.setExpirationDate(""+product.getExpirationDate());
		
		return Response.noContent().build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response removeproduct(@PathParam("id") String productId) {
		Product toberemoved = repository.getProduct(productId);
		if (toberemoved==null)
			throw new NotFoundException("The product with id=" + productId + " was not found");
		else
			repository.deleteProduct(productId);
		
		return Response.noContent().build();
	}
	
}
