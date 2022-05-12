package aiss.api;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import aiss.api.resources.OrderResource;
import aiss.api.resources.ProductResource;
import aiss.api.resources.SupermarketResource;


public class MarketApplication extends Application {
	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> classes = new HashSet<Class<?>>();

	// Loads all resources that are implemented in the application
	// so that they can be found by RESTEasy.
	public MarketApplication() {

		singletons.add(SupermarketResource.getInstance());
		singletons.add(ProductResource.getInstance());
		singletons.add(OrderResource.getInstance());
	}

	@Override
	public Set<Class<?>> getClasses() {
		return classes;
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
}
