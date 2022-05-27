package aiss.api.resources;

import aiss.api.consuming.resources.ProductResource;
import aiss.model.consuming.Product;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.restlet.resource.ResourceException;

public class ProductResourceTest {
	
	static Product product1, product2, product3, product4;
	static ProductResource pr = new ProductResource();

	@BeforeClass
	public static void setup() throws Exception {
		
		// Test product 1
		product1 = pr.addProduct(new Product("Patatas", "3", "5","100","Food","1.5"));
		
		// Test product 2
		product2 = pr.addProduct(new Product("CornFlakes", "4.5", "40","1000","Food","5.0"));
		
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
			System.out.println("Product " + i++ + " : " + p.getName() + " (ID=" + p.getId() + ")");
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
	
//	@Test
//	public void testUpdateProduct() {
//		
//		String productName = "Zanahoria";
//		String averagePopularity = "4.0";
//		String availability = "5";
//		String totalStock = "20";
//		String averagePrice = "1.5";
//		String category = "FOOD";
//		
//		
//		// Update product
//		product2.setName(productName);
//		product2.setAveragePopularity(Double.valueOf(averagePopularity));
//		product2.setAvailability(Integer.valueOf(availability));
//		product2.setTotalStock(Integer.valueOf(totalStock));
//		product2.setAveragePrice(Double.valueOf(averagePrice));
//		product2.setCategory(category);
//		
//		boolean success = pr.updateProduct(product2);
//		
//		assertTrue("Error when updating the product", success);
//		
//		Product product  = pr.getProduct(product2.getId());
//		
//		if (success) {
//			System.out.println("Canción actualizada correctamente");
//		}
//		assertEquals("The product's name has not been updated correctly", productName, product.getName());
//		assertEquals("The product's average popularity has not been updated correctly", averagePopularity, product.getAveragePopularity());
//		assertEquals("The product's availability has not been updated correctly", availability, product.getAvailability());
//		assertEquals("The product's total stock has not been updated correctly", totalStock, product.getTotalStock());
//		assertEquals("The product's total average price has not been updated correctly", averagePrice, product.getAveragePrice());
//		assertEquals("The product's total category has not been updated correctly", category, product.getCategory());
//	}
	
	@Test(expected = ResourceException.class)
	public void testDeleteProduct() {
		
		// Delete products
		boolean success = pr.deleteProduct(product4.getId());
		
		assertTrue("Error when deleting the product", success);
		
		Product product = pr.getProduct(product4.getId());
		assertNull("The product has not been deleted correctly", product);
	}
	
}
