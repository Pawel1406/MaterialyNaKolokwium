import database.DatabaseConnection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.sql.SQLException;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class SongTest {
    @Test
    public void testRead() throws SQLException {
        DatabaseConnection.connect("songs.db", "");
        Optional<Song> song = Song.Persistence.read(27);
        Song equalsong = new Song("Janis Joplin", "Me and Bobby McGee", 271);
        assertEquals(equalsong, song.get());
    }
    @Test
    public void testReadFail() throws SQLException {
        DatabaseConnection.connect("songs.db", "");
        Optional<Song> song = Song.Persistence.read(171);
        assertTrue(song.isEmpty());
    }

    private static Stream<Arguments> args() {
        return Stream.of(
                arguments(41, "The Beach Boys","California Girls",165),
                arguments(42, "The Temptations","Ain't Too Proud to Beg",154),
                arguments(43, "The Four Tops","Reach Out I'll Be There",181)
        );
    }

    @ParameterizedTest
    @MethodSource("args")
    public void testReadMany(int id, String artist, String title, int length) throws SQLException {
        DatabaseConnection.connect("songs.db", "");
        Optional<Song> song = Song.Persistence.read(id);
        Song equalsong = new Song(artist, title, length);
        assertEquals(equalsong, song.get());
    }

}
