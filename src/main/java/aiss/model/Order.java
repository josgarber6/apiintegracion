package aiss.model;

import java.util.*;

public class Order {
	
	private String id;
	private String date;
	private String address;
	private String shippingCosts;
	private String supermarket;
	private List<Product> products;

	public Order() {}
	
	protected void setProducts(List<Product> s) {
		products = s;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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
		this.shippingCosts = shippingCosts;
	}

	public String getSupermarket() {
		return supermarket;
	}

	public void setSupermarket(String supermarket) {
		this.supermarket = supermarket;
	}

	public List<Product> getProducts() {
		return products;
	}
	
//	Getting the order price
	
	public String getOrderPrice(List<Product> s) {
		
		Double sum = s.stream().mapToDouble(x -> Double.valueOf(x.getPrice())).sum();
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
	
}
