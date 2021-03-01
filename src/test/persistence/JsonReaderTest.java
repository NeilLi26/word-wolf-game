package persistence;

import model.Player;
import model.PlayersAndWordPairs;
import model.WordPair;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            PlayersAndWordPairs playersAndWordPairs = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
            System.out.println("Test Passed");
        }
    }

    @Test
    void testReaderGeneralWordPair() {
        JsonReader reader = new JsonReader("./data/defaultWordPairs.json");
        try {
            PlayersAndWordPairs playersAndWordPairs = reader.read();
            List<WordPair> wordPairList = playersAndWordPairs.getWordPairs();
            List<Player> testPlayers = playersAndWordPairs.getPlayers();
            assertEquals(6, wordPairList.size());
            checkWordPair("Ocean", "Pool", wordPairList.get(0));
            checkWordPair("Tuxedo", "Military_Uniform", wordPairList.get(1));
            checkWordPair("Java", "Python", wordPairList.get(2));
            checkWordPair("Tea", "Coffee", wordPairList.get(3));
            checkWordPair("Chocolate", "Vanilla", wordPairList.get(4));
            checkWordPair("Mountain", "Skyscraper", wordPairList.get(5));
            checkPlayer("a", testPlayers.get(0));
            checkPlayer("b", testPlayers.get(1));
            checkPlayer("c", testPlayers.get(2));
            checkPlayer("d", testPlayers.get(3));
            checkPlayer("e", testPlayers.get(4));

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
