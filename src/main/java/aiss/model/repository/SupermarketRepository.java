package aiss.model.repository;

import java.util.Collection;

import aiss.model.Supermarket;
import aiss.model.User;
import aiss.model.Order;
import aiss.model.Product;

public interface SupermarketRepository {
	
	
	// Products
	public void addProduct(Product p);
	public Collection<Product> getAllProducts();
	public Product getProduct(String productId);
	public void updateProduct(Product p);
	public void deleteProduct(String productId);
	
	// Supermarkets
	public void addSupermarket(Supermarket s);
	public Collection<Supermarket> getAllSupermarkets();
	public Supermarket getSupermarket(String supermarketId);
	public void updateSupermarket(Supermarket s);
	public void deleteSupermarket(String supermarketId);
	public Collection<Product> getAll(String supermarketId);
	public void addProduct(String supermarketId, String productId);
	public void removeProduct(String supermarketId, String productId);
	
	// Orders
	public void addOrder(Order o);
	public Collection<Order> getAllOrders();
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
