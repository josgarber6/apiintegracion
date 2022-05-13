package aiss.model.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import aiss.model.Order;
import aiss.model.Product;
import aiss.model.Supermarket;
import aiss.model.User;


public class MapSupermarketRepository implements SupermarketRepository{

	Map<String, Supermarket> supermarketMap;
	Map<String, Product> productMap;
	Map<String, Order> orderMap;
	Map<String, User> userMap;
	private static MapSupermarketRepository instance=null;
	private int index=0; // Index to create supermarkets, products and orders' identifiers.

	
	
	public static MapSupermarketRepository getInstance() {
		
		if (instance==null) {
			instance = new MapSupermarketRepository();
			instance.init();
		}
		
		return instance;
	}
	
	public void init() {
		
		supermarketMap = new HashMap<String,Supermarket>();
		productMap = new HashMap<String,Product>();
		
		// Create products
		Product patatas = new Product("Patatas", "1,50€", "64", "2022-05-30", "3", "FOOD");
		addProduct(patatas);
		
		Product cornFlakes = new Product("CornFlakes", "2.50", "25", "15/02/2023", "2", "FOOD");
		addProduct(cornFlakes);
		
		Product macarrones = new Product("Macarrones", "2", "150", "null", "4", "FOOD");
		addProduct(macarrones);
		
		// Create supermarkets
		Supermarket mercadona=new Supermarket();
		mercadona.setName("Mercadona");
		mercadona.setDescription("Supermercados Mercadona");
		addSupermarket(mercadona);
		
		Supermarket dia = new Supermarket();
		dia.setName("Dia");
		dia.setDescription("Supermercados Dia");
		addSupermarket(dia);
		
		// Create orders
		Order o1=new Order();
		o1.addProduct(patatas);
		o1.setAddress("Avenida de la Reina Mercerdes, s/n");
		o1.setDate("10-05-2022");
		o1.setShippingCosts("5,00€");
		o1.setSupermarket("Mercadona");
		
		// Create users
		User u1 = new User("Maria","aa@aiss.com","secret","baños, 33");
		addUser(u1);
		
		User u2 = new User("Jose Antonio Pascual","abc@aiss.com","qwert","Sevilla, Sierpes, 24");
		addUser(u2);
		
		User u3 = new User("pepe","a@aiss.com","1234","reina mercedes");
		addUser(u3);
		
		// Add products to supermarkets and orders
		addProduct(mercadona.getId(), patatas.getId());
		addProduct(mercadona.getId(), cornFlakes.getId());
		addProduct(mercadona.getId(), macarrones.getId());
		
		addProduct(dia.getId(), patatas.getId());
		addProduct(dia.getId(), cornFlakes.getId());
		addProduct(dia.getId(), macarrones.getId());
		
		addProduct(o1.getId(), patatas.getId());
		addProduct(o1.getId(), cornFlakes.getId());
		addProduct(o1.getId(), macarrones.getId());
		
	}
	
	// Supermarket related operations
	@Override
	public void addSupermarket(Supermarket s) {
		String id = "s" + index++;	
		s.setId(id);
		supermarketMap.put(id,s);
	}
	
	@Override
	public Collection<Supermarket> getAllSupermarkets() {
			return supermarketMap.values();
	}

	@Override
	public Supermarket getSupermarket(String id) {
		return supermarketMap.get(id);
	}
	
	@Override
	public void updateSupermarket(Supermarket p) {
		supermarketMap.put(p.getId(),p);
	}

	@Override
	public void deleteSupermarket(String id) {	
		supermarketMap.remove(id);
	}
	

	@Override
	public void addProduct(String supermarketId, String songId) {
		Supermarket supermarket = getSupermarket(supermarketId);
		supermarket.addProduct(productMap.get(songId));
	}

	@Override
	public Collection<Product> getAll(String supermarketId) {
		return getSupermarket(supermarketId).getProducts();
	}

	@Override
	public void removeProduct(String supermarketId, String songId) {
		getSupermarket(supermarketId).deleteProduct(songId);
	}

	
	// Product related operations
	
	@Override
	public void addProduct(Product p) {
		String id = "p" + index++;
		p.setId(id);
		productMap.put(id, p);
	}
	
	@Override
	public Collection<Product> getAllProducts() {
		return productMap.values();
	}

	@Override
	public Product getProduct(String productId) {
		return productMap.get(productId);
	}

	@Override
	public void updateProduct(Product p) {
		Product product = productMap.get(p.getId());
		product.setName(p.getName());
		product.setPrice(""+p.getPrice());
		product.setQuantity(""+p.getQuantity());;
		product.setExpirationDate(""+p.getExpirationDate());
	}

	@Override
	public void deleteProduct(String productId) {
		productMap.remove(productId);
	}
	
	// Order related operations

	@Override
	public void addOrder(Order o) {
		String id = "o" + index++;
		o.setId(id);
		orderMap.put(id, o);
	}

	@Override
	public Collection<Order> getAllOrders() {
		return orderMap.values();
	}

	@Override
	public Order getOrder(String orderId) {
		return orderMap.get(orderId);
	}

	@Override
	public void updateOrder(Order o) {
		Order order = orderMap.get(o.getId());
		order.setAddress(o.getAddress());
		order.setDate(o.getDate());
		order.setShippingCosts(o.getShippingCosts());
		order.setSupermarket(o.getSupermarket());
	}


	@Override
	public void deleteOrder(String orderId) {
		orderMap.remove(orderId);
	}
	
	// User related operations

	@Override
	public void addUser(User u) {
		String id = "u" + index++;
		u.setId(id);
		userMap.put(id, u);
	}

	@Override
	public Collection<User> getAllUsers() {
		return userMap.values();
	}

	@Override
	public User getUser(String userId) {
		return userMap.get(userId);
	}

	@Override
	public void updateUser(User u) {
		User user = userMap.get(u.getId());
		user.setName(u.getName());
		user.setEmail(u.getEmail());
		user.setPassword(u.getPassword());
		user.setAddress(u.getAddress());
	}

	@Override
	public String getToken(String userId) {
		User user = userMap.get(userId);
		return user.getToken();
	}

	@Override
	public void deleteUser(String userId) {
		userMap.remove(userId);
	}
	
}
