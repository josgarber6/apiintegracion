package aiss.api.resources;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.jboss.resteasy.spi.BadRequestException;

import aiss.model.Order;
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
					|| (isEmpty && (o.getProducts() == null || o.getSongs().size() == 0))
					|| (!isEmpty && (o.getProducts() != null && o.getSongs().size() > 0))) {
					
					result.add(o);
				}
					
			}
			
			if (order != null) {
				if (order.equals("id")) {
					Collections.sort(result, new ComparatorIdOrder());
				}
				else if (order.equals("-id")) {
					Collections.sort(result, new ComparatorNameOrderReversed());
				}
				else {
					throw new BadRequestException("The order parameter must be 'name' or '-name'.");
				}
			}
			
		}
		return result;
	}

}
