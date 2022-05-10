package aiss.model.resources;

import static org.junit.Assert.*;
import java.util.Collection;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.restlet.resource.ResourceException;

import aiss.model.product;

public class ProductResourceTest {

	static Product product1, product2, product3, product4;
	static ProductResource sr = new ProductResource();
	
	@BeforeClass
	public static void setup() throws Exception {
		
		// Test product 1
		product1 = sr.addproduct(new product("Test title","Test artist","Test album","2016"));
		
		// Test product 2
		product2 = sr.addproduct(new product("Test title 2","Test artist 2","Test album 2","2017"));
		
	}

	@AfterClass
	public static void tearDown() throws Exception {
		sr.deleteproduct(product1.getId());
		sr.deleteproduct(product2.getId());
	}
	
	@Test
	public void testGetAll() {
		Collection<product> products = sr.getAll();
		
		assertNotNull("The collection of products is null", products);
		
		// Show result
		System.out.println("Listing all products:");
		int i=1;
		for (product s : products) {
			System.out.println("product " + i++ + " : " + s.getTitle() + " (ID=" + s.getId() + ")");
		}
	}

	@Test
	public void testGetproduct() {
		//TODO
		product product = sr.getproduct(product2.getId());
		assertEquals("The id of the products do not match", product2.getId(), product.getId());

		// Show result
		System.out.println("product id: " +  product.getId());
		System.out.println("product artist: " +  product.getArtist());
	}

	@Test
	public void testAddproduct() {
		
		//TODO
		
		String productTitle = "Burn It Up";
		String productArtist = "Kaotix";
		String productAlbum = "Kaos Recordings Vol. 1";
		String productYear = "2022";
		
		product4 = sr.addproduct(new product(productTitle,productArtist, productAlbum, productYear));
		
		assertNotNull("Error when adding the product", product4);
		assertEquals("The product's name has not been setted correctly", productTitle, product4.getTitle());
		assertEquals("The product's description has not been setted correctly", productAlbum, product4.getAlbum());

	}

	@Test
	public void testUpdateproduct() {
		
		String productId = "0";
		String productName = "Dr.Kenobi";
		String productAlbum = "Update product test album";
		String productYear = "1995";
		
		// Update product
		product1.setTitle(productTitle);
		product1.setArtist(productArtist);
		product1.setAlbum(productAlbum);
		product1.setYear(productYear);
		
		boolean success = sr.updateproduct(product1);
		
		assertTrue("Error when updating the product", success);
		
		product product  = sr.getproduct(product1.getId());
		
		if (success) {
			System.out.println("Product update correctly");
		}
		assertEquals("The product's title has not been updated correctly", productTitle, product.getTitle());
		assertEquals("The product's artist has not been updated correctly", productArtist, product.getArtist());
		assertEquals("The product's album has not been updated correctly", productAlbum, product.getAlbum());
		assertEquals("The product's year has not been updated correctly", productYear, product.getYear());
	}
//
//	@Test(expected = ResourceException.class)
//	public void testDeleteproduct() {
//		
//		// Delete products
//		boolean success = sr.deleteproduct(product2.getId());
//		
//		assertTrue("Error when deleting the product", success);
//		
//		product product  = sr.getproduct(product2.getId());
//		assertNull("The product has not been deleted correctly", product);
//	}

}
