//package aiss.model.resources;
//
//import static org.junit.Assert.*;
//
//import java.util.Collection;
//
//import org.jboss.resteasy.spi.BadRequestException;
//import org.junit.AfterClass;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.restlet.resource.ResourceException;
//
//import aiss.api.resources.MarketResource;
//import aiss.api.resources.OrderResource;
//import aiss.api.resources.ProductResource;
//import aiss.model.Market;
//import aiss.model.Order;
//import aiss.model.Product;
//import aiss.model.User;
//
//public class MarketResourceTest {
//
//	static Market market1, market2, market3, market4, market5;
//	static Product product1, product2, product3, product4, product5, product6, product7, product8;
//	static Order order1, order2, order3, order4;
//	static User user;
//	static MarketResource SourceMarket = MarketResource.getInstance();
//	static ProductResource SourceProduct = ProductResource.getInstance();
//	static OrderResource SourceOrder = OrderResource.getInstance();
//	
//	@BeforeClass
//	public static void setUp() throws Exception {
//		
//		user = new User("u1", "Maria","aa@aiss.com","secret","baños, 33", "aaa");
//	
//		market1 = new Market("Mercadona", "Supermercados Mercadona");
//		market2 = new Market("Dia", "Supermercados Dia");
//		market5 = new Market("Mercadona", null);
//		
//		SourceMarket.addMarket(null, market1); SourceMarket.addMarket(null, market2);  SourceMarket.addMarket(null, market5);
//		
//		product1 = new Product(market1.getId(), "Patatas", "1.50", "200", "2022-09-30", "3", "FOOD");
//		product2 = new Product(market1.getId(), "Macarrones", "2.50", "300", "2022-12-30", "3", "FOOD");
//		product3 = new Product(market1.getId(), "PlayStation", "499.99", "5", "null", "4", "LEISURE");
//		product4 = new Product(market1.getId(), "Camiseta Nike", "35.34", "75", "null", "2", "CLOTHES");
//		product5 = new Product(market2.getId(), "Patatas", "2.50", "65", "2023-01-07", "4", "FOOD");
//		product6 = new Product(market2.getId(), "Cebollas", "1.25", "148", "2022-08-30", "5", "FOOD");
//		product7 = new Product(market2.getId(), "pepinillos", "2", "93", "2022-06-30", "2", "FOOD");
//		product8 = new Product(market2.getId(), "Call of Duty V", "45.50", "101", "null", "1", "LEISURE");
//		
//		SourceProduct.addProduct(null, product1); SourceProduct.addProduct(null, product2);
//		SourceProduct.addProduct(null, product3); SourceProduct.addProduct(null, product4);
//		SourceProduct.addProduct(null, product5); SourceProduct.addProduct(null, product6);
//		SourceProduct.addProduct(null, product7); SourceProduct.addProduct(null, product8);
//		
//
//		order1 = new Order("Avenida de la Reina Mercerdes, s/n", "5.00", market1.getId());
//		order2 = new Order("Calle Sierpes", "5.00", market1.getId());
//		order3 = new Order("Avenida Pedro Porro", "10.00", market1.getId());
//		order4 = new Order("Murcia", "10.00", market1.getId());
//		order1.setUser(user); order2.setUser(user); order3.setUser(user); order4.setUser(user);
//		
//		SourceOrder.addOrder(null, "aaa", order1); SourceOrder.addOrder(null, "aaa", order2);
//		SourceOrder.addOrder(null, "aaa", order3); SourceOrder.addOrder(null, "aaa", order4);
//		
//	}
//	
//
//	@AfterClass
//	public static void tearDown() throws Exception {
//
//	}
//	
//	@Test(expected = BadRequestException.class)
//	public void testAddMarketWithNameEqualNullShouldThrowBadRequestException() {
//		market3 = new Market(null, "Grandes almacenes");
//		SourceMarket.addMarket(null, market3);
//	}
//	
//	@Test(expected = BadRequestException.class)
//	public void testAddMarketWithNameEqualEmptyStringShouldThrowBadRequestException() {
//		market4 = new Market("", "");
//		SourceMarket.addMarket(null, market4);
//	}
//
//	@Test
//	public void testGetAll() {
//		Collection<Market> markets1 = SourceMarket.getAll(null, null, null, true); // test devuelve mercad0s con orders vacios.
//		Collection<Market> markets2 = SourceMarket.getAll(null, null, true, null); // test devuelve mercados con productos vacios.
//		Collection<Market> markets3 = SourceMarket.getAll(null, null, false, false); // test devuelve mercados con orders y productos llenos
//		Collection<Market> markets4 = SourceMarket.getAll(null, "Mercado", false, null); // test devuelve mercados con name "Mercadona" y productos
//		Collection<Market> markets5 = SourceMarket.getAll(null, "Dia", null, false); // test devuelve mercados con name "Dia" y pedidos
//		
//		assertNotNull("The collection of markets is null", markets1);
//		assertNotNull("The collection of markets is null", markets2);
//		assertNotNull("The collection of markets is not null", markets3);
//		assertNotNull("The collection of markets is null", markets4);
//		assertNull("The collection of markets is not null", markets5);
//		
//		// Show result
//		System.out.println("Listing all markets:");
//		int i = 1;
//		int j = 1;
//		/*
//		 * Solo muestro market2 y market4
//		 */
//		for (Market m : markets2) {
//			System.out.println("Market " + i++ +" : NAME= " + m.getName() + " ID=" + m.getId() + " DESCRIPTION= "+ m.getDescription());
//			for(Order o : m.getOrders()) {
//				System.out.println("Order " + j++ +" : IDMARKET= " + o.getIdMarket() + " ID=" + o.getId() + " ADDRESS= "+ o.getAddress() + " SHIPPINGCOSTS= "+ o.getShippingCosts());
//			}
//		}
//		i = 1;
//		j = 1;
//		for (Market m : markets4) {
//			System.out.println("Market " + i++ +" : NAME= " + m.getName() + " ID=" + m.getId() + " DESCRIPTION= "+ m.getDescription());
//			for(Product p : m.getProducts()) {
//				System.out.println("Order " + j++ +" : NAME= " + p.getName() + " ID=" + p.getId() + " IDMARKET=" + p.getIdMarket() +" EXPIRATION_DATE= "+ p.getExpirationDate() + " PRICE= "+ p.getPrice());
//			}
//		}
//		
//	}
//
////	@Test
////	public void testGetSupermarket() {
////		Supermarket s = smr.getSupermarket(market.getId());
////		
////		assertEquals("The id of the markets do not match", market.getId(), s.getId());
////		assertEquals("The names of the markets do not match", market.getName(), s.getName());
////		assertEquals("The descriptions of the markets do not match", market.getDescription(), s.getDescription());
////		
////		// Show result
////		System.out.println("Supermarket id: " +  s.getId());
////		System.out.println("Supermarket name: " +  s.getName());
////		System.out.println("Supermarket name: " +  s.getDescription());
////
////	}
////
////	@Test
////	public void testAddSupermarket() {
////		String marketName = "Add supermarket test name";
////		String marketDescription = "Add supermarket test description";
////		
////		market4 = smr.addSupermarket(new Supermarket(marketName,marketDescription));
////		
////		assertNotNull("Error when adding the supermarket", market4);
////		assertEquals("The supermarket's name has not been setted correctly", marketName, markett4.getName());
////		assertEquals("The supermarket's description has not been setted correctly", marketDescription, market4.getDescription());
////	}
////
////	@Test
////	public void testUpdateSupermarket() {
////		String marketName = "Updated supermarket name";
////		String marketDescription = "Updated supermarket description";
////
////		// Update supermarket
////		market.setName(marketName);
////
////		boolean success = smr.updateSupermarket(market);
////		
////		assertTrue("Error when updating the supermarket", success);
////		
////		Supermarket sm  = smr.getSupermarket(market.getId());
////		assertEquals("The supermarket's name has not been updated correctly", marketName, sm.getName());
////
////	}
////
////	@Test
////	public void testDeleteSupermarket() {
////		boolean success = smr.deleteSupermarket(market2.getId());
////		assertTrue("Error when deleting the supermarket", success);
////		
////		Supermarket sm = smr.getSupermarket(market2.getId());
////		assertNull("The supermarket has not been deleted correctly", sm);
////	}
////
////	@Test
////	public void testAddProduct() {
////		if(product!=null) {
////			boolean success = smr.addProduct(market3.getId(), product.getId());
////			assertTrue("Error when adding the product", success);
////		}
////	}
////
////	@Test
////	public void testRemoveProduct() {
////		//TODO
////		boolean success = smr.removeProduct(market3.getId(), product.getId());
////		assertTrue("Error when removing the product", success);
////	}
//
//}
