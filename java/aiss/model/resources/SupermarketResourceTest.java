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
		
		market = plr.addSupermarket(new Supermarket("Test market 1"));
		market2 = plr.addSupermarket(new Playlist("Test market 2"));
		market3 = plr.addSupermarket(new Playlist("Test market 3"));
		market4 = plr.addSupermarket(new Playlist("Test market 4"));
		
	
		product = sr.addProduct(new Song("Test name","Test price","Test availability"));
		if(product!=null)
			smr.addProduct(market.getId(), product.getId());
	}

	@AfterClass
	public static void tearDown() throws Exception {
		plr.deleteSupermarket(market.getId());
		plr.deleteSupermarket(market3.getId());
		plr.deleteSupermarket(market4.getId());
		if(product!=null)
			pr.deleteProduct(product.getId());
	}

	@Test
	public void testGetAll() {
		Collection<Supermarket> markets = smr.getAll(); 
		
		assertNotNull("The collection of playlists is null", playlists);
		
		// Show result
		System.out.println("Listing all playlists:");
		int i=1;
		for (Playlist pl : playlists) {
			System.out.println("Playlist " + i++ + " : " + pl.getName() + " (ID=" + pl.getId() + ")");
		}
		
	}

	@Test
	public void testGetPlaylist() {
		Playlist p = plr.getPlaylist(playlist.getId());
		
		assertEquals("The id of the playlists do not match", playlist.getId(), p.getId());
		assertEquals("The name of the playlists do not match", playlist.getName(), p.getName());
		
		// Show result
		System.out.println("Playlist id: " +  p.getId());
		System.out.println("Playlist name: " +  p.getName());

	}

	@Test
	public void testAddPlaylist() {
		String playlistName = "Add playlist test title";
		String playlistDescription = "Add playlist test description";
		
		playlist4 = plr.addPlaylist(new Playlist(playlistName,playlistDescription));
		
		assertNotNull("Error when adding the playlist", playlist4);
		assertEquals("The playlist's name has not been setted correctly", playlistName, playlist4.getName());
		assertEquals("The playlist's description has not been setted correctly", playlistDescription, playlist4.getDescription());
	}

	@Test
	public void testUpdatePlaylist() {
		String playlistName = "Updated playlist name";

		// Update playlist
		playlist.setName(playlistName);

		boolean success = plr.updatePlaylist(playlist);
		
		assertTrue("Error when updating the playlist", success);
		
		Playlist pl  = plr.getPlaylist(playlist.getId());
		assertEquals("The playlist's name has not been updated correctly", playlistName, pl.getName());

	}

	@Test
	public void testDeletePlaylist() {
		boolean success = plr.deletePlaylist(playlist2.getId());
		assertTrue("Error when deleting the playlist", success);
		
		Playlist pl = plr.getPlaylist(playlist2.getId());
		assertNull("The playlist has not been deleted correctly", pl);
	}

	@Test
	public void testAddSong() {
		if(song!=null) {
			boolean success = plr.addSong(playlist3.getId(), song.getId());
			assertTrue("Error when adding the song", success);
		}
	}

	@Test
	public void testRemoveSong() {
		//TODO
		boolean success = plr.removeSong(playlist3.getId(), song.getId());
		assertTrue("Error when removing the song", success);
	}

}
