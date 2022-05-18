package aiss.model.resources;

import static org.junit.Assert.*;
import java.util.Collection;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.restlet.resource.ResourceException;

import aiss.model.Product;

public class ProductResourceTest {

//	static Product product1, product2, product3, product4;
//	static ProductResource sr = new ProductResource();
//	
//	@BeforeClass
//	public static void setup() throws Exception {
//		
//		// Test product 1
//		product1 = sr.addproduct(new product("Test id","Test name",23,Boolean.TRUE));
//		
//		// Test product 2
//		product2 = sr.addproduct(new product("Test id2","Test name2",22,Boolean.FALSE));
//		
//	}
//
//	@AfterClass
//	public static void tearDown() throws Exception {
//		sr.deleteproduct(product1.getId());
//		sr.deleteproduct(product2.getId());
//	}
//	
//	@Test
//	public void testGetAll() {
//		Collection<product> products = sr.getAll();
//		
//		assertNotNull("The collection of products is null", products);
//		
//		// Show result
//		System.out.println("Listing all products:");
//		int i=1;
//		for (Product s : products) {
//			System.out.println("product " + i++ + " : " + s.getName() + " (ID=" + s.getId() + ")");
//		}
//	}
//
//	@Test
//	public void testGetproduct() {
//		//TODO
//		Product product = sr.getproduct(product2.getId());
//		assertEquals("The id of the products do not match", product2.getId(), product.getId());
//
//		// Show result
//		System.out.println("product id: " +  product.getId());
//		System.out.println("product name: " +  product.getName());
//	}
//
//	@Test
//	public void testAddproduct() {
//		
//		//TODO
//		
//		String productId = "3";
//		String productName = "Manolo";
//		Integer productPrice = 245;
//		Boolean productAvailability = Boolean.TRUE;
//		
//		product4 = sr.addproduct(new product(productId,productName, productPrice, productAvailability));
//		
//		assertNotNull("Error when adding the product", product4);
//		assertEquals("The product's name has not been setted correctly", productTitle, product4.getTitle());
//		assertEquals("The product's description has not been setted correctly", productAlbum, product4.getAlbum());
//
//	}
//
//	@Test
//	public void testUpdateproduct() {
//		
//		String productId = "0";
//		String productName = "Dr.Kenobi";
//		String productPrice = "23";
//		
//		String productYear = "1995";
//		
//		// Update product
//		product1.setTitle(productTitle);
//		product1.setArtist(productArtist);
//		product1.setAlbum(productAlbum);
//		product1.setYear(productYear);
//		
//		boolean success = sr.updateproduct(product1);
//		
//		assertTrue("Error when updating the product", success);
//		
//		product product  = sr.getproduct(product1.getId());
//		
//		if (success) {
//			System.out.println("Product update correctly");
//		}
//		assertEquals("The product's title has not been updated correctly", productTitle, product.getTitle());
//		assertEquals("The product's artist has not been updated correctly", productArtist, product.getArtist());
//		assertEquals("The product's album has not been updated correctly", productAlbum, product.getAlbum());
//		assertEquals("The product's year has not been updated correctly", productYear, product.getYear());
//	}
////
////	@Test(expected = ResourceException.class)
////	public void testDeleteproduct() {
////		
////		// Delete products
////		boolean success = sr.deleteproduct(product2.getId());
////		
////		assertTrue("Error when deleting the product", success);
////		
////		product product  = sr.getproduct(product2.getId());
////		assertNull("The product has not been deleted correctly", product);
////	}

}
