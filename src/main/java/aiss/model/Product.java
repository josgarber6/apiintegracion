package aiss.model;

public class Product {

	private String id;
	private String name;
	private String price;
	private Boolean availability;

	public Product() {
	}

	public Product(String name, String price, Boolean availability) {
		this.name = name;
		this.price = price;
		this.availability = availability;
	}
	
	public Product(String id, String name, String price, Boolean availability) {
		this.id=id;
		this.name = name;
		this.price = price;
		this.availability = availability;
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

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public Boolean getAvailability() {
		return availability;
	}

	public void setAvailability(Boolean availability) {
		this.availability = availability;
	}

}
