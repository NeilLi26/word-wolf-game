package persistence;

import model.WordPair;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest{
    @Test
    void testWriterInvalidFile() {
        try {
            List<WordPair> testList = new ArrayList<>();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
            System.out.println("testWriterInvalidFile passed");
        }
    }
/*
    @Test
    void testWriterEmptyWorkroom() {
        try {
            WorkRoom wr = new WorkRoom("My work room");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
            writer.open();
            writer.write(wr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkroom.json");
            wr = reader.read();
            assertEquals("My work room", wr.getName());
            assertEquals(0, wr.numThingies());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
*/

    @Test
    void testWriterGeneralWorkroom() {
        try {
            List<WordPair> testList = new ArrayList<>();
            testList.add(new WordPair("Ocean", "Pool"));
            testList.add(new WordPair("Tuxedo", "Military_Uniform"));
            testList.add(new WordPair("Java", "Python"));
            testList.add(new WordPair("Tea", "Coffee"));
            testList.add(new WordPair("Chocolate", "Vanilla"));
            testList.add(new WordPair("Mountain", "Skyscraper"));
            JsonWriter writer = new JsonWriter("./data/defaultWordPairs.json");
            writer.open();
            writer.write(testList);
            writer.close();

            JsonReader reader = new JsonReader("./data/defaultWordPairs.json");
            testList = reader.read();
            assertEquals(6, testList.size());
            checkWordPair("Ocean", "Pool", testList.get(0));
            checkWordPair("Tuxedo", "Military_Uniform", testList.get(1));
            checkWordPair("Java", "Python", testList.get(2));
            checkWordPair("Tea", "Coffee", testList.get(3));
            checkWordPair("Chocolate", "Vanilla", testList.get(4));
            checkWordPair("Mountain", "Skyscraper", testList.get(5));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }


}
