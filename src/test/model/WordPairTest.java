package model;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class WordPairTest {
    private WordPair testPair;

    @BeforeEach
    void initTestPair() {
        testPair = new WordPair("a", "b");
    }

    @Test
    void gettersTest() {
        assertEquals(testPair.getFirstWord(), "a");
        assertEquals(testPair.getSecondWord(), "b");

        testPair = new WordPair("String", "Char");

        assertEquals(testPair.getFirstWord(), "String");
        assertEquals(testPair.getSecondWord(), "Char");

        testPair = new WordPair("Trying stuff out", "Rejecting Everything");

        assertEquals(testPair.getFirstWord(), "Trying stuff out");
        assertEquals(testPair.getSecondWord(), "Rejecting Everything");
    }
}
