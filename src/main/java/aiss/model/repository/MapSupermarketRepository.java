package aiss.model.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import aiss.model.Supermarket;
import aiss.model.Product;


public class MapSupermarketRepository implements SupermarketRepository{

	Map<String, Supermarket> supermarketMap;
	Map<String, Product> productMap;
	private static MapSupermarketRepository instance=null;
	private int index=0;			// Index to create playlists and songs' identifiers.
	
	
	public static MapSupermarketRepository getInstance() {
		
		if (instance==null) {
			instance = new MapSupermarketRepository();
			instance.init();
		}
		
		return instance;
	}
	
	public void init() {
		
		supermarketMap = new HashMap<String,Supermarket>();
		productMap = new HashMap<String,Product>();
		
		// Create songs
		Product patatas=new Product();
		patatas.setName("Patatas");
		patatas.setPrice("1,50€/kg");
		patatas.setAvailability("20");
		addProduct(patatas);
		
		// Create playlists
		Supermarket mercadona=new Supermarket();
		mercadona.setName("Mercadona");
		mercadona.setDescription("Supermercados Mercadona");
		addSupermarket(mercadona);
		
		Supermarket dia = new Supermarket();
		dia.setName("Dia");
		dia.setDescription("Supermercados Dia");
		addSupermarket(dia);
		
		// Add songs to playlists
		addProduct(mercadona.getId(), patatas.getId());
		
		addProduct(dia.getId(), losingMyReligion.getId());
		addProduct(dia.getId(), gotye.getId());
	}
	
	// Playlist related operations
	@Override
	public void addSupermarket(Supermarket s) {
		String id = "s" + index++;	
		s.setId(id);
		supermarketMap.put(id,s);
	}
	
	@Override
	public Collection<Supermarket> getAllSupermarkets() {
			return supermarketMap.values();
	}

	@Override
	public Supermarket getSupermarket(String id) {
		return supermarketMap.get(id);
	}
	
	@Override
	public void updateSupermarket(Supermarket p) {
		supermarketMap.put(p.getId(),p);
	}

	@Override
	public void deleteSupermarket(String id) {	
		supermarketMap.remove(id);
	}
	

	@Override
	public void addProduct(String supermarketId, String songId) {
		Supermarket supermarket = getSupermarket(supermarketId);
		supermarket.addProduct(productMap.get(songId));
	}

	@Override
	public Collection<Product> getAll(String supermarketId) {
		return getSupermarket(supermarketId).getProducts();
	}

	@Override
	public void removeProduct(String supermarketId, String songId) {
		getSupermarket(supermarketId).deleteProduct(songId);
	}

	
	// Song related operations
	
	@Override
	public void addProduct(Product p) {
		String id = "p" + index++;
		p.setId(id);
		productMap.put(id, p);
	}
	
	@Override
	public Collection<Product> getAllProducts() {
			return productMap.values();
	}

	@Override
	public Product getProduct(String productId) {
		return productMap.get(productId);
	}

	@Override
	public void updateProduct(Product p) {
		Product product = productMap.get(p.getId());
		product.setName(p.getName());
		product.setPrice(p.getPrice());
		product.setAvailability(p.getAvailability());
	}

	@Override
	public void deleteProduct(String productId) {
		productMap.remove(productId);
	}
	
}
