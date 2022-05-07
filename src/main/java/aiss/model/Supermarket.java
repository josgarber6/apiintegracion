package aiss.model;

import java.util.ArrayList;
import java.util.List;

public class Supermarket {

	private String id;
	private String name;
	private String description;
	private List<Product> products;
	
	public Supermarket() {}
	
	public Supermarket(String name) {
		this.name = name;
	}
	
	protected void setProducts(List<Product> s) {
		products = s;
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
		return products;
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
