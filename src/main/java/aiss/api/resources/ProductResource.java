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

import aiss.api.resources.comparators.ComparatorIdProduct;
import aiss.api.resources.comparators.ComparatorNameProduct;
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
	public Collection<Product> getAll(@QueryParam("q") String query, @QueryParam("order") String order, 
			@QueryParam("limit") Integer limit, @QueryParam("offset") Integer offset)
	{
		List<Product> result = new ArrayList<Product>();
		for (Product product: repository.getAllProducts()) {
			
			if (query == null || product.getName().contains(query)
				|| product.getId().contains(query)
				) {
				result.add(product);
			}
			
			if (order != null) {
				if (order.equals("name")) {
					Collections.sort(result, new ComparatorNameProduct());
				} else if (order.equals("id")) {
					Collections.sort(result, new ComparatorIdProduct());
				} else {
					throw new BadRequestException("The order parameter must be 'name', 'id'.");
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
	
	public Response addProduct(@Context UriInfo uriInfo, Product product) {
		if (product.getId() == null || "".equals(product.getId()))
			throw new BadRequestException("The id of the product must not be null");
		
		if (product.getName()==null)
			throw new BadRequestException("The name of the product must not be null");
		
		if (product.getPrice()==null)
			throw new BadRequestException("The price of the product must not be null");
		
		if (product.getAvailability()==null)
			throw new BadRequestException("The availability of the product must not be null");
		
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
		
		// Update id
		if (product.getId()!=null)
			oldproduct.setId(product.getId());				
		
		// Update name
		if (product.getName()!=null)
			oldproduct.setName(product.getName());
		
		// Update price
		if (product.getPrice()!=null)
			oldproduct.setPrice(product.getPrice());
		
		// Update Availability
		if (product.getAvailability()!=null)
			oldproduct.setAvailability(product.getAvailability());
		
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