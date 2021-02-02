package ui;

import model.*;

import java.util.List;
import java.util.Scanner;

public class WordWolfInterface {
    private Scanner input;
    private WordWolfGame currentGame;
    private GameState gameState;
    private List<Player> players;
    private List<WordPair> wordList;

    private enum GameState {
        MENU, ADD_PLAYER, ADD_WORD,
    }

    public WordWolfInterface() {
        runGame();
    }

    private void runGame() {
        Boolean gameRunning = true;
        String command = null;

        initValues();

        while (gameRunning) {
            command = input.next();

            if (gameState == GameState.MENU) {
                System.out.println("Welcome to Neil's Word Wolf game!");
                System.out.println("1: Add players");
                System.out.println("2: Remove players");
                System.out.println("3: Add words");
                System.out.println("4: Start game");
                System.out.println("5: Exit");
            }
        }
    }

    //TODO:1 finish the things that will be seen if they want to create a player
    public void addingPlayers() {
        System.out.println("What would you like to name this player?");
        System.out.println("What ");
    }

    //TODO:2 finish the things that will be seen if they want to add words
    public void addWordPairs() {
        System.out.println("What is the first word that you would like to add?");
        System.out.println("What is the second word that you would like to add?");
        System.out.println("Words added");
    }

    //TODO:3 make a default list of similar words
    public List<WordPair> makeDefaultWordlist() {
        return null;
    }

    //TODO 5:
    //MODIFIES: this
    //EFFECTS: initiates the fields
    private void initValues() {
        //stub
    }

    //TODO: 6
    //REQUIRES: players must have a size larger than 3
    //MODIFIES: this
    //EFFECTS: creates an instance of a game
    private void initGame() {
        //currentGame = new WordWolfGame(players, );
    }

    //TODO: 7 display the players
    //EFFECTS: shows all the players currently in the players list
    private void displayPlayers(Player players) {
        //stub
    }

    //TODO
    //MODIFIES: this
    //EFFECTS: check to see if the game has ended
    public void checkState(WordWolfGame game) {
        if (game.getVictor() == Role.MAJORITY) {
            System.out.println("The majority has won!");
        } else if (game.getVictor() == Role.MINORITY) {
            System.out.println("The wolves have won!");
        } else if (game.getVictor() == Role.MRWHITE) {
            System.out.println("The Mr.White has won!");
        }
    }
}
