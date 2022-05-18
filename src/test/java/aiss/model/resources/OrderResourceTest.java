package aiss.model.resources;

import static org.junit.Assert.*;

import java.util.Collection;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.restlet.resource.ResourceException;

import aiss.api.resources.OrderResource;
import aiss.api.resources.ProductResource;
import aiss.model.Order;
import aiss.model.Playlist;
import aiss.model.Product;
import aiss.model.Song;

public class OrderResourceTest {

	static Order order, order2, order3, order4;
	static Product product;
	static OrderResource or = new OrderResource();
	static ProductResource pr = new ProductResource();
	
	@BeforeClass
	public static void setUp() throws Exception {
		
		order = or.addOrder(new Order("Test list 1"));
		order2 = or.addOrder(new Order("Test list 2"));
		order3 = or.addOrder(new Order("Test list 3"));
		order4 = or.addOrder(new Order("Test list 4"));
	
		product = pr.addProduct(new Product("Test name","20,00€","true"));
		if(product!=null)
			or.addProduct(order.getId(), product.getId());
	}

	@AfterClass
	public static void tearDown() throws Exception {
		or.deleteOrder(order.getId());
		or.deleteOrder(order3.getId());
		or.deleteOrder(order4.getId());
		if(product!=null)
			pr.deleteProduct(product.getId());
	}

	@Test
	public void testGetAll() {
		Collection<Order> orders = or.getAll(); 
		
		assertNotNull("The collection of orders is null", orders);
		
		// Show result
		System.out.println("Listing all orders:");
		int i=1;
		for (Order o : orders) {
			System.out.println("Order " + i++ + " : " + " (ID=" + pl.getId() + ")");
		}
		
	}

	@Test
	public void testGetOrder() {
		Order o = or.getOrder(order.getId());
		
		assertEquals("The id of the orders do not match", order.getId(), o.getId());
		assertEquals("The products of the orders do not match", order.getProducts(), o.getProducts());
		
		// Show result
		System.out.println("Order id: " +  o.getId());
		System.out.println("Order products: " +  o.getProducts());

	}

	@Test
	public void testAddOrder() {
		String orderId = "Add order test id";
		List<Product> orderProducts = new ArrayList<>();
		orderProducts.add(product);
		
		order4 = or.addOrder(new Order(orderId,orderProducts));
		
		assertNotNull("Error when adding the order", order4);
		assertEquals("The order's name has not been setted correctly", orderId, order4.getId());
		assertEquals("The order's description has not been setted correctly", orderProducts, order4.getProducts());
	}

	@Test
	public void testUpdatePlaylist() {
		String orderDate = "Updated order date";

		// Update order
		order.setDate(orderDate);

		boolean success = or.updateOrder(order);
		
		assertTrue("Error when updating the order", success);
		
		Order o = or.getOrder(order.getId());
		assertEquals("The order's name has not been updated correctly", orderDate, o.getDate());

	}

	@Test
	public void testDeleteOrder() {
		boolean success = or.deleteOrder(order2.getId());
		assertTrue("Error when deleting the order", success);
		
		Order o = or.getOrder(order2.getId());
		assertNull("The order has not been deleted correctly", o);
	}

	@Test
	public void testAddProduct() {
		if(product!=null) {
			boolean success = or.addProduct(order3.getId(), product.getId());
			assertTrue("Error when adding the product", success);
		}
	}

	@Test
	public void testRemoveProduct() {
		boolean success = or.removeProduct(order3.getId(), product.getId());
		assertTrue("Error when removing the product", success);
	}

}
