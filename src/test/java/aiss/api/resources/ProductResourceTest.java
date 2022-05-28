package aiss.api.resources;

import aiss.api.consuming.resources.OnlineShopResource;
import aiss.api.consuming.resources.ProductResource;
import aiss.model.consuming.MarketProduct;
import aiss.model.consuming.OnlineShop;
import aiss.model.consuming.Product;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.restlet.resource.ResourceException;

public class ProductResourceTest {
	
	static Product product1, product2, product3, product4;
	static OnlineShop online1, online2, online3, online4;
	static MarketProduct MercadoProduct1, SupersolProduct1, DiaProduct1, MASProduct1;
	static ProductResource pr = new ProductResource();
	static OnlineShopResource or = new OnlineShopResource();

	@BeforeClass
	public static void setup() throws Exception {
		
		// Test product 1
		product1 = pr.addProduct(new Product("Patatas", "3.25", "5","100","Food","1.5"));
		
		// Test product 2
		product2 = pr.addProduct(new Product("CornFlakes", "4.5", "40","1000","Food","5.0"));
		
		MercadoProduct1 = new MarketProduct(product1, "3.50", "10", "25");
		SupersolProduct1 = new MarketProduct(product1, "2.0", "22", "5");
		DiaProduct1 = new MarketProduct(product1, "4.66", "50", "10");
		MASProduct1 = new MarketProduct(product1, "2.50", "30", "25");
		
		
		
	}
	
	@AfterClass
	public static void tearDown() throws Exception {
		pr.deleteProduct(product1.getId());
		pr.deleteProduct(product2.getId());
	}
	
	@Test
	public void testGetAll() {
		Collection<Product> products = pr.getAll();
		
		assertNotNull("The collection of products is null", products);
		
		// Show result
		System.out.println("Listing all products:");
		int i=1;
		for (Product p : products) {
			System.out.println("Product " + i++ + " : " + p.getName() + " (ID=" + p.getId() + ")" + " (PRICE=" + p.getAveragePrice() + ")" + " (POPULARITY=" + p.getAveragePopularity() + ")" + " (STOCK=" + p.getTotalStock() + ")");
		}
	}
	
	@Test
	public void testGetProduct() {
		Product product = pr.getProduct(product2.getId());
		assertEquals("The id of the products do not match", product2.getId(), product.getId());

		// Show result
		System.out.println("Product name: " +  product.getId());
		System.out.println("Product average popularity: " +  product.getAveragePopularity());
	}
	
	@Test
	public void testAddProduct() {
		
		String productName = "Patatas";
		String averagePopularity = "3";
		String availability = "5";
		String totalStock = "10";
		String averagePrice = "1.5";
		String category = "FOOD";
		
		product4 = pr.addProduct(new Product(productName, averagePopularity, availability, totalStock, category, averagePrice));
		
		assertNotNull("Error when adding the product", product4);
		assertEquals("The product's name has not been setted correctly", productName, product4.getName());
		assertEquals("The product's category has not been setted correctly", category, product4.getCategory());

	}
	
	@Test
	public void testUpdateProduct() {
		
		String productName = "Zanahoria";
		String category = "FOOD";
		
		
		// Update product
		product2.setName(productName);
		product2.setCategory(category);
		
		boolean success = pr.updateProduct(product2);
		
		assertTrue("Error when updating the product", success);
		
		Product product  = pr.getProduct(product2.getId());
		
		if (success) {
			System.out.println("Product actualizada correctamente");
		}
		assertEquals("The product's name has not been updated correctly", productName, product.getName());
		assertEquals("The product's total category has not been updated correctly", category, product.getCategory());
	}
	
	@Test(expected = ResourceException.class)
	public void testDeleteProduct() {
		
		// Delete products
		boolean success = pr.deleteProduct(product4.getId());
		
		assertTrue("Error when deleting the product", success);
		
		Product product = pr.getProduct(product4.getId());
		assertNull("The product has not been deleted correctly", product);
	}
	
}
