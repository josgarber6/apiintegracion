package aiss.model;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class Product {

	private String id;
	private String idMarket;
	private String name;
	private String price;
	private String rating;
	private String quantity;
	private String expirationDate;
	private ProductType type;
	
	public enum ProductType{
		LEISURE, FOOD, CLOTHES,  HEALTH, SPORT
	}
	

	public Product() {
	}
	
	/**
	 * Obtain a instance {@code Product} from a name, price, quantity, expiration date, rating and type of product.
	 * @param idMarket String, id of the market to which the product belongs.
	 * @param name String, name of the product.
	 * @param price String, price of the product.
	 * @param quantity String, quantity of the product.
	 * @param date String, date of expiration, format yyyy-mm-dd, or null if the product is timeless.
	 * @param rating String, valuation of the product, is a value between 1-5.
	 * @param type String, category of the product, it can be {LEISURE, FOOD, CLOTHES,  HEALTH, SPORT}.
	 */
	public Product(String idMarket, String name, String price, String quantity, String date, String rating, String type) {
		/*
		 * PARA LOS PRODUCTOS QUE NO TENGAN CADUCIDAD EL VALOR expirationDate SERA El LocalDate.MAX
		 * EL FORMATO DE LocalDate es yyyy-mm-dd
		 */
		Pattern ISODateFormat = Pattern.compile("^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$");
		if(ISODateFormat.asPredicate().test(date)) {
			this.expirationDate = ""+LocalDate.parse(date);
		} else if (date.equals("null")) this.expirationDate = ""+LocalDate.MAX;
		else this.expirationDate = null;
		this.idMarket = idMarket;
		this.name = name;
		this.price = ""+Double.valueOf(price);
		this.quantity = ""+Integer.valueOf(quantity);
		this.rating = ""+Integer.valueOf(rating);
		this.type = ProductType.valueOf(type);
	}
	
	/**
	 * Obtain a instance {@code Product} from a id, name, price, quantity, expiration date, rating and type of product
	 * @param id String, unique identifier
	 * @param idMarket String, id of the market to which the product belongs.
	 * @param name String, name of the product.
	 * @param price String, price of the product.
	 * @param quantity String, quantity of the product.
	 * @param date String, date of expiration yyyy-mm-dd or null if the product is timeless.
	 * @param rating String, valuation of the product, is a value between 1-5.
	 * @param type String, category of the product, it can be {LEISURE, FOOD, CLOTHES,  HEALTH, SPORT}.
	 */
	public Product(String id, String idMarket, String name, String price, String quantity, String date, String rating, String type) {
		/*
		 * PARA LOS PRODUCTOS QUE NO TENGAN CADUCIDAD EL VALOR expirationDate SERA El LocalDate.MAX
		 * EL FORMATO DE LocalDate es yyyy-mm-dd
		 */
		this.id = id;
		this.idMarket = idMarket;
		Pattern ISODateFormat = Pattern.compile("^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$");
		if(ISODateFormat.asPredicate().test(date)) {
			this.expirationDate = ""+LocalDate.parse(date);
		} else if (date.equals("null")) this.expirationDate = ""+LocalDate.MAX;
		else this.expirationDate = null;
		this.name = name;
		this.price = ""+Double.valueOf(price);
		this.quantity = ""+Integer.valueOf(quantity);
		this.rating = ""+Integer.valueOf(rating);
		this.type = ProductType.valueOf(type);
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
		this.price = ""+Double.valueOf(price);
	}

	public Boolean getAvailability() {
		Integer quantity = Integer.valueOf(getQuantity());
		return quantity>0? true:false;
	}

	public String getExpirationDate() {
		return expirationDate;
	}
	/**
	 * 
	 * @param expirationDate String, format yyyy-mm-dd, or null if the product is timeless.
	 */
	public void setExpirationDate(String expirationDate) {
		Pattern ISODateFormat = Pattern.compile("^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$");
		if(ISODateFormat.asPredicate().test(expirationDate)) {
			this.expirationDate = ""+LocalDate.parse(expirationDate);
		} else if (expirationDate.equals("null")) this.expirationDate = ""+LocalDate.MAX;
		else this.expirationDate = null;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		Integer q = Integer.valueOf(quantity);
		if(q < 1 || q > 5) this.quantity = null;
		else this.quantity = ""+Integer.valueOf(quantity);
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = ""+Integer.valueOf(rating);
	}

	public ProductType getType() {
		return type;
	}

	public void setType(String type) {
		this.type = ProductType.valueOf(type);
	}

	public String getIdMarket() {
		return idMarket;
	}

	public void setIdMarket(String idMarket) {
		this.idMarket = idMarket;
	}

}
