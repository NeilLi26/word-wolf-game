package persistence;

import model.WordPair;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkWordPair(String firstWord, String secondWord, WordPair wordPair) {
        assertEquals(firstWord, wordPair.getFirstWord());
        assertEquals(secondWord, wordPair.getSecondWord());
    }
}
