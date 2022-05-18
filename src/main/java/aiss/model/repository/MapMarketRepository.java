package aiss.model.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import aiss.model.Order;
import aiss.model.Product;
import aiss.model.Market;
import aiss.model.User;


public class MapMarketRepository implements MarketRepository {

	Map<String, Market> MarketMap;
	Map<String, Product> productMap;
	Map<String, Order> orderMap;
	Map<String, User> userMap;
	private static MapMarketRepository instance=null;
	private int index=0; // Index to create Markets, products and orders' identifiers.

	
	
	public static MapMarketRepository getInstance() {
		
		if (instance==null) {
			instance = new MapMarketRepository();
			instance.init();
		}
		
		return instance;
	}
	
	public void init() {
		
		MarketMap = new HashMap<String,Market>();
		productMap = new HashMap<String,Product>();
		orderMap = new HashMap<String,Order>();
		userMap = new HashMap<String, User>();
		
		// Create Markets
		Market mercadona=new Market();
		mercadona.setName("Mercadona");
		mercadona.setDescription("Supermercados Mercadona");
		addMarket(mercadona);
		
		Market dia = new Market();
		dia.setName("Dia");
		dia.setDescription("Supermercados Dia");
		addMarket(dia);
		
		// Create products
		Product patatas = new Product(mercadona.getId(), "Patatas", "1.50", "64", "2022-05-30", "3", "FOOD");
		addProduct(patatas);
				
		Product cornFlakes = new Product(mercadona.getId(), "CornFlakes", "2.50", "25", "2023-02-15", "2", "FOOD");
		addProduct(cornFlakes);
				
		Product macarrones = new Product(dia.getId(), "Macarrones", "2", "150", "null", "4", "FOOD");
		addProduct(macarrones);
		
		// Create users
		User u1 = new User("Maria","aa@aiss.com","secret","baños, 33");
		addUser(u1);
		
		User u2 = new User("Jose Antonio Pascual","abc@aiss.com","qwert","Sevilla, Sierpes, 24");
		addUser(u2);
				
		User u3 = new User("pepe","a@aiss.com","1234","reina mercedes");
		addUser(u3);
		
		// Create orders
		Order o1=new Order();
		o1.setAddress("Avenida de la Reina Mercerdes, s/n");
		o1.setDate("2022-10-05");
		o1.setShippingCosts("5.00");
		o1.setIdMarket(mercadona.getId());
		o1.setUser(u1);
		addOrder(o1);
		
		// Add products to Markets and orders
//		addProductToMarket(mercadona.getId(), patatas.getId());
//		addProductToMarket(mercadona.getId(), cornFlakes.getId());
//		
//		addProductToMarket(dia.getId(), macarrones.getId());
		
		addProductToOrder(o1.getId(), patatas.getId());
		addProductToOrder(o1.getId(), cornFlakes.getId());
		addProductToOrder(o1.getId(), macarrones.getId());
		
	}
	
	// Market related operations
	@Override
	public void addMarket(Market s) {
		String id = "s" + index++;	
		s.setId(id);
		MarketMap.put(id,s);
	}
	
	@Override
	public Collection<Market> getAllMarkets() {
			return MarketMap.values();
	}

	@Override
	public Market getMarket(String id) {
		return MarketMap.get(id);
	}
	
	@Override
	public void updateMarket(Market p) {
		MarketMap.put(p.getId(),p);
	}

	@Override
	public void deleteMarket(String id) {	
		MarketMap.remove(id);
	}
	

//	@Override
//	public void addProductToMarket(String MarketId, String productId) {
//		Market Market = getMarket(MarketId);
//		Market.addProduct(productMap.get(productId));
//	}
	
	@Override
	public List<Product> getAllProductsByMarket(String marketId) {
		Collection<Product> products = getAllProducts();
		return products.stream()
				.filter(x -> x.getIdMarket().equals(marketId))
				.collect(Collectors.toList());
	}
	
	public List<Order> getAllOrdersByMarket(String marketId) {
		Collection<Order> orders = getAllOrders();
		return orders.stream().filter(x -> x.getIdMarket().equals(marketId)).collect(Collectors.toList());
	}

//	@Override
//	public Collection<Product> getAll(String MarketId) {
//		return getMarket(MarketId).getProducts();
//	}
//
//	@Override
//	public void removeProductOfMarket(String MarketId, String songId) {
//		getMarket(MarketId).deleteProduct(songId);
//	}

	
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
		order.setDate(""+o.getDate());
		order.setShippingCosts(""+o.getShippingCosts());
		order.setIdMarket(o.getIdMarket());
	}


	@Override
	public void deleteOrder(String orderId) {
		orderMap.remove(orderId);
	}
	
	@Override
	public void addProductToOrder(String orderId, String productId) {
		Order o = getOrder(orderId);
		o.addProduct(getProduct(productId));
	}

	@Override
	public void removeProductOfOrder(String orderId, String productId) {
		Order o = getOrder(orderId);
		o.deleteProduct(productId);
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
