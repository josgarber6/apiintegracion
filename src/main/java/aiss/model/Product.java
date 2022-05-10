package aiss.model;

public class Product {

	private String id;
	private String name;
	private Integer price;
	private Boolean availability;

	public Product() {
	}

	public Product(String name, String price, String availability) {
		this.name = name;
		this.price = Integer.valueOf(price);
		this.availability = Boolean.valueOf(availability);
	}
	
	public Product(String id, String name, String price, String availability) {
		this.id=id;
		this.name = name;
		this.price = Integer.valueOf(price);
		this.availability = Boolean.valueOf(availability);
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

	public Integer getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = Integer.valueOf(price);
	}

	public Boolean getAvailability() {
		return availability;
	}

	public void setAvailability(Boolean availability) {
		this.availability = availability;
	}

}
