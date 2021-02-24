package persistence;

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
            List<WordPair> wordPairList = reader.read();
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
            List<WordPair> wordPairList = reader.read();
            assertEquals(6, wordPairList.size());
            checkWordPair("Ocean", "Pool", wordPairList.get(0));
            checkWordPair("Tuxedo", "Military_Uniform", wordPairList.get(1));
            checkWordPair("Java", "Python", wordPairList.get(2));
            checkWordPair("Tea", "Coffee", wordPairList.get(3));
            checkWordPair("Chocolate", "Vanilla", wordPairList.get(4));
            checkWordPair("Mountain", "Skyscraper", wordPairList.get(5));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
