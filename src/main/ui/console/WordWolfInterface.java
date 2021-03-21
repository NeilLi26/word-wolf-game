package ui.console;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WordWolfInterface {
    private static final String JSON_STORE = "./data/PlayersAndWordPairs.json";
    private static final String JSON_DEFAULT_WORDS = "./data/defaultWordPairs.json";
    private static final String JSON_LOCATION_WORDS = "./data/locationRelatedWords.json";
    private static final String JSON_POP_CULTURE_WORDS = "./data/popCultureRelatedWords.json";
    private static final String JSON_UNIVERSITY_WORDS = "./data/universityRelatedWords.json";
    private static final int MINIMUM_WORD_PAIR_REQUIRED = 3;
    private static final int PRINT_NAME_SPACING = 10;
    private static final int MAX_PLAYERS_SIZE = 10;
    private static final String EDIT_PLAYER_LIST_COMMAND = "1";
    private static final String EDIT_WORD_LIST_COMMAND = "2";
    private static final String START_GAME_COMMAND = "3";
    private static final String SAVE_GAME_STATE_COMMAND = "4";
    private static final String LOAD_GAME_STATE_COMMAND = "5";
    private static final String CHOOSE_WORDLIST_COMMAND = "6";
    private static final String QUIT_COMMAND = "7";
    private static final String ADD_PLAYER_COMMAND = "1";
    private static final String REMOVE_PLAYER_COMMAND = "2";
    private static final String REMOVE_ALL_PLAYERS_COMMAND = "3";
    private static final String RETURN_TO_MENU_COMMAND = "4";
    private static final String DISPLAY_WORDS_COMMAND = "1";
    private static final String ADD_WORDS_COMMAND = "2";
    private static final String REMOVE_ALL_WORDS_COMMAND = "3";
    private static final String DEFAULT_WORDLIST_COMMAND = "1";
    private static final String LOCATION_WORDLIST_COMMAND = "2";
    private static final String POP_CULTURE_WORDLIST_COMMAND = "3";
    private static final String UNIVERSITY_WORDLIST_COMMAND = "4";
    private static final String CHOOSE_NONE_COMMAND = "5";

    private Scanner input;
    private MenuState menuState;
    private List<Player> players;
    private List<WordPair> wordList;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private PlayersAndWordPairs saveState;

    private enum MenuState {
        MENU, PLAYER_EDIT, WORD_EDIT, IN_GAME, CHOOSING_WORDLIST
    }

    public WordWolfInterface() {
        initValues();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runWordWolf();
    }

    //EFFECTS: runs the menu portion of the word wolf game
    private void runWordWolf() {
        boolean programRunning = true;

        while (programRunning) {
            switch (menuState) {
                case MENU:
                    displayMenu();
                    programRunning = processCommandMainMenu();
                    break;
                case PLAYER_EDIT:
                    displayPlayerOptions();
                    processCommandPlayer();
                    break;
                case WORD_EDIT:
                    wordEditingState();
                    break;
                case IN_GAME:
                    new GameInterface(players, wordList);
                    menuState = MenuState.MENU;
                    break;
                case CHOOSING_WORDLIST:
                    choosingState();
                    break;
            }
        }
    }

    //EFFECTS: displays the word editing options, and processes the player's command while in this state
    private void wordEditingState() {
        displayWordListOptions();
        processCommandWords();
    }

    //EFFECTS: displays the choosing options, and processes the player's command while in this state
    private void choosingState() {
        displayPreMadeWordPairsOptions();
        processCommandChoosing();
    }

    // MODIFIES: this
    // EFFECTS: processes user command when game state is MENU, and
    // return false if the player enters a four, and true otherwise
    private boolean processCommandMainMenu() {
        String command = input.next();

        switch (command) {
            case EDIT_PLAYER_LIST_COMMAND:
            case EDIT_WORD_LIST_COMMAND:
            case START_GAME_COMMAND:
            case CHOOSE_WORDLIST_COMMAND:
                menuState = handleChangeMenuState(command);
                break;
            case SAVE_GAME_STATE_COMMAND:
                savePlayersAndWordPairs();
                break;
            case LOAD_GAME_STATE_COMMAND:
                loadPlayersAndWordPairs();
                break;
            case QUIT_COMMAND:
                System.out.println("quitting game");
                return false;
            default:
                System.out.println("Invalid input");
                break;
        }

        return true;
    }

    //MODIFIES: this
    //EFFECTS: returns a MenuState according to the command given
    private MenuState handleChangeMenuState(String command) {
        switch (command) {
            case EDIT_PLAYER_LIST_COMMAND:
                return MenuState.PLAYER_EDIT;
            case EDIT_WORD_LIST_COMMAND:
                return MenuState.WORD_EDIT;
            case START_GAME_COMMAND:
                if (wordList.size() >= MINIMUM_WORD_PAIR_REQUIRED) {
                    return MenuState.IN_GAME;
                } else {
                    System.out.println("You need at least 3 word pairs in your word list");
                }
            case CHOOSE_WORDLIST_COMMAND:
                return MenuState.CHOOSING_WORDLIST;
            default:
                return MenuState.MENU;
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command when game state is PLAYER
    private void processCommandPlayer() {
        String command = input.next();

        switch (command) {
            case ADD_PLAYER_COMMAND:
                addingPlayer();
                break;
            case REMOVE_PLAYER_COMMAND:
                removePlayer();
                break;
            case REMOVE_ALL_PLAYERS_COMMAND:
                this.players = new ArrayList<>();
                break;
            case RETURN_TO_MENU_COMMAND:
                menuState = MenuState.MENU;
                break;
            default:
                System.out.println("Invalid input");
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command when game state is WORD
    private void processCommandWords() {
        String command = input.next();

        switch (command) {
            case DISPLAY_WORDS_COMMAND:
                displayWordPairs();
                break;
            case ADD_WORDS_COMMAND:
                addWordPairs();
                break;
            case RETURN_TO_MENU_COMMAND:
                menuState = MenuState.MENU;
                break;
            case REMOVE_ALL_WORDS_COMMAND:
                this.wordList = new ArrayList<>();
                break;
            default:
                System.out.println("Invalid input");
                break;
        }
    }

    //EFFECTS: prints out the menu options
    private void displayMenu() {
        System.out.println("Welcome to Neil's Word Wolf game!");
        System.out.println(ADD_PLAYER_COMMAND + ": Add/Remove players");
        System.out.println(ADD_WORDS_COMMAND + ": Add words");
        System.out.println(START_GAME_COMMAND + ": Start game");
        System.out.println(SAVE_GAME_STATE_COMMAND + ": Save Players and current Word List");
        System.out.println(LOAD_GAME_STATE_COMMAND + ": Load Players and Saved Word List");
        System.out.println(CHOOSE_WORDLIST_COMMAND + ": Choose a pre-made word list");
        System.out.println(QUIT_COMMAND + ": Exit");
    }

    //EFFECTS: prints out the players in the game and the options
    private void displayPlayerOptions() {
        System.out.println("Current players:");

        displayPlayers();

        System.out.println(ADD_PLAYER_COMMAND + ". Add player");
        System.out.println(REMOVE_PLAYER_COMMAND + ". Remove Player");
        System.out.println(REMOVE_ALL_PLAYERS_COMMAND + ". Remove all players");
        System.out.println(RETURN_TO_MENU_COMMAND + ". Go back");
    }

    //EFFECTS: prints out the options for the wordlist
    private void displayWordListOptions() {
        System.out.println(DISPLAY_WORDS_COMMAND + ". Display Word Pairs");
        System.out.println(ADD_WORDS_COMMAND + ". Add word pairs");
        System.out.println(REMOVE_ALL_WORDS_COMMAND + ". Remove all word pairs");
        System.out.println(RETURN_TO_MENU_COMMAND + ". Go back");
    }

    //EFFECTS: prints out the players in the game and the options
    private void displayPreMadeWordPairsOptions() {
        System.out.println("Pre-made word pair options:");
        System.out.println(DEFAULT_WORDLIST_COMMAND + ". Default Wordlist");
        System.out.println(LOCATION_WORDLIST_COMMAND + ". Location Wordlist");
        System.out.println(POP_CULTURE_WORDLIST_COMMAND + ". Pop Culture Wordlist");
        System.out.println(UNIVERSITY_WORDLIST_COMMAND + ". University Wordlist");
        System.out.println(CHOOSE_NONE_COMMAND + ". Go back");
    }

    private void processCommandChoosing() {
        String command = input.next();
        String readLocation = "";
        switch (command) {
            case DEFAULT_WORDLIST_COMMAND:
                readLocation = JSON_DEFAULT_WORDS;
                break;
            case LOCATION_WORDLIST_COMMAND:
                readLocation = JSON_LOCATION_WORDS;
                break;
            case POP_CULTURE_WORDLIST_COMMAND:
                readLocation = JSON_POP_CULTURE_WORDS;
                break;
            case UNIVERSITY_WORDLIST_COMMAND:
                readLocation = JSON_UNIVERSITY_WORDS;
                break;
            case CHOOSE_NONE_COMMAND:
                menuState = MenuState.MENU;
                break;
            default:
                System.out.println("Invalid input");
                break;
        }
        readFromPreMadeWords(readLocation);
    }

    //MODIFIES: this
    //EFFECTS: loads the wordlist from the given wordlist
    private void readFromPreMadeWords(String jsonLocation) {
        if (menuState.equals(MenuState.CHOOSING_WORDLIST)) {
            jsonReader = new JsonReader(jsonLocation);
            try {
                saveState = jsonReader.read();
                wordList = saveState.getWordPairs();
                System.out.println("Loaded word list from " + JSON_STORE);
            } catch (IOException e) {
                System.out.println("Unable to read from file: " + JSON_STORE);
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: adds a player into players and return true. If the player wants to be named "continue", there are 10
    // players already, or there is a player with the same name in the game, return false and do not add the new player
    private boolean addingPlayer() {
        System.out.println("What would you like to name this player?");
        String name = input.next();

        if (name.equals("continue") || name.equals("wolf") || name.equals("white") || name.equals("skip")) {
            System.out.println("Nice try, but no game breaking for you today");
            return false;
        } else if (players.size() >= MAX_PLAYERS_SIZE) {
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
    private boolean removePlayer() {
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
    private void displayWordPairs() {
        System.out.println("Words:");
        for (WordPair wp: wordList) {
            System.out.println(wp.getFirstWord() + " and " + wp.getSecondWord());
        }
    }

    // EFFECTS: saves the players and word pairs to file
    private void savePlayersAndWordPairs() {
        try {
            saveState = new PlayersAndWordPairs(players, wordList);
            jsonWriter.open();
            jsonWriter.write(saveState);
            jsonWriter.close();
            System.out.println("Saved players and word list to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads players and word pairs from file
    private void loadPlayersAndWordPairs() {
        try {
            saveState = jsonReader.read();
            wordList = saveState.getWordPairs();
            players = saveState.getPlayers();
            System.out.println("Loaded players and word list from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }

    }

    //EFFECTS: prints out the players, with two on each row
    private void displayPlayers() {
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
    private void spaceNames(int counter, Player p) {
        if (counter % 2 == 0) {
            System.out.print(p.getName());

            for (int x = 0; x < PRINT_NAME_SPACING - p.getName().length(); x++) {
                System.out.print(" ");
            }

        } else {
            System.out.println(p.getName());
        }
    }

    //MODIFIES: this
    //EFFECTS: adds a pair of words to the wordlist
    private void addWordPairs() {
        System.out.println("What is the first word that you would like to add?");
        String firstWord = input.next();
        System.out.println("What is the second word that you would like to add?");
        String secondWord = input.next();

        wordList.add(new WordPair(firstWord, secondWord));
        System.out.println("Word pair added");
    }

    //MODIFIES: this
    //EFFECTS: adds the five default word pairs to the wordlist
    private void makeDefaultWordlist() {
        jsonReader = new JsonReader(JSON_DEFAULT_WORDS);
        wordList = new ArrayList<>();
        try {
            wordList = jsonReader.read().getWordPairs();
            System.out.println("Loaded word list from " + JSON_DEFAULT_WORDS);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_DEFAULT_WORDS);
        }
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
