package aiss.model;

import java.time.LocalDate;
import java.util.*;

public class Order {
	
	private String id;
	private String idMarket;
	private String startDate;
	private String deliveryDate;
	private String address;
	private String shippingCosts;
	private User user;
	private List<Product> products;

	public Order() {
		this.startDate = ""+LocalDate.now();
	}
	
	/**
	 * Obtain a instance {@code Order} from a address, shippingCosts, idMarket.
	 * @param address String, lugar de entrega del pedido.
	 * @param shippingCosts String, precio del envio del pedido.
	 * @param idMarket String, id del market al que pertenece el pedido.
	 */
	public Order(String address, String shippingCosts, String idMarket) {
		this.address = address;
		this.shippingCosts = shippingCosts;
		this.idMarket = idMarket;
		this.products = new ArrayList<>();
		this.startDate= ""+LocalDate.now();
	}
	
	public Order(String id, List<Product> lp) {
		this.id = id;
		this.startDate = ""+LocalDate.now();
		this.products = lp;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdMarket() {
		return idMarket;
	}
	
	public void setIdMarket(String idMarket) {
		this.idMarket = idMarket;
	}
	
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = ""+LocalDate.parse(startDate);
	}
	
	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = "" + LocalDate.parse(deliveryDate);
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getShippingCosts() {
		return shippingCosts;
	}

	public void setShippingCosts(String shippingCosts) {
		this.shippingCosts = ""+Double.valueOf(shippingCosts);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Product> getProducts() {
		return products;
	}
	
	public void setProducts(List<Product> lp) {
		products = lp;
	}
	
//	Getting the order price
	
	public String getOrderPrice(List<Product> lp) {
		
		Double sum = lp.stream()
				.mapToDouble(x -> Double.valueOf(x.getPrice()))
				.sum();
		return sum.toString();
		
	}
	
	public Product getProduct(String productId) {
		if (products==null)
			return null;
		
		Product product =null;
		
		for(Product p: products)
			if (p.getId().equals(productId))
			{
				product=p;
				break;
			}
		return product;
	}
	
	public void addProduct(Product p) {
		if (products==null)
			products = new ArrayList<Product>();
		
		products.add(p);
	}
	
	public void deleteProduct(Product p) {
		products.remove(p);
	}
	
	public void deleteProduct(String productId) {
		Product p = getProduct(productId);
		if (p!=null)
			products.remove(p);
	}

}
