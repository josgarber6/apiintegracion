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
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.jboss.resteasy.spi.BadRequestException;
import org.jboss.resteasy.spi.NotFoundException;

import aiss.api.resources.comparators.ComparatorIdUser;
import aiss.api.resources.comparators.ComparatorNameUser;
import aiss.api.resources.comparators.ComparatorNameUserReversed;
import aiss.model.Order;
import aiss.model.User;
import aiss.model.repository.MapMarketRepository;
import aiss.model.repository.MarketRepository;

@Path("/users")
public class UserResource {
	
	public static UserResource _instance = null;
	MarketRepository repository;
	
	private UserResource() {
		repository = MapMarketRepository.getInstance();
	}
	
	public static UserResource getInstance() 
	{
		if(_instance == null) {
			_instance = new UserResource();
		}
		return _instance;
	}
	
	@GET
	@Produces("application/json")
	public Collection<User> getAll(@QueryParam("order") String order, 
			@QueryParam("limit") Integer limit, 
			@QueryParam("offset") Integer offset,
			@QueryParam("name") String name)
	{
		List<User> users = new ArrayList<>();
		
		for(User user: repository.getAllUsers()) {
			if (name == null || user.getName().equals(name)) {
				users.add(user);
			}
			
			if(order != null) {
				if(order.equals("name")) {
					Collections.sort(users, new ComparatorNameUser());
				} else if(order.equals("-name")) {
					Collections.sort(users, new ComparatorNameUserReversed());
				} else if(order.equals("id")) {
					Collections.sort(users, new ComparatorIdUser());
		        } else {
					throw new BadRequestException("The order parameter must be 'name' , '-name' or 'id'.");
				}
			}
		}
		
		if(limit != null && offset != null) {
			users = users.subList(offset, users.size()).stream().limit(limit).collect(Collectors.toList());
		} else if(limit != null && offset == null) {
			users = users.stream().limit(limit).collect(Collectors.toList());
		}
		
		return users;
	}
	
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public User get(@PathParam("id") String id) {
		User user = repository.getUser(id);
		
		if(user == null) {
			throw new NotFoundException("The user with id=" + id + " was not found");			
		}
		
		return user;
	}
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response addUser(@Context UriInfo uriInfo, User user) {
		if(user.getName() == null || user.getName().equals("")) 
			throw new  BadRequestException("The name of the product must not be null");
		
		if(user.getEmail() == null || user.getEmail().equals(""))
			throw new  BadRequestException("The email of the product must not be null");
		
		if(user.getPassword() == null || user.getPassword().equals(""))
			throw new  BadRequestException("The password of the product must not be null");
		
		if(user.getAddress() == null || user.getAddress().equals(""))
				throw new  BadRequestException("The addres of the product must not be null");
		
		repository.addUser(user);
		
		// Builds the response. Returns the product the has just been added.
		UriBuilder ub = uriInfo.getAbsolutePathBuilder().path(this.getClass(), "get");
		URI uri = ub.build(user.getId());
		ResponseBuilder resp = Response.created(uri);
		resp.entity(user);			
		return resp.build();
	}
	
	 @PUT
	 @Consumes("application/json")
	 public Response updateUser(@QueryParam("token") String token, User user) {
		 if (!token.equals(user.getToken())) {
			 throw new BadRequestException("The token is incorrect");
		 }
		 
		 User oldUser = repository.getUser(user.getId());
		 
		 if(oldUser == null) {
			 throw new NotFoundException("The user with id=" + user.getId() + " was not found");
		 }
		 
		 if(user.getName() != null || !"".equals(user.getName()))
			 oldUser.setName(user.getName());
		 
		 if(user.getEmail() != null || !"".equals(user.getEmail()))
			 oldUser.setEmail(user.getEmail());
		 
		 if(user.getPassword() != null || !"".equals(user.getPassword()))
			 oldUser.setPassword(user.getPassword());
		 
		 if(user.getAddress() != null || !"".equals(user.getAddress()))
			 oldUser.setAddress(user.getAddress());
		 
		 return Response.noContent().build();
	 }
	 
	 @DELETE
	 @Path("/{id}")
	 public Response removeUser(@PathParam("id") String id,
			 					@QueryParam("token") String token) {
		 
		 if(!token.equals(repository.getUser(id).getToken())) {
			 throw new BadRequestException("The token is different.");
		 }
		 
		 User toBeRemoved = repository.getUser(id);
		 List<Order> toBeRemovedOrder = repository.getOrdersByUser(id);
		 
		 if(toBeRemoved == null) {
			  throw new NotFoundException("The userwith id=" + id + " was not found");
		 }else {
			 for(Order o : toBeRemovedOrder) {
				 repository.deleteOrder(o.getId());
			 }
		 	 repository.deleteUser(id);	
		 }
	 	 return Response.noContent().build();

			 
		 
		 
	 }
	 
	 @GET
	 @Path("/token/{id}")
	 public String getToken(@PathParam("id") String id,
			 @QueryParam("name") String name,
			 @QueryParam("password") String password) {
		 String token;
		 User user = repository.getUser(id);
		 
		 if(user == null) {
			 throw new NotFoundException("The user with id=" + id + " was not found");
		 } else if(name.equals(user.getName()) && password.equals(user.getPassword())) {
			 token = user.getToken();
		 }
		 else {
			 throw new BadRequestException("The name and password are incorrect.");
		 }
		 
		 return token;
	 }

}
