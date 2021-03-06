package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private Player testPlayer;

    @BeforeEach
    void initTestPlayer() {
        testPlayer = new Player("testee");
    }

    @Test
    void setRoleTest() {
        assertEquals(testPlayer.getRole(), Role.NOTHING_YET);
        testPlayer.setRole(Role.MAJORITY);
        assertEquals(testPlayer.getRole(), Role.MAJORITY);
        testPlayer.setRole(Role.MINORITY);
        assertEquals(testPlayer.getRole(), Role.MINORITY);
        testPlayer.setRole(Role.MRWHITE);
        assertEquals(testPlayer.getRole(), Role.MRWHITE);
    }


    @Test
    void setWordTest() {
        testPlayer.setWord("TESTING");
        assertEquals(testPlayer.getWord(), "TESTING");
        testPlayer.setWord("another word");
        assertEquals(testPlayer.getWord(), "another word");
    }
}