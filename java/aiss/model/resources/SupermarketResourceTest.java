package aiss.model.resources;

import static org.junit.Assert.*;

import java.util.Collection;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.restlet.resource.ResourceException;

import aiss.model.Playlist;
import aiss.model.Song;

public class PlaylistResourceTest {

	static Supermarket market, market2, market3, market4;
	static Product product;
	static SupermarketResource smr = new SupermarketResource();
	static ProductResource pr = new ProductResource();
	
	@BeforeClass
	public static void setUp() throws Exception {
		
<<<<<<< HEAD
		market = smr.addSupermarket(new Supermarket("Test market 1"));
		market2 = smr.addSupermarket(new Supermarket("Test market 2"));
		market3 = smr.addSupermarket(new Supermarket("Test market 3"));
		market4 = smr.addSupermarket(new Supermarket("Test market 4"));
		
	
		product = sr.addProduct(new Product("Test name","Test price","Test availability"));
=======
		market = plr.addSupermarket(new Supermarket("Test market 1"));
		market2 = plr.addSupermarket(new Playlist("Test market 2"));
		market3 = plr.addSupermarket(new Playlist("Test market 3"));
		market4 = plr.addSupermarket(new Playlist("Test market 4"));
		
	
		product = sr.addProduct(new Song("Test name","Test price","Test availability"));
>>>>>>> e5bc5d9e950718ba9a6d16c8243b8e90648cad3b
		if(product!=null)
			smr.addProduct(market.getId(), product.getId());
	}

	@AfterClass
	public static void tearDown() throws Exception {
<<<<<<< HEAD
		smr.deleteSupermarket(market.getId());
		smr.deleteSupermarket(market3.getId());
		smr.deleteSupermarket(market4.getId());
=======
		plr.deleteSupermarket(market.getId());
		plr.deleteSupermarket(market3.getId());
		plr.deleteSupermarket(market4.getId());
>>>>>>> e5bc5d9e950718ba9a6d16c8243b8e90648cad3b
		if(product!=null)
			pr.deleteProduct(product.getId());
	}

	@Test
	public void testGetAll() {
		Collection<Supermarket> markets = smr.getAll(); 
		
		assertNotNull("The collection of supermarkets is null", markets);
		
		// Show result
		System.out.println("Listing all markets:");
		int i=1;
		for (Supermarket sm : markets) {
			System.out.println("Supermarket " + i++ + " : " + sm.getName() + " (ID=" + sm.getId() + ")");
		}
		
	}

	@Test
	public void testGetSupermarket() {
		Supermarket s = smr.getSupermarket(market.getId());
		
		assertEquals("The id of the markets do not match", market.getId(), s.getId());
		assertEquals("The names of the markets do not match", market.getName(), s.getName());
		assertEquals("The descriptions of the markets do not match", market.getDescription(), s.getDescription());
		
		// Show result
		System.out.println("Supermarket id: " +  s.getId());
		System.out.println("Supermarket name: " +  s.getName());
		System.out.println("Supermarket name: " +  s.getDescription());

	}

	@Test
	public void testAddSupermarket() {
		String marketName = "Add supermarket test name";
		String marketDescription = "Add supermarket test description";
		
		market4 = smr.addSupermarket(new Supermarket(marketName,marketDescription));
		
		assertNotNull("Error when adding the supermarket", market4);
		assertEquals("The supermarket's name has not been setted correctly", marketName, markett4.getName());
		assertEquals("The supermarket's description has not been setted correctly", marketDescription, market4.getDescription());
	}

	@Test
	public void testUpdateSupermarket() {
		String marketName = "Updated supermarket name";
		String marketDescription = "Updated supermarket description";

		// Update supermarket
		market.setName(marketName);

		boolean success = smr.updateSupermarket(market);
		
		assertTrue("Error when updating the supermarket", success);
		
		Supermarket sm  = smr.getSupermarket(market.getId());
		assertEquals("The supermarket's name has not been updated correctly", marketName, sm.getName());

	}

	@Test
	public void testDeleteSupermarket() {
		boolean success = smr.deleteSupermarket(market2.getId());
		assertTrue("Error when deleting the supermarket", success);
		
		Supermarket sm = smr.getSupermarket(market2.getId());
		assertNull("The supermarket has not been deleted correctly", sm);
	}

	@Test
	public void testAddProduct() {
		if(product!=null) {
			boolean success = smr.addProduct(market3.getId(), product.getId());
			assertTrue("Error when adding the product", success);
		}
	}

	@Test
	public void testRemoveProduct() {
		//TODO
		boolean success = smr.removeProduct(market3.getId(), product.getId());
		assertTrue("Error when removing the product", success);
	}

}
