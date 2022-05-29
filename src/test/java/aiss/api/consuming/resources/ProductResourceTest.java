package aiss.api.consuming.resources;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.restlet.resource.ResourceException;

import aiss.model.consuming.MarketProduct;
import aiss.model.consuming.OnlineShop;
import aiss.model.consuming.Product;

public class ProductResourceTest {
	
	static Product product1, product2, product3;
	static OnlineShop Mercadona, Supersol, Dia, MAS;
	static MarketProduct MercadoProduct1, SupersolProduct1, DiaProduct1, MASProduct1;
	static ProductResource pr = new ProductResource();
	static OnlineShopResource or = new OnlineShopResource();

	@BeforeClass
	public static void setUp() throws Exception {
				
		product2 = pr.addProduct(new Product("CornFlakes", "Food"));
		product1 = pr.addProduct(new Product("Patatas", "FOOD"));
				
		Mercadona = or.addOnlineShop(new OnlineShop("Mercadona", "https://www.mercadona.es/", "4", "Supermercados Mercadona", null));
		Supersol = or.addOnlineShop(new OnlineShop("Supersol", "https://www.supersol.es/", "4", "Supermercados Supersol", null));
		Dia = or.addOnlineShop(new OnlineShop("Dia", "https://www.dia.es/", "4", "Supermercados Dia", null));
		MAS = or.addOnlineShop(new OnlineShop("MAS", "https://www.mas.es/", "4", "Supermercados MAS", null));
	}

	@AfterClass
	public static void tearDown() throws Exception {
		pr.deleteProduct(product3.getId());
		pr.deleteProduct(product1.getId());
		or.deleteOnlineShop(Mercadona.getId());
		or.deleteOnlineShop(Supersol.getId());
		or.deleteOnlineShop(Dia.getId());
		or.deleteOnlineShop(MAS.getId());
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
		assertNotNull("The products is null", product);
		assertEquals("The category of the products do not match", product2.getCategory(), product.getCategory());
		assertEquals("The name of the products do not match", product2.getName(), product.getName());
		assertEquals("The id of the products do not match", product2.getId(), product.getId());

		// Show result
		System.out.println("Product id: " +  product.getId());
		System.out.println("Product name: " +  product.getName());
		System.out.println("Product average popularity: " +  product.getAveragePopularity());
		System.out.println("Product availability: " +  product.getAvailability());
		System.out.println("Product category: " +  product.getCategory());
		System.out.println("Product average price: " +  product.getAveragePrice());
	}
	
	@Test
	public void testAddProduct() {
		
		String productName = "Pantalon Zara";
		String category = "ROPA";
		
		product3 = pr.addProduct(new Product(productName, category));
		
		assertNotNull("Error when adding the product", product3);
		assertEquals("The product's name has not been setted correctly", productName, product3.getName());
		assertEquals("The product's category has not been setted correctly", category, product3.getCategory());

	}
	
	@Test
	public void testAppProductToShop() {
		
		MercadoProduct1 = new MarketProduct(product1, "3.50", "10", "25");
		SupersolProduct1 = new MarketProduct(product1, "2.0", "22", "5");
		DiaProduct1 = new MarketProduct(product1, "4.66", "50", "10");
		MASProduct1 = new MarketProduct(product1, "2.50", "30", "25");
		
		if(product1 != null) {
			boolean success1 = or.addProduct(Mercadona.getId(), product1.getId(), MercadoProduct1);
			boolean success2 =  or.addProduct(Supersol.getId(), product1.getId(), SupersolProduct1);
			boolean success3 = or.addProduct(Dia.getId(), product1.getId(), DiaProduct1);
			boolean success4 = or.addProduct(MAS.getId(), product1.getId(), MASProduct1);
			
			assertTrue("Error when adding the product1 to Mercadona", success1);
			assertTrue("Error when adding the product1 to Supersol", success2);
			assertTrue("Error when adding the product1 to Dia", success3);
			assertTrue("Error when adding the product1 to MAS", success4);
		}
	}
	
	
	
	@Test
	public void testUpdateProduct() {
		
		String productName = "Zanahoria";
		String category = "ROPA";
		
		
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
		boolean success = pr.deleteProduct(product2.getId());
		
		assertTrue("Error when deleting the product", success);
		
		Product product = pr.getProduct(product2.getId());
		assertNull("The product has not been deleted correctly", product);
	}

}
