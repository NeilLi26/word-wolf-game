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
        testPlayer.setRole(Role.MAJORITY);
        assertEquals(testPlayer.getRole(), Role.MAJORITY);
        testPlayer.setRole(Role.MINORITY);
        assertEquals(testPlayer.getRole(), Role.MINORITY);
        testPlayer.setRole(Role.MRWHITE);
        assertEquals(testPlayer.getRole(), Role.MRWHITE);
    }

    @Test
    void setWordTest() {
        assertFalse(testPlayer.isWordVisible());
        testPlayer.seeWord();
        assertTrue(testPlayer.isWordVisible());
        testPlayer.hideWord();
        assertFalse(testPlayer.isWordVisible());
    }
}