import Dimitruk.Music.music.Playlist;
import Dimitruk.Music.music.Song;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlaylistTest {
    @Test
    public void testEmptyPlaylist() {
        Playlist playlist = new Playlist();
        assertTrue (playlist.isEmpty());
    }
    @Test
    public void testSingleElement() {
        Playlist playlist = new Playlist();
        playlist.add(new Song("Daria Zawiałow", "Pank fu", 183));
        assertEquals(1, playlist.size());
    }

    @Test
    public void testSameElement(){
        Playlist playlist = new Playlist();
        Song song = new Song("Daria Zawiałow", "Pank fu", 183);
        Song sameSong = new Song("Daria Zawiałow", "Pank fu", 183);
        playlist.add(song);
        assertTrue(playlist.contains(song));
        assertEquals(playlist.getFirst(), sameSong);
    }

    @Test
    public void testEqualElement(){
        Playlist playlist = new Playlist();
        Song song = new Song("Daria Zawiałow", "Pank fu", 183);
        Song sameSong = new Song("Daria Zawiałow", "Pank fu", 183);
        playlist.add(song);
        assertEquals(playlist.getFirst(), sameSong);
        assertTrue(playlist.contains(song));
    }

    @Test
    public void testAtSecond() {
        Playlist playlist = new Playlist();
        Song song1 = new Song("Sabaton", "Soldier of Heaven", 100);
        Song song2 = new Song("Schwarzer Engel", "Viva la musica", 150);
        Song song3 = new Song("Rammstein", "Spring", 200);
        playlist.add(song1);
        playlist.add(song2);
        playlist.add(song3);
        assertEquals(song1, playlist.atSecond(0));
        assertEquals(song1, playlist.atSecond(50));
        assertEquals(song2, playlist.atSecond(200));
        assertEquals(song3, playlist.atSecond(300));
    }
    private IndexOutOfBoundsException doesThrowExceptionCommon(int seconds) {
        Playlist playlist = new Playlist();
        Song song1 = new Song("Sabaton", "Soldier of Heaven", 100);
        Song song2 = new Song("Schwarzer Engel", "Viva la musica", 150);
        Song song3 = new Song("Rammstein", "Spring", 200);
        playlist.add(song1);
        playlist.add(song2);
        playlist.add(song3);
        return assertThrows(IndexOutOfBoundsException.class, ()->playlist.atSecond(seconds));
    }
    @Test
    public void testDoesThrowException() {
        assertEquals("Zbyt duży czas", doesThrowExceptionCommon(1000).getMessage());
    }

    @Test
    public void testDoesThrowNegativeException() {
        assertEquals("Ujemny czas", doesThrowExceptionCommon(-1000).getMessage());
    }
}
