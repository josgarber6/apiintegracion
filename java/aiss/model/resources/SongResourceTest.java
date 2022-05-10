package aiss.model.resources;

import static org.junit.Assert.*;
import java.util.Collection;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.restlet.resource.ResourceException;

import aiss.model.Song;

public class SongResourceTest {

	static Song song1, song2, song3, song4;
	static SongResource sr = new SongResource();
	
	@BeforeClass
	public static void setup() throws Exception {
		
		// Test song 1
		song1 = sr.addSong(new Song("Test title","Test artist","Test album","2016"));
		
		// Test song 2
		song2 = sr.addSong(new Song("Test title 2","Test artist 2","Test album 2","2017"));
		
	}

	@AfterClass
	public static void tearDown() throws Exception {
		sr.deleteSong(song1.getId());
		sr.deleteSong(song2.getId());
	}
	
	@Test
	public void testGetAll() {
		Collection<Song> songs = sr.getAll();
		
		assertNotNull("The collection of songs is null", songs);
		
		// Show result
		System.out.println("Listing all songs:");
		int i=1;
		for (Song s : songs) {
			System.out.println("Song " + i++ + " : " + s.getTitle() + " (ID=" + s.getId() + ")");
		}
	}

	@Test
	public void testGetSong() {
		//TODO
		Song song = sr.getSong(song2.getId());
		assertEquals("The id of the songs do not match", song2.getId(), song.getId());

		// Show result
		System.out.println("Song id: " +  song.getId());
		System.out.println("Song artist: " +  song.getArtist());
	}

	@Test
	public void testAddSong() {
		
		//TODO
		
		String songTitle = "Burn It Up";
		String songArtist = "Kaotix";
		String songAlbum = "Kaos Recordings Vol. 1";
		String songYear = "2022";
		
		song4 = sr.addSong(new Song(songTitle,songArtist, songAlbum, songYear));
		
		assertNotNull("Error when adding the song", song4);
		assertEquals("The song's name has not been setted correctly", songTitle, song4.getTitle());
		assertEquals("The song's description has not been setted correctly", songAlbum, song4.getAlbum());

	}

	@Test
	public void testUpdateSong() {
		
		String songTitle = "El patio de mi casa";
		String songArtist = "Dr.Kenobi";
		String songAlbum = "Update song test album";
		String songYear = "1995";
		
		// Update song
		song1.setTitle(songTitle);
		song1.setArtist(songArtist);
		song1.setAlbum(songAlbum);
		song1.setYear(songYear);
		
		boolean success = sr.updateSong(song1);
		
		assertTrue("Error when updating the song", success);
		
		Song song  = sr.getSong(song1.getId());
		
		if (success) {
			System.out.println("Canción actualizada correctamente");
		}
		assertEquals("The song's title has not been updated correctly", songTitle, song.getTitle());
		assertEquals("The song's artist has not been updated correctly", songArtist, song.getArtist());
		assertEquals("The song's album has not been updated correctly", songAlbum, song.getAlbum());
		assertEquals("The song's year has not been updated correctly", songYear, song.getYear());
	}
//
//	@Test(expected = ResourceException.class)
//	public void testDeleteSong() {
//		
//		// Delete songs
//		boolean success = sr.deleteSong(song2.getId());
//		
//		assertTrue("Error when deleting the song", success);
//		
//		Song song  = sr.getSong(song2.getId());
//		assertNull("The song has not been deleted correctly", song);
//	}

}
