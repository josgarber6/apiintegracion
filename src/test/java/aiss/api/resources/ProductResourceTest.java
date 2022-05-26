package aiss.api.resources;

import aiss.model.consuming.Product;
import aiss.model.repository.MapMarketRepository;

import static org.junit.Assert.assertNotNull;

import java.util.Collection;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import aiss.api.consuming.resources.ProductResource;

public class ProductResourceTest {
	
	static Product product1, product2, product3, product4;
	static ProductResource pr = new ProductResource();

	@BeforeClass
	public static void setup() throws Exception {
		
		// Test product 1
		product1 = pr.addProduct(new Product("DDJ-1000", "5", "0","100","DJ Controllers","1299,90"));
		
//		product3 = pr.addProduct(MapMarketRepository.productMap.get(pr))
		
		// Test song 2
		product2 = pr.addProduct(new Product("DDJ-400", "4.5", "40","1000","DJ Controllers","289,90"));
		
	}
	
	@AfterClass
	public static void tearDown() throws Exception {
		pr.deleteProduct(product1.getId());
		pr.deleteProduct(product2.getId());
	}
	
	@Test
	public void testGetAll() {
		Collection<Product> products = pr.getAll();
		
		assertNotNull("The collection of songs is null", products);
		
		// Show result
		System.out.println("Listing all products:");
		int i=1;
		for (Product p : products) {
			System.out.println("Product " + i++ + " : " + p.getName() + " (ID=" + p.getId() + ")");
		}
	}
	
}
