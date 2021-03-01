package model;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlayersAndWordPairsTest {
    private List<Player> testPlayers;
    private List<WordPair> testWordPairs;
    private PlayersAndWordPairs testPlayersAndWords;

    @Test
    void emptyListGettersTest() {
        testPlayers = new ArrayList<>();
        testWordPairs = new ArrayList<>();
        testPlayersAndWords = new PlayersAndWordPairs(testPlayers, testWordPairs);

        assertEquals(testPlayersAndWords.getPlayers(), testPlayers);
        assertEquals(testPlayersAndWords.getWordPairs(), testWordPairs);
    }

    @Test
    void populatedListGettersTest() {
        testPlayers = new ArrayList<>();
        testPlayers.add(new Player("a"));
        testPlayers.add(new Player("b"));
        testPlayers.add(new Player("c"));
        testPlayers.add(new Player("d"));
        testPlayers.add(new Player("e"));
        testWordPairs = new ArrayList<>();
        testWordPairs.add(new WordPair("String", "Char"));
        testWordPairs.add(new WordPair("Trying stuff out", "Rejecting Everything"));
        testPlayersAndWords = new PlayersAndWordPairs(testPlayers, testWordPairs);

        assertEquals(testPlayersAndWords.getPlayers(), testPlayers);
        assertEquals(testPlayersAndWords.getWordPairs(), testWordPairs);
    }
}
