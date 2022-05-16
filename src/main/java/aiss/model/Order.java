package aiss.model;

import java.time.LocalDate;
import java.util.*;

public class Order {
	
	private String id;
	private LocalDate dateStart;
	private LocalDate dateDelivery;
	private String address;
	private Double shippingCosts;
	private String idMarket;
	private User user;
	private List<Product> products;

	public Order() {
		
	}
	
	public Order(String id, List<Product> ps) {
		this.id = id;
		this.dateStart = LocalDate.now();
		this.products = ps;
	}
	
	public void setProducts(List<Product> ps) {
		products = ps;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return dateStart;
	}

	public void setDate(String date) {
		this.dateStart = LocalDate.parse(date);
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getShippingCosts() {
		return shippingCosts;
	}

	public void setShippingCosts(String shippingCosts) {
		this.shippingCosts = Double.valueOf(shippingCosts);
	}

	public String getMarket() {
		return idMarket;
	}

	public void setMarket(String market) {
		this.idMarket = market;
	}

	public List<Product> getProducts() {
		return products;
	}
	
//	Getting the order price
	
	public String getOrderPrice(List<Product> ps) {
		
		Double sum = ps.stream().mapToDouble(x -> Double.valueOf(x.getPrice())).sum();
		return sum.toString();
		
	}
	
	public Product getProduct(String id) {
		if (products==null)
			return null;
		
		Product product =null;
		for(Product p: products)
			if (p.getId().equals(id))
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
	
	public void deleteProduct(String id) {
		Product p = getProduct(id);
		if (p!=null)
			products.remove(p);
	}

	public LocalDate getDateDelivery() {
		return dateDelivery;
	}

	public void setDateDelivery(String dateDelivery) {
		this.dateDelivery = LocalDate.parse(dateDelivery);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
