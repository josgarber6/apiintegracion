package aiss.model.repository;

import java.util.Collection;

import aiss.model.Market;
import aiss.model.User;
import aiss.model.Order;
import aiss.model.Product;

public interface MarketRepository {
	
	
	// Products
	public void addProduct(Product p);
	public Collection<Product> getAllProducts();
	public Product getProduct(String productId);
	public void updateProduct(Product p);
	public void deleteProduct(String productId);
	
	// Markets
	public void addMarket(Market s);
	public Collection<Market> getAllMarkets();
	public Market getMarket(String MarketId);
	public void updateMarket(Market s);
	public void deleteMarket(String MarketId);
	public Collection<Product> getAll(String MarketId);
	public void addProductToMarket(String MarketId, String productId);
	public void removeProductOfMarket(String MarketId, String productId);
	
	// Orders
	public void addOrder(Order o);
	public Collection<Order> getAllOrders();
	public void addProductToOrder(String orderId, String productId);
	public void removeProductOfOrder(String orderId, String productId);
	public Order getOrder(String orderId);
	public void updateOrder(Order o);
	public void deleteOrder(String orderId);
	
	// Users
	public void addUser(User u);
	public Collection<User> getAllUsers();
	public User getUser(String userId);
	public void updateUser(User u);
	public String getToken(String userId);
	public void deleteUser(String userId);
	
}
