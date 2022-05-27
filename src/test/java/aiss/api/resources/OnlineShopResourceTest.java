package aiss.api.resources;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import aiss.api.consuming.resources.OnlineShopResource;
import aiss.api.consuming.resources.ProductResource;
import aiss.model.consuming.MarketProduct;
import aiss.model.consuming.OnlineShop;
import aiss.model.consuming.Product;

public class OnlineShopResourceTest {
	
	static OnlineShop shop, shop2, shop3, shop4;
	static MarketProduct marketProduct, marketProduct2, marketProduct3, marketProduct4;
	static Product product, product2, product3, product4;
	static List<MarketProduct> ls, ls2, ls3, ls4;
	static OnlineShopResource osr = new OnlineShopResource();
	static ProductResource pr = new ProductResource();
	
	@BeforeClass
	public static void setUp() throws Exception {
		
		product = pr.getProduct("p0");
		product2 = pr.getProduct("p1");
		product3 = pr.getProduct("p2");
		product4 = pr.getProduct("p3");
		
		marketProduct = new MarketProduct(product, "15.99", "25", "4");
		marketProduct2 = new MarketProduct(product2, "50.0", "100", "3.5");
		marketProduct3 = new MarketProduct(product3, "15.95", "20", "4.5");
		marketProduct4 = new MarketProduct(product4, "25.90", "99", "2.6");
		
		ls.add(marketProduct);
		
		ls2.add(marketProduct);
		ls2.add(marketProduct2);
		
		ls3.add(marketProduct2);
		ls3.add(marketProduct3);
		
		ls4.add(marketProduct);
		ls4.add(marketProduct2);
		ls4.add(marketProduct3);
		ls4.add(marketProduct4);
		
		shop = osr.addOnlineShop(new OnlineShop("Mercadona", "https://www.mercadona.es/", "4.5", "Supermercados Mercadona", ls));
		shop2 = osr.addOnlineShop(new OnlineShop("Dia", "https://www.dia.es/", "3", "Siempre cerca de ti", ls2));
		shop3 = osr.addOnlineShop(new OnlineShop("Carrefour", "https://www.carrefour.es/", "4", "Ahorra con Carrefour online", ls3));
		shop4 = osr.addOnlineShop(new OnlineShop("Lidl", "https://www.lidl.es/", "3.5", "¡Marca la diferencia!", ls4));
	
	}

	@AfterClass
	public static void tearDown() throws Exception {
		osr.deleteOnlineShop(shop.getId());
		osr.deleteOnlineShop(shop3.getId());
		osr.deleteOnlineShop(shop4.getId());
		if(product!=null)
			pr.deleteProduct(product.getId());
	}
	
	@Test
	public void testGetAll() {
		Collection<OnlineShop> shops = osr.getAll(); 
		
		assertNotNull("The collection of online shops is null", shops);
		
		// Show result
		System.out.println("Listing all shops:");
		int i=1;
		for (OnlineShop os : shops) {
			System.out.println("Shop " + i++ + " : " + os.getName() + " (ID=" + os.getId() + ")");
		}
		
	}
	
	@Test
	public void testGetShop() {
		OnlineShop s = osr.getOnlineShop(shop.getId());
		
		assertEquals("The id of the shops do not match", shop.getId(), s.getId());
		assertEquals("The name of the shops do not match", shop.getName(), s.getName());
		
		// Show result
		System.out.println("Shop id: " +  s.getId());
		System.out.println("Shop name: " + s.getName());

	}

	@Test
	public void testAddShop() {
		String shopName = "El Corte Ingles";
		String url = "https://www.elcorteingles.es/";
		String shopDescription = "Ya es primavera en El Corte Ingles";
		String rating = "5";
		List<MarketProduct> newListProduct = ls4;
		
		shop4 = osr.addOnlineShop(new OnlineShop(shopName,url,shopDescription,rating,newListProduct));
		
		assertNotNull("Error when adding the shop", shop4);
		assertEquals("The shop's name has not been setted correctly", shopName, shop4.getName());
		assertEquals("The shop's url has not been setted correctly", url, shop4.getUrl());
		assertEquals("The shop's description has not been setted correctly", shopDescription, shop4.getDescription());
		assertEquals("The shop's rating has not been setted correctly", rating, shop4.getRating());
		assertEquals("The shop's products have not been setted correctly", newListProduct, shop4.getProducts());
	}
	
	@Test
	public void testDeletePlaylist() {
		boolean success = osr.deleteOnlineShop(shop2.getId());
		assertTrue("Error when deleting the shop", success);
		
		OnlineShop os = osr.getOnlineShop(shop2.getId());
		assertNull("The shop has not been deleted correctly", os);
	}

	@Test
	public void testAddSong() {
		if(product!=null) {
			boolean success = osr.addProduct(shop3.getId(), product.getId());
			assertTrue("Error when adding the product", success);
		}
	}

	@Test
	public void testRemoveSong() {
		boolean success = osr.removeProduct(shop3.getId(), product.getId());
		assertTrue("Error when removing the song", success);
	}

}