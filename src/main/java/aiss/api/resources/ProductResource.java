 package aiss.api.resources;

import java.net.URI;
import java.time.LocalDate;
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
import aiss.api.resources.comparators.ComparatorPriceProduct;
import aiss.api.resources.comparators.ComparatorPriceProductReversed;
import aiss.api.resources.comparators.ComparatorQuantityProduct;
import aiss.api.resources.comparators.ComparatorQuantityProductReversed;
import aiss.api.resources.comparators.ComparatorRatingProduct;
import aiss.api.resources.comparators.ComparatorRatingProductReversed;
import aiss.model.Product;
import aiss.model.repository.MapMarketRepository;
import aiss.model.repository.MarketRepository;



@Path("/products")
public class ProductResource {

	private static ProductResource _instance=null;
	MarketRepository repository;
	
	private ProductResource(){
		repository=MapMarketRepository.getInstance();
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
			@QueryParam("qAux") Integer queryAux,
			@QueryParam("pExpired") Boolean queryExpiration,
			@QueryParam("order") String order, 
			@QueryParam("limit") Integer limit, 
			@QueryParam("offset") Integer offset)
	{
		List<Product> products = new ArrayList<Product>();
		
		for (Product product: repository.getAllProducts()) {
			
			Boolean Max_price_rating_quantity = queryAux != null && ("price".equals(query) && Double.valueOf(product.getPrice()) >= queryAux) ||
					("rating".equals(query) && Integer.valueOf(product.getRating()) >= queryAux) || 
					("quantity".equals(query) && Integer.valueOf(product.getQuantity()) >= queryAux);
			
			Boolean Min_price_rating_quantity = queryAux != null && ("-price".equals(query) && Double.valueOf(product.getPrice()) <= queryAux) ||
					("-rating".equals(query) && Integer.valueOf(product.getRating()) <= queryAux) || 
					("-quantity".equals(query) && Integer.valueOf(product.getQuantity()) <= queryAux);
			
//			SE PUEDEN LLEVAR A CABO TODAS LAS COMBINACIONES POSIBLES DEL FILTRO NO ENUMERADO Y ENUMERADO
			 
			if (query == null && queryExpiration == null
				|| query == null  && (queryExpiration && LocalDate.parse(product.getExpirationDate()).isAfter(LocalDate.now())
							|| !queryExpiration && LocalDate.parse(product.getExpirationDate()).isBefore(LocalDate.now()))
				|| product.getName().contains(query) && queryExpiration == null
				|| product.getType().name().contains(query) && queryExpiration == null
				|| Max_price_rating_quantity && queryExpiration == null
				|| Min_price_rating_quantity && queryExpiration == null
				|| product.getName().contains(query) && (queryExpiration && LocalDate.parse(product.getExpirationDate()).isAfter(LocalDate.now())
						|| !queryExpiration && LocalDate.parse(product.getExpirationDate()).isBefore(LocalDate.now())) 
				|| product.getType().name().contains(query)  && (queryExpiration && LocalDate.parse(product.getExpirationDate()).isAfter(LocalDate.now())
						|| !queryExpiration && LocalDate.parse(product.getExpirationDate()).isBefore(LocalDate.now()))
				|| Max_price_rating_quantity  && (queryExpiration && LocalDate.parse(product.getExpirationDate()).isAfter(LocalDate.now())
						|| !queryExpiration && LocalDate.parse(product.getExpirationDate()).isBefore(LocalDate.now()))
				|| Min_price_rating_quantity  && (queryExpiration && LocalDate.parse(product.getExpirationDate()).isAfter(LocalDate.now())
						|| !queryExpiration && LocalDate.parse(product.getExpirationDate()).isBefore(LocalDate.now()))) {
				products.add(product);
			}
			
			
			if (order != null) {
				if (order.equals("name")) {
					Collections.sort(products, new ComparatorNameProduct());
				} else if (order.equals("id")) {
					Collections.sort(products, new ComparatorIdProduct());
				} else if (order.equals("price")) {
					Collections.sort(products, new ComparatorPriceProduct());
				} else if (order.equals("-price")) {
					Collections.sort(products, new ComparatorPriceProductReversed());
				} else if (order.equals("quantity")) {
					Collections.sort(products, new ComparatorQuantityProduct());
				} else if (order.equals("-quantity")) {
					Collections.sort(products, new ComparatorQuantityProductReversed());
				} else if (order.equals("rating")) {
					Collections.sort(products, new ComparatorRatingProduct());
				} else if (order.equals("-rating")) {
					Collections.sort(products, new ComparatorRatingProductReversed());
				} else {
					throw new BadRequestException("The order parameter must be id,idMarket,name,price,price,rating,quantity,expirationDate,type,availability");
				}
			}
			
		}
		
		if (limit != null && offset == null) {
			products = products.stream().limit(limit).collect(Collectors.toList());
		}
		else if(limit != null && offset != null){
			products = products.subList(offset, products.size()).stream().limit(limit).collect(Collectors.toList());
		}
		
		return products;
		
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
		
		if (product.getName() == null || "".equals(product.getName()))
			throw new BadRequestException("The name of the product must not be null");
		if (product.getRating() == null || Integer.valueOf(product.getRating()) <= 0)
			throw new BadRequestException("The rating of the product must not be null or lesser or equals to zero");
		if (product.getPrice() == null || Double.valueOf(product.getPrice()) <= 0)
			throw new BadRequestException("The price of the product must not be null or lesser or equals to zero");
		
		if(product.getQuantity() == null || Integer.valueOf(product.getQuantity()) < 0)
			throw new BadRequestException("The quantity of the product must not be null or lesser  to zero");
		
		if (product.getExpirationDate() == null || LocalDate.parse(product.getExpirationDate()).isBefore(LocalDate.now()) 
				|| LocalDate.parse(product.getExpirationDate()).isEqual(LocalDate.now()))
			throw new BadRequestException("The expiration date of the prouduct must not be null or lesser or equal than the actual date");
		
 		if(product.getExpirationDate().equals("null")) {
			throw new BadRequestException("The expiration date of the prouduct can't be null");
		}
		
		if (product.getType() == null)
			throw new BadRequestException("The type of the prouduct must not be null");
		
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
	public Response updateProduct(Product product) {
		Product oldproduct = repository.getProduct(product.getId());
		if (oldproduct == null) {
			throw new NotFoundException("The product with id=" + product.getId() + " was not found");
		}
		
		// Update price
		if (product.getPrice() != null && Double.valueOf(product.getPrice()) > 0)
			oldproduct.setPrice(""+product.getPrice());
		
		// Update rating
		if (product.getRating() != null && Integer.valueOf(product.getRating()) > 0)
			oldproduct.setRating(""+product.getRating());
		
		// Update quantity
		if (product.getQuantity() != null && Integer.valueOf(product.getQuantity()) > 0)
			oldproduct.setQuantity(""+product.getQuantity());
		
		// Update expirationDate
		if (product.getExpirationDate() != null || LocalDate.parse(product.getExpirationDate()).isAfter(LocalDate.now()))
			oldproduct.setExpirationDate(""+product.getExpirationDate());
		if(product.getExpirationDate().equals("null")) {
			throw new BadRequestException("The expiration date of the prouduct can't be null");
		}
		return Response.noContent().build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response removeProduct(@PathParam("id") String productId) {
		Product toberemoved = repository.getProduct(productId);
		if (toberemoved==null)
			throw new NotFoundException("The product with id=" + productId + " was not found");
		else
			repository.deleteProduct(productId);
		
		return Response.noContent().build();
	}
	
}
