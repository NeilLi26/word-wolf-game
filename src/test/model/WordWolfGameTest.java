package model;

import exceptions.NotEnoughPlayersException;
import exceptions.PlayerWithNoRoleInGameException;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;


public class WordWolfGameTest {

    private static String MINORITY_WORD = "MINOR";
    private static String MAJORITY_WORD = "MAJOR";

    private WordWolfGame testGame;
    private List<Player> testPlayers;

    @BeforeEach
    void initGameTest() {
        testPlayers = new ArrayList<>();
        testPlayers.add(new Player("Amy"));
        testPlayers.add(new Player("Bob"));
        testPlayers.add(new Player("Cedric"));
        testPlayers.add(new Player("Deborah"));
        testPlayers.add(new Player("Evan"));
        testPlayers.add(new Player("Fatima"));
        testPlayers.add(new Player("Gordon"));


        for (Player p: testPlayers) {
            if (p.getName().equals("Bob")) {
                p.setRole(Role.MINORITY);
            } else if (p.getName().equals("Deborah")) {
                p.setRole(Role.MRWHITE);
            } else {
                p.setRole(Role.MAJORITY);
            }
        }

        try {
            testGame = new WordWolfGame(testPlayers, MINORITY_WORD, MAJORITY_WORD);
        } catch ( PlayerWithNoRoleInGameException e) {
            fail("this constructor should be working");
        }
    }

    @Test
    void playerWithNoAssignedRoleConstructorTest() {
        testPlayers = new ArrayList<>();
        testPlayers.add(new Player("Amy"));
        testPlayers.add(new Player("Bob"));
        testPlayers.add(new Player("Cedric"));
        testPlayers.add(new Player("Deborah"));
        testPlayers.add(new Player("Evan"));
        testPlayers.add(new Player("Fatima"));
        testPlayers.add(new Player("Gordon"));


        for (Player p: testPlayers) {
            switch (p.getName()) {
                case "Bob":
                    p.setRole(Role.MINORITY);
                    break;
                case "Deborah":
                    p.setRole(Role.MRWHITE);
                    break;
                case "Evan":
                    p.setRole(Role.NOTHING_YET);
                    break;
                default:
                    p.setRole(Role.MAJORITY);
                    break;
            }
        }

        try {
            testGame = new WordWolfGame(testPlayers, MINORITY_WORD, MAJORITY_WORD);
            fail("this constructor should be failing");
        } catch ( PlayerWithNoRoleInGameException e) {
            System.out.println("Nice Catch");
        }
    }

    @Test
    void assignRoleTest() {
        assertEquals(testPlayers.get(0).getWord(), testGame.getMajorityWord());
        assertEquals(testPlayers.get(1).getWord(), testGame.getMinorityWord());
        assertEquals(testPlayers.get(2).getWord(), testGame.getMajorityWord());
        assertEquals(testPlayers.get(3).getWord(), "You are the Mr. White");
        assertEquals(testPlayers.get(4).getWord(), testGame.getMajorityWord());
        assertEquals(testPlayers.get(5).getWord(), testGame.getMajorityWord());
        assertEquals(testPlayers.get(6).getWord(), testGame.getMajorityWord());
    }

    @Test
    void victorTest() {
        testGame.setVictor(Role.MAJORITY);
        assertEquals(testGame.getVictor(), Role.MAJORITY);
        testGame.setVictor(Role.MINORITY);
        assertEquals(testGame.getVictor(), Role.MINORITY);
        testGame.setVictor(Role.MRWHITE);
        assertEquals(testGame.getVictor(), Role.MRWHITE);
    }

    @Test
    void voteKickTest() {
        assertEquals(testGame.getRemainingWolfs(), 1);
        assertEquals(testGame.getRemainingWhites(), 1);
        assertEquals(testGame.getPlayers().size(), 7);

        assertTrue(testGame.kickPlayer("Amy"));

        assertEquals(testGame.getRemainingWolfs(), 1);
        assertEquals(testGame.getRemainingWhites(), 1);
        assertEquals(testGame.getPlayers().size(), 6);

        assertFalse(testGame.kickPlayer("Neil"));

        assertEquals(testGame.getRemainingWolfs(), 1);
        assertEquals(testGame.getRemainingWhites(), 1);
        assertEquals(testGame.getPlayers().size(), 6);

        assertTrue(testGame.kickPlayer("Bob"));

        assertEquals(testGame.getRemainingWolfs(), 0);
        assertEquals(testGame.getRemainingWhites(), 1);
        assertEquals(testGame.getPlayers().size(), 5);

        assertTrue(testGame.kickPlayer("Deborah"));

        assertEquals(testGame.getRemainingWolfs(), 0);
        assertEquals(testGame.getRemainingWhites(), 0);
        assertEquals(testGame.getPlayers().size(), 4);
    }
}
