package aiss.model;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class Product {

	private String id;
	private String name;
	private Double price;
	private Integer rating;
	private Integer quantity;
	private LocalDate expirationDate;
	
	public enum ProductType{
		LEISURE, FOOD, CLOTHES,  HEALTH, 
	}
	

	public Product() {
	}
	
	/**
	 * Obtain a instance {@code Product} from a name, price, quantity, expiration date.
	 * @param name String, name of the product.
	 * @param price String, price of the product.
	 * @param quantity String, quantity of the product.
	 * @param date String, date of expiration, format yyyy-mm-dd, dd/mm/yyyy, or null if the product is timeless.
	 * @param rating String, valuation of the product.
	 */
	public Product(String name, String price, String quantity, String date, String rating) {
		/*
		 * PARA LOS PRODUCTOS QUE NO TENGAN CADUCIDAD EL VALOR expirationDate SERA El LocalDate.MAX
		 * EL FORMATO DE LocalDate es yyyy-mm-dd
		 */
		Pattern ISODateFormat = Pattern.compile("^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$");
		if(ISODateFormat.asMatchPredicate().test(date)) this.expirationDate = LocalDate.parse(date);
		else if(date.equals("null")) this.expirationDate = LocalDate.MAX;
		else {
			String[] ParseDate = date.split("/");
			this.expirationDate = LocalDate.of(Integer.valueOf(ParseDate[2]), Integer.valueOf(ParseDate[1]), Integer.valueOf(ParseDate[0]));
		}
		this.name = name;
		this.price = Double.valueOf(price);
		this.quantity = Integer.valueOf(quantity);
		this.rating = Integer.valueOf(rating);
	}
	
	/**
	 * Obtain a instance {@code Product} from a id, name, price, quantity, expiration date.
	 * @param id String, unique identifier
	 * @param name String, name of the product.
	 * @param price String, price of the product.
	 * @param quantity String, quantity of the product.
	 * @param date String, date of expirationformat yyyy-mm-dd, dd/mm/yyyy, or null if the product is timeless.
	 * @param rating String, valuation of the product.
	 */
	public Product(String id, String name, String price, String quantity, String date, String rating) {
		/*
		 * PARA LOS PRODUCTOS QUE NO TENGAN CADUCIDAD EL VALOR expirationDate SERA El LocalDate.MAX
		 * EL FORMATO DE LocalDate es yyyy-mm-dd
		 */
		this.id = id;
		Pattern ISODateFormat = Pattern.compile("^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$");
		if(ISODateFormat.asMatchPredicate().test(date)) this.expirationDate = LocalDate.parse(date);
		else if(date.equals("null")) this.expirationDate = LocalDate.MAX;
		else {
			String[] ParseDate = date.split("/");
			this.expirationDate = LocalDate.of(Integer.valueOf(ParseDate[2]), Integer.valueOf(ParseDate[1]), Integer.valueOf(ParseDate[0]));
		}
		this.name = name;
		this.price = Double.valueOf(price);
		this.quantity = Integer.valueOf(quantity);
		this.rating = Integer.valueOf(rating);
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = Double.valueOf(price);
	}

	public Boolean getAvailability() {
		return getQuantity()>0? true:false;
	}

	public LocalDate getExpirationDate() {
		return expirationDate;
	}
	/**
	 * 
	 * @param expirationDate String, format yyyy-mm-dd, dd/mm/yyyy, or null if the product is timeless.
	 */
	public void setExpirationDate(String expirationDate) {
		Pattern ISODateFormat = Pattern.compile("^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$");
		if(ISODateFormat.asMatchPredicate().test(expirationDate)) this.expirationDate = LocalDate.parse(expirationDate);
		else if(expirationDate.equals("null")) this.expirationDate = LocalDate.MAX;
		else {
			String[] ParseDate = expirationDate.split("/");
			this.expirationDate = LocalDate.of(Integer.valueOf(ParseDate[2]), Integer.valueOf(ParseDate[1]), Integer.valueOf(ParseDate[0]));
		}
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = Integer.valueOf(quantity);
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = Integer.valueOf(rating);
	}

}
