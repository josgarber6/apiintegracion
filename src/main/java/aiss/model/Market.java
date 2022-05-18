package aiss.model;

import java.util.List;

import aiss.model.repository.MapMarketRepository;

public class Market {

	private String id;
	private String name;
	private String description;
	
	public Market() {
		
	}
	
	/**
	 * Obtain a instance {@code Market} from a name and a description of the market.
	 * @param name: String, nombre del almacen.
	 * @param description: String, descripcion del almacen.
	 */
	public Market(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<Product> getProducts() {
		return MapMarketRepository.getInstance().getAllProductsByMarket(this.id);
	}
	
	public Product getProduct(String productId) {
		if (getProducts() == null) return null;
		Product product = null;
		for(Product p: getProducts()) {
			if (p.getId().equals(productId)) {
				product = p;
				break;
			}
		}
		return product;
	}
	
	public List<Order> getOrders() {
		return MapMarketRepository.getInstance().getAllOrdersByMarket(this.id);
	}
	
	public Order getOrder(String id) {
		if (getOrders() == null) return null;
		Order order = null;
		for (Order o: getOrders()) {
			if (o.getId().equals(id)) {
				order = o;
				break;
			}
		}
		return order;
	}
}
