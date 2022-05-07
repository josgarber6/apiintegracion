package aiss.model.repository;

import java.util.Collection;

import aiss.model.Supermarket;
import aiss.model.Product;

public interface SupermarketRepository {
	
	
	// Products
	public void addProduct(Product s);
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
	public void addProduct(String supermarketId, String ProductId);
	public void removeProduct(String supermarketId, String ProductId); 

	
	
	
	

}
