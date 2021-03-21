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

    @Test
    void settersTest() {
        testPlayers = new ArrayList<>();
        testWordPairs = new ArrayList<>();
        testPlayersAndWords = new PlayersAndWordPairs(testPlayers, testWordPairs);


        List<Player> newTestPlayers = new ArrayList<>();
        newTestPlayers.add(new Player("a"));
        newTestPlayers.add(new Player("b"));
        newTestPlayers.add(new Player("c"));
        newTestPlayers.add(new Player("d"));
        newTestPlayers.add(new Player("e"));

        List<WordPair> newTestWords = new ArrayList<>();
        newTestWords.add(new WordPair("String", "Char"));
        newTestWords.add(new WordPair("Trying stuff out", "Rejecting Everything"));

        testPlayersAndWords.setPlayers(newTestPlayers);
        testPlayersAndWords.setWordPairs(newTestWords);

        assertEquals(testPlayersAndWords.getPlayers(), newTestPlayers);
        assertEquals(testPlayersAndWords.getWordPairs(), newTestWords);
    }

    @Test
    void addersTest() {
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

        int initialPlayersSize = testPlayersAndWords.getPlayers().size();
        int initialWordsSize = testPlayersAndWords.getWordPairs().size();

        testPlayersAndWords.addPlayer(new Player("f"));
        testPlayersAndWords.addPlayer(new Player("g"));

        testPlayersAndWords.addWordPair(new WordPair("One", "Two"));
        testPlayersAndWords.addWordPair(new WordPair("Three", "Four"));

        assertEquals(testPlayersAndWords.getPlayers().size(), initialPlayersSize + 2);
        assertEquals(testPlayersAndWords.getWordPairs().size(), initialWordsSize + 2);
        assertEquals(testPlayersAndWords.getPlayers().get(initialPlayersSize).getName(), "f");
        assertEquals(testPlayersAndWords.getPlayers().get(initialPlayersSize + 1).getName(), "g");
        assertEquals(testPlayersAndWords.getWordPairs().get(initialWordsSize).getFirstWord(), "One");
        assertEquals(testPlayersAndWords.getWordPairs().get(initialWordsSize + 1).getFirstWord(), "Three");
    }

    @Test
    void removersTest() {
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

        int initialPlayersSize = testPlayersAndWords.getPlayers().size();
        int initialWordsSize = testPlayersAndWords.getWordPairs().size();

        testPlayersAndWords.removePlayer(initialPlayersSize - 1);
        testPlayersAndWords.removePlayer(initialPlayersSize - 2);

        testPlayersAndWords.removeWordPair(initialWordsSize - 1);

        assertEquals(testPlayersAndWords.getPlayers().size(), initialPlayersSize - 2);
        assertEquals(testPlayersAndWords.getWordPairs().size(), initialWordsSize - 1);
    }
}
