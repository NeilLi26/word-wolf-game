package ui;

import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WordWolfInterface {
    private static final int PRINT_NAME_SPACING = 25;

    private Scanner input;
    private MenuState menuState;
    private List<Player> players;
    private List<WordPair> wordList;

    private enum MenuState {
        MENU, PLAYER_EDIT, WORD_EDIT, IN_GAME
    }

    public WordWolfInterface() {
        initValues();
        runWordWolf();
    }

    //EFFECTS: runs the menu portion of the word wolf game
    private void runWordWolf() {
        boolean programRunning = true;

        while (programRunning) {
            if (menuState == MenuState.MENU) {
                displayMenu();
                programRunning = processCommandMainMenu();
            } else if (menuState == MenuState.PLAYER_EDIT) {
                displayPlayerOptions();
                processCommandPlayer();
            } else if (menuState == MenuState.WORD_EDIT) {
                displayWordListOptions();
                processCommandWords();
            } else if (menuState == MenuState.IN_GAME) {
                new GameInterface(players, wordList);
                menuState = MenuState.MENU;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command when game state is MENU, and
    // return false if the player enters a four, and true otherwise
    private boolean processCommandMainMenu() {
        String command = input.next();

        if (command.equals("1")) {
            menuState = MenuState.PLAYER_EDIT;
        } else if (command.equals("2")) {
            menuState = MenuState.WORD_EDIT;
        } else if (command.equals("3")) {
            if (players.size() >= 3) {
                menuState = MenuState.IN_GAME;
            } else {
                System.out.println("not enough players (need at least 3)");
            }
        } else if (command.equals("4")) {
            System.out.println("quitting game");
            return false;
        } else {
            System.out.println("Invalid input");
        }

        return true;
    }

    // MODIFIES: this
    // EFFECTS: processes user command when game state is PLAYER
    private void processCommandPlayer() {
        String command = input.next();

        if (command.equals("1")) {
            addingPlayer();
        } else if (command.equals("2")) {
            removePlayer();
        } else if (command.equals("3")) {
            menuState = MenuState.MENU;
        } else {
            System.out.println("Invalid input");
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command when game state is WORD
    private void processCommandWords() {
        String command = input.next();

        if (command.equals("1")) {
            displayWordPairs();
        } else if (command.equals("2")) {
            addWordPairs();
        } else if (command.equals("3")) {
            menuState = MenuState.MENU;
        } else {
            System.out.println("Invalid input");
        }
    }

    //EFFECTS: prints out the menu options
    private void displayMenu() {
        System.out.println("Welcome to Neil's Word Wolf game!");
        System.out.println("1: Add/Remove players");
        System.out.println("2: Add words");
        System.out.println("3: Start game");
        System.out.println("4: Exit");
    }

    //EFFECTS: prints out the players in the game and the options
    private void displayPlayerOptions() {
        System.out.println("Current players:");

        displayPlayers();

        System.out.println("1. Add player");
        System.out.println("2. Remove Player");
        System.out.println("3. Go back");
    }

    //EFFECTS: prints out the options for the wordlist
    private void displayWordListOptions() {
        System.out.println("1. Display Word Pairs");
        System.out.println("2. Add word pairs");
        System.out.println("3. Go back");
    }

    //MODIFIES: this
    //EFFECTS: adds a player into players and return true. If the player wants to be named "continue", there are 10
    // players already, or there is a player with the same name in the game, return false and do not add the new player
    public boolean addingPlayer() {
        System.out.println("What would you like to name this player?");
        String name = input.next();

        if (name.equals("continue") || name.equals("wolf") || name.equals("white") || name.equals("skip")) {
            System.out.println("Nice try, but no game breaking for you today");
            return false;
        } else if (players.size() >= 10) {
            System.out.println("Player amount already at maximum");
            return false;
        } else if (name.equals("savemetimeohgreatneil")) {
            System.out.println("oh i shall save you time - neil");
            initiateDefaultPlayers();
            return true;
        } else {
            for (Player p : players) {
                if (p.getName().equals(name)) {
                    System.out.println("Player already exists");
                    return false;
                }
            }
        }

        players.add(new Player(name));
        System.out.println(name + " added");
        return true;
    }

    //MODIFIES: this
    //EFFECTS: removes a player from players and return true, if not found, return false
    public boolean removePlayer() {
        System.out.println("Which player would you like to remove?");
        String name = input.next();

        for (Player p: players) {
            if (p.getName().equals(name)) {
                players.remove(p);
                System.out.println(name + " removed");
                return true;
            }
        }

        System.out.println(name + " was not found in the list");
        return false;
    }

    //EFFECTS: prints out all the word pairs in the wordlist
    public void displayWordPairs() {
        System.out.println("Words:");
        for (WordPair wp: wordList) {
            System.out.println(wp.getFirstWord() + " and " + wp.getSecondWord());
        }
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

    public void addWordPairs() {
        System.out.println("What is the first word that you would like to add?");
        String firstWord = input.next();
        System.out.println("What is the second word that you would like to add?");
        String secondWord = input.next();

        wordList.add(new WordPair(firstWord, secondWord));
        System.out.println("Word pair added");
    }

    public void makeDefaultWordlist() {
        wordList = new ArrayList<>();
        wordList.add(new WordPair("Ocean", "Pool"));
        wordList.add(new WordPair("Tuxedo", "Military Uniform"));
        wordList.add(new WordPair("Java", "Python"));
        wordList.add(new WordPair("Tea", "Coffee"));
        wordList.add(new WordPair("Chocolate", "Vanilla"));
        wordList.add(new WordPair("Mountain", "Skyscraper"));
    }

    //MODIFIES: this
    //EFFECTS: initiates the fields
    private void initValues() {
        input = new Scanner(System.in);
        menuState = MenuState.MENU;
        players = new ArrayList<>();
        makeDefaultWordlist(); //initiates word list
    }

    //MODIFIES: this
    //EFFECTS: cheat for testing, initiates default list of players
    private void initiateDefaultPlayers() {
        players.add(new Player("Amy"));
        players.add(new Player("Bob"));
        players.add(new Player("Cedric"));
        players.add(new Player("Deborah"));
        players.add(new Player("Evan"));
        players.add(new Player("Fatima"));
        players.add(new Player("Gordon"));
        players.add(new Player("Haiti"));
        players.add(new Player("Indigo"));
        players.add(new Player("Jackey"));
    }
}
