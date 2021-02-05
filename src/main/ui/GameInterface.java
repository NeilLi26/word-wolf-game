package ui;

import model.Player;
import model.WordPair;
import model.WordWolfGame;

import java.util.List;

public class GameInterface {
    private static final int PRINT_NAME_SPACING = 25;

    private List<Player> players;
    private List<WordPair> wordList;
    private GameState gameState;
    private WordWolfGame currentGame;

    private enum GameState {
        SHOWING_WORDS, DESCRIBING_WORD, PLAYERS_VOTING, END
    }

    public GameInterface(List<Player> players, List<WordPair> wordList) {
        this.players = players;
        this.wordList = wordList;
        runCurrentGame();
    }

    //EFFECTS: runs a single round of the word wolf game
    public void runCurrentGame() {
        Boolean gameRunning = true;
        String command = null;

        while (gameRunning) {
            if (gameState == GameState.SHOWING_WORDS) {
                displayPLayerWordMenu();
            } else if (gameState == GameState.DESCRIBING_WORD) {
                displayDescribingPlayers();
            } else if (gameState == GameState.PLAYERS_VOTING) {
                displayVotingMenu();
            } else if (gameState == GameState.END) {
                displayEndOfRound();
            }
        }
    }

    //TODO: 1
    //MODIFIES: this
    //EFFECTS: display the menu for when the players get to see their words
    public void displayPLayerWordMenu() {
        System.out.println("Who's word would you like to see?");
        displayPlayers();
    }

    //EFFECTS: prints out the players, with two on each row
    public void displayPlayers() {
        int counter = 0;

        for (Player p: players) {
            spaceNames(counter, p);
            counter++;
        }

        if (counter % 2 == 1) {
            System.out.println();
        }
    }

    //EFFECTS: prints the names with proper spacing and alignment
    public void spaceNames(int counter, Player p) {
        if (counter % 2 == 0) {
            System.out.print(p.getName());

            for (int x = 0; x < PRINT_NAME_SPACING - p.getName().length(); x++) {
                System.out.print(" ");
            }

        } else {
            System.out.println(p.getName());
        }
    }

    //TODO: 2
    //MODIFIES: this
    //EFFECTS: display the players who will be describing their word
    public void displayDescribingPlayers() {

    }

    //TODO: 3
    //MODIFIES: this
    //EFFECTS: display the voting menu for the players
    public boolean displayVotingMenu() {
        return true;
    }

    //TODO: 4
    //EFFECTS: displays the victor and ends the game
    public void displayEndOfRound() {

    }
}
