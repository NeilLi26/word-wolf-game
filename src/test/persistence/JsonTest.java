package persistence;

import model.Player;
import model.Role;
import model.WordPair;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkWordPair(String firstWord, String secondWord, WordPair wordPair) {
        assertEquals(firstWord, wordPair.getFirstWord());
        assertEquals(secondWord, wordPair.getSecondWord());
    }

    protected void checkPlayer(String name, Player player) {
        assertEquals(name, player.getName());
        assertEquals(Role.NOTHING_YET, player.getRole());
    }
}
