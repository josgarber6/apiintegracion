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
		
		Product one=new Product();
		one.setTitle("One");
		one.setArtist("U2");
		one.setYear("1992");
		one.setAlbum("Achtung Baby");
		addProduct(one);
		
		Product losingMyReligion=new Product();
		losingMyReligion.setTitle("Losing my Religion");
		losingMyReligion.setArtist("REM");
		losingMyReligion.setYear("1991");
		losingMyReligion.setAlbum("Out of Time");
		addProduct(losingMyReligion);
		
		Product smellLikeTeenSpirit=new Product();
		smellLikeTeenSpirit.setTitle("Smell Like Teen Spirit");
		smellLikeTeenSpirit.setArtist("Nirvana");
		smellLikeTeenSpirit.setAlbum("Nevermind");
		smellLikeTeenSpirit.setYear("1991");
		addProduct(smellLikeTeenSpirit);
		
		Product gotye=new Product();
		gotye.setTitle("Someone that I used to know");
		gotye.setArtist("Gotye");
		gotye.setYear("2011");
		gotye.setAlbum("Making Mirrors");
		addProduct(gotye);
		
		// Create playlists
		Supermarket japlaylist=new Supermarket();
		japlaylist.setName("AISSPlayList");
		japlaylist.setDescription("AISS PlayList");
		addPlaylist(japlaylist);
		
		Supermarket playlist = new Supermarket();
		playlist.setName("Favourites");
		playlist.setDescription("A sample playlist");
		addPlaylist(playlist);
		
		// Add songs to playlists
		addProduct(japlaylist.getId(), patatas.getId());
		addProduct(japlaylist.getId(), one.getId());
		addProduct(japlaylist.getId(), smellLikeTeenSpirit.getId());
		addProduct(japlaylist.getId(), losingMyReligion.getId());
		
		addProduct(playlist.getId(), losingMyReligion.getId());
		addProduct(playlist.getId(), gotye.getId());
	}
	
	// Playlist related operations
	@Override
	public void addPlaylist(Supermarket p) {
		String id = "p" + index++;	
		p.setId(id);
		playlistMap.put(id,p);
	}
	
	@Override
	public Collection<Supermarket> getAllPlaylists() {
			return playlistMap.values();
	}

	@Override
	public Supermarket getPlaylist(String id) {
		return playlistMap.get(id);
	}
	
	@Override
	public void updatePlaylist(Supermarket p) {
		playlistMap.put(p.getId(),p);
	}

	@Override
	public void deletePlaylist(String id) {	
		playlistMap.remove(id);
	}
	

	@Override
	public void addProduct(String playlistId, String songId) {
		Supermarket playlist = getPlaylist(playlistId);
		playlist.addProduct(songMap.get(songId));
	}

	@Override
	public Collection<Product> getAll(String playlistId) {
		return getPlaylist(playlistId).getProducts();
	}

	@Override
	public void removeProduct(String playlistId, String songId) {
		getPlaylist(playlistId).deleteProduct(songId);
	}

	
	// Song related operations
	
	@Override
	public void addProduct(Product s) {
		String id = "s" + index++;
		s.setId(id);
		songMap.put(id, s);
	}
	
	@Override
	public Collection<Product> getAllProducts() {
			return songMap.values();
	}

	@Override
	public Product getProduct(String songId) {
		return songMap.get(songId);
	}

	@Override
	public void updateProduct(Product s) {
		Product song = songMap.get(s.getId());
		song.setTitle(s.getTitle());
		song.setAlbum(s.getAlbum());
		song.setArtist(s.getArtist());
		song.setYear(s.getYear());
	}

	@Override
	public void deleteProduct(String songId) {
		songMap.remove(songId);
	}
	
}
