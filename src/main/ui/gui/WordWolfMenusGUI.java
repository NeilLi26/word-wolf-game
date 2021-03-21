package ui.gui;

import model.Player;
import model.PlayersAndWordPairs;
import model.WordPair;
import persistence.JsonReader;
import ui.gui.ingamepanels.StartGamePanel;
import ui.gui.menupanels.MenuPanel;
import ui.gui.menupanels.PlayersMenu;
import ui.gui.menupanels.SaveAndLoadMenu;
import ui.gui.menupanels.WordsMenu;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
word wolf gui JFrame, with all the menu related panels
 */
public class WordWolfMenusGUI extends JFrame {
    //players and words
    PlayersAndWordPairs playersAndWordPairs;

    //Java Swing related fields
    MenuPanel playersMenu;
    MenuPanel wordsMenu;
    JPanel gameMenu;
    JPanel saveLoadMenu;
    JTabbedPane tabbedPane = new JTabbedPane();

    //JSON reader for loading default word list
    private JsonReader jsonReader;
    private static final String JSON_DEFAULT_WORDS = "./data/defaultWordPairs.json";

    //EFFECTS: constructs a new WordWolfMenusGUI
    public WordWolfMenusGUI() {
        super("Word Wolf Game");
        initPlayersAndWords();
        generatePanels();
        addPanels();
        generateFrame();
    }

    //MODIFIES: this
    //EFFECTS: initiates the players and words
    private void initPlayersAndWords() {
        List<Player> players = new ArrayList<>();
        List<WordPair> wordPairs = generateDefaultWordList();
        playersAndWordPairs = new PlayersAndWordPairs(players, wordPairs);
        displayWordPairs();
    }

    //MODIFIES: this
    //EFFECTS: loads the word pairs from the default word list file
    private List<WordPair> generateDefaultWordList() {
        jsonReader = new JsonReader(JSON_DEFAULT_WORDS);
        List<WordPair> wordList = new ArrayList<>();
        try {
            wordList = jsonReader.read().getWordPairs();
            System.out.println("Loaded word list from " + JSON_DEFAULT_WORDS);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_DEFAULT_WORDS);
        }
        return wordList;
    }

    //MODIFIES: this
    //EFFECTS: generates the panels to be added to the tabbedPane
    private void generatePanels() {
        playersMenu = new PlayersMenu(playersAndWordPairs);
        wordsMenu = new WordsMenu(playersAndWordPairs);
        gameMenu = new StartGamePanel(playersAndWordPairs);
        saveLoadMenu = new SaveAndLoadMenu(this);
    }

    //MODIFIES: this
    //EFFECTS: adds the panels to the tabbedPane
    private void addPanels() {
        tabbedPane.add("Modify Players", playersMenu);
        tabbedPane.add("Modify Word Pairs", wordsMenu);
        tabbedPane.add("Start Game", gameMenu);
        tabbedPane.add("Save and Load", saveLoadMenu);
        add(tabbedPane);
    }

    //MODIFIES: this
    //EFFECTS: generates the JFrame for this
    private void generateFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(600,400);
        setVisible(true);
    }

    //EFFECTS: prints out all the word pairs in the wordlist
    private void displayWordPairs() {
        System.out.println("Words:");
        for (WordPair wp: playersAndWordPairs.getWordPairs()) {
            System.out.println(wp.getFirstWord() + " and " + wp.getSecondWord());
        }
    }

    //getters
    public PlayersAndWordPairs getPlayersAndWordPairs() {
        return playersAndWordPairs;
    }

    public JPanel getPlayersMenu() {
        return playersMenu;
    }

    public JPanel getWordsMenu() {
        return wordsMenu;
    }

    //MODIFIES: this
    //EFFECTS: updates the words menu and players menu after a load
    public void update() {
        wordsMenu.updateJList();
        playersMenu.updateJList();
    }
}
