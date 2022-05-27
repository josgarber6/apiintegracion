package aiss.api.consuming.resources;

import java.util.Arrays;
import java.util.Collection;

import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

import aiss.model.consuming.OnlineShop;

public class OnlineShopResource {
	
	private String uri = "https://the-prices-comparator-api.ew.r.appspot.com/api/shops";
	
	public Collection<OnlineShop> getAll() {
		
		ClientResource cr = null;
		OnlineShop [] lists = null;
		try {
			cr = new ClientResource(uri);
			lists = cr.get(OnlineShop[].class);
			
		} catch (ResourceException re) {
			System.err.println("Error when retrieving the collections of online shops: " + cr.getResponse().getStatus());
		}
		
		return Arrays.asList(lists);
	}
	
	public OnlineShop getOnlineShop(String onlineShopId) {
		
		ClientResource cr = null;
		OnlineShop list = null;
		try {
			cr = new ClientResource(uri + "/" + onlineShopId);
			list = cr.get(OnlineShop.class);
			
		} catch (ResourceException re) {
			System.err.println("Error when retrieving the online shop: " + cr.getResponse().getStatus());
		}
		
		return list;

	}
	
	public OnlineShop addOnlineShop(OnlineShop os) {
		
		ClientResource cr = null;
		OnlineShop resultOnlineShop = null;
		try {
			cr = new ClientResource(uri);
			cr.setEntityBuffering(true);		// Needed for using RESTlet from JUnit tests
			resultOnlineShop = cr.post(os,OnlineShop.class);
			
		} catch (ResourceException re) {
			System.err.println("Error when adding the online shop: " + cr.getResponse().getStatus());
		}
		
		return resultOnlineShop;
	}
	
	public boolean updatePlaylist(OnlineShop os) {
		ClientResource cr = null;
		boolean success = true;
		try {
			cr = new ClientResource(uri);
			cr.setEntityBuffering(true);		// Needed for using RESTlet from JUnit tests
			cr.put(os);
			
			
		} catch (ResourceException re) {
			System.err.println("Error when updating the online shop: " + cr.getResponse().getStatus());
			success = false;
		}
		
		return success;
	}
	
	public boolean deleteOnlineShop(String onlineShopId) {
		ClientResource cr = null;
		boolean success = true;
		try {
			cr = new ClientResource(uri + "/" + onlineShopId);
			cr.setEntityBuffering(true);		// Needed for using RESTlet from JUnit tests
			cr.delete();
			
		} catch (ResourceException re) {
			System.err.println("Error when deleting the online shop: " + cr.getResponse().getStatus());
			success = false;
		}
		
		return success;
	}
	
	public boolean addProduct(String onlineShopId, String productId) {
		// Use	cr.post(" ") to avoid RESTlet crashing
		ClientResource cr = null;
		boolean success = true;
		try {
			cr = new ClientResource(uri + "/" + onlineShopId + "/" + productId);
			cr.setEntityBuffering(true);
			cr.post(" ");
		} catch (ResourceException re) {
			System.err.println("Error when adding the product into the online shop: " + cr.getResponse().getStatus());
			success = false;
		}
		return success;
		
	}
	
	public boolean removeProduct(String onlineShopId, String productId) {
		ClientResource cr = null;
		boolean success = true;
		try {
			cr = new ClientResource(uri + "/" + onlineShopId + "/" + productId);
			cr.setEntityBuffering(true);
			cr.delete();
		} catch (ResourceException re) {
			System.err.println("Error when removing the product from the online shop: " + cr.getResponse().getStatus());
			success = false;
		}
		return success;
	}

}
