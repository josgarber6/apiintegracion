package aiss.model;

import java.time.LocalDate;
import java.util.*;

public class Order {
	
	private String id;
	private String idMarket;
	private String dateStart;
	private String dateDelivery;
	private String address;
	private String shippingCosts;
	private User user;
	private List<Product> products;

	public Order() {
		
	}
	
	public Order(String id, List<Product> lp) {
		this.id = id;
		this.dateStart = ""+LocalDate.now();
		this.products = lp;
	}
	
	public void setProducts(List<Product> lp) {
		products = lp;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDate() {
		return dateStart;
	}

	public void setDate(String date) {
		this.dateStart = ""+LocalDate.parse(date);
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

	public String getIdMarket() {
		return idMarket;
	}

	public void setIdMarket(String idMarket) {
		this.idMarket = idMarket;
	}

	public List<Product> getProducts() {
		return products;
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

	public String getDateDelivery() {
		return dateDelivery;
	}

	public void setDateDelivery(String dateDelivery) {
		this.dateDelivery = "" + LocalDate.parse(dateDelivery);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
