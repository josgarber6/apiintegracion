package aiss.api.consuming.resources;

import java.util.Arrays;
import java.util.Collection;

import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

import aiss.model.consuming.Product;

public class ProductResource {
	
	private String uri = "https://the-prices-comparator-api.ew.r.appspot.com/api/products";

	
	public Collection<Product> getAll() {
		ClientResource cr = null;
		Product [] products = null;
		try {
			cr = new ClientResource(uri);
			products = cr.get(Product[].class);
			
		} catch (ResourceException re) {
			System.err.println("Error when retrieving all products: " + cr.getResponse().getStatus());
			throw re;
		}
		return Arrays.asList(products);
	}
	
	public Product getProduct(String productId) {
		ClientResource cr = null;
		Product product = null;
		try {
			cr = new ClientResource(uri + "/" + productId);
			product = cr.get(Product.class);
		} catch (ResourceException re) {
			System.err.println("Error when retrieving the product: " + cr.getResponse().getStatus());
			throw re;
		}
		return product;
	}
	
	public Product addProduct(Product product) {
		ClientResource cr = null;
		Product resultProduct = null;
		try {
			cr = new ClientResource(uri);
			cr.setEntityBuffering(true);		// Needed for using RESTlet from JUnit tests
			resultProduct = cr.post(product,Product.class);
		} catch (ResourceException re) {
			System.err.println("Error when adding the product: " + cr.getResponse().getStatus());
		}
		return resultProduct;
	}
	
	public boolean updateProduct(Product product) {
		ClientResource cr = null;
		boolean success = true;
		try {
			cr = new ClientResource(uri);
			cr.setEntityBuffering(true);		// Needed for using RESTlet from JUnit tests
			cr.put(product);
		} catch (ResourceException re) {
			System.err.println("Error when updating the product: " + cr.getResponse().getStatus());
			success = false;
		}
		return success;
	}
	

	public boolean deleteProduct(String productId) {
		ClientResource cr = null;
		boolean success = true;
		try {
			cr = new ClientResource(uri + "/" + productId);
			cr.setEntityBuffering(true);		// Needed for using RESTlet from JUnit tests
			cr.delete();
		} catch (ResourceException re) {
			System.err.println("Error when deleting the product: " + cr.getResponse().getStatus());
			success = false;
			throw re;
		}
		return success;
	}

}
