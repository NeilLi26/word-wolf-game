package persistence;

import model.Player;
import model.PlayersAndWordPairs;
import model.WordPair;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest{
    private List<Player> testPlayers;
    private List<WordPair> testList;
    private PlayersAndWordPairs testPlayersAndWordPairs;

    @Test
    void testWriterInvalidFile() {
        try {
            testList = new ArrayList<>();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
            System.out.println("testWriterInvalidFile passed");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            testList = new ArrayList<>();
            testList.add(new WordPair("Ocean", "Pool"));
            testList.add(new WordPair("Tuxedo", "Military_Uniform"));
            testList.add(new WordPair("Java", "Python"));
            testList.add(new WordPair("Tea", "Coffee"));
            testList.add(new WordPair("Chocolate", "Vanilla"));
            testList.add(new WordPair("Mountain", "Skyscraper"));
            testPlayers = new ArrayList<>();
            testPlayers.add(new Player("a"));
            testPlayers.add(new Player("b"));
            testPlayers.add(new Player("c"));
            testPlayers.add(new Player("d"));
            testPlayers.add(new Player("e"));
            testPlayersAndWordPairs = new PlayersAndWordPairs(testPlayers, testList);
            JsonWriter writer = new JsonWriter("./data/defaultWordPairs.json");
            writer.open();
            writer.write(testPlayersAndWordPairs);
            writer.close();

            JsonReader reader = new JsonReader("./data/defaultWordPairs.json");
            testPlayersAndWordPairs = reader.read();
            testPlayers = testPlayersAndWordPairs.getPlayers();
            testList = testPlayersAndWordPairs.getWordPairs();
            assertEquals(6, testList.size());
            checkWordPair("Ocean", "Pool", testList.get(0));
            checkWordPair("Tuxedo", "Military_Uniform", testList.get(1));
            checkWordPair("Java", "Python", testList.get(2));
            checkWordPair("Tea", "Coffee", testList.get(3));
            checkWordPair("Chocolate", "Vanilla", testList.get(4));
            checkWordPair("Mountain", "Skyscraper", testList.get(5));
            checkPlayer("a", testPlayers.get(0));
            checkPlayer("b", testPlayers.get(1));
            checkPlayer("c", testPlayers.get(2));
            checkPlayer("d", testPlayers.get(3));
            checkPlayer("e", testPlayers.get(4));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }


}
