package ui.gui.menupanels;

import model.PlayersAndWordPairs;
import model.WordPair;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.console.WordWolfInterface;
import ui.gui.WordWolfMenusGUI;

import java.io.FileNotFoundException;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/*
panel for saving and loading to/from a panel
 */
public class SaveAndLoadMenu extends JPanel implements ActionListener {
    private WordWolfMenusGUI mainMenu;
    private PlayersAndWordPairs playersAndWordPairs;

    //button commands
    private static final String SAVE_GAME = "saveGame";
    private static final String LOAD_SAVED_GAME = "loadGame";
    private static final String LOAD_DEFAULT_WORDS = "loadDefault";
    private static final String LOAD_LOCATION_WORDS = "loadLocations";
    private static final String LOAD_POP_CULTURE_WORDS = "loadPopCulture";
    private static final String LOAD_UNIVERSITY_WORDS = "loadUniversity";

    //save files
    private static final String JSON_STORE = "./data/PlayersAndWordPairs.json";
    private static final String JSON_DEFAULT_WORDS = "./data/defaultWordPairs.json";
    private static final String JSON_LOCATION_WORDS = "./data/locationRelatedWords.json";
    private static final String JSON_POP_CULTURE_WORDS = "./data/popCultureRelatedWords.json";
    private static final String JSON_UNIVERSITY_WORDS = "./data/universityRelatedWords.json";

    //readers and writers
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    //buttons
    JButton saveButton;
    JButton loadButton;
    JButton loadDefaultWords;
    JButton loadLocationWords;
    JButton loadPopCultureWords;
    JButton loadUniversityWords;

    //reading string
    private String readLocation;

    //EFFECTS: constructs a SaveAndLoadMenu
    public SaveAndLoadMenu(WordWolfMenusGUI menu) {
        mainMenu = menu;
        playersAndWordPairs = menu.getPlayersAndWordPairs();

        setLayout(new GridLayout(6, 1, 3, 3));

        generateButtons();
        addButtons();
    }

    //MODIFIES: this
    //EFFECTS: generates all the buttons
    private void generateButtons() {
        saveButton = new JButton("Save");
        saveButton.setActionCommand(SAVE_GAME);
        saveButton.addActionListener(this);
        loadButton = new JButton("Load Saved");
        loadButton.setActionCommand(LOAD_SAVED_GAME);
        loadButton.addActionListener(this);
        loadDefaultWords = new JButton("Load Default Words");
        loadDefaultWords.setActionCommand(LOAD_DEFAULT_WORDS);
        loadDefaultWords.addActionListener(this);
        loadLocationWords = new JButton("Load Location Words");
        loadLocationWords.setActionCommand(LOAD_LOCATION_WORDS);
        loadLocationWords.addActionListener(this);
        loadPopCultureWords = new JButton("Load Pop-Culture Words");
        loadPopCultureWords.setActionCommand(LOAD_POP_CULTURE_WORDS);
        loadPopCultureWords.addActionListener(this);
        loadUniversityWords = new JButton("Load University Words");
        loadUniversityWords.setActionCommand(LOAD_UNIVERSITY_WORDS);
        loadUniversityWords.addActionListener(this);
    }

    //MODIFIES: this
    //EFFECTS: adds the buttons to the panel
    private void addButtons() {
        add(saveButton);
        add(loadButton);
        add(loadDefaultWords);
        add(loadLocationWords);
        add(loadPopCultureWords);
        add(loadUniversityWords);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals(SAVE_GAME)) {
            savePlayersAndWordPairs();
        } else if (command.equals(LOAD_SAVED_GAME)) {
            loadPlayersAndWordPairs();
        } else {
            switch (command) {
                case LOAD_DEFAULT_WORDS:
                    readLocation = JSON_DEFAULT_WORDS;
                    break;
                case LOAD_LOCATION_WORDS:
                    readLocation = JSON_LOCATION_WORDS;
                    break;
                case LOAD_POP_CULTURE_WORDS:
                    readLocation = JSON_POP_CULTURE_WORDS;
                    break;
                default:
                    readLocation = JSON_UNIVERSITY_WORDS;
                    break;
            }
            readFromPreMadeWords(readLocation);
        }
        mainMenu.update();
    }

    // EFFECTS: saves the players and word pairs to file
    private void savePlayersAndWordPairs() {
        try {
            jsonWriter = new JsonWriter(JSON_STORE);
            jsonWriter.open();
            System.out.println("nothing");
            jsonWriter.write(playersAndWordPairs);
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
            jsonReader = new JsonReader(JSON_STORE);
            PlayersAndWordPairs readTemp = jsonReader.read();
            playersAndWordPairs.setPlayers(readTemp.getPlayers());
            playersAndWordPairs.setWordPairs(readTemp.getWordPairs());
            System.out.println("Loaded players and word list from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }

    }

    //MODIFIES: this
    //EFFECTS: loads the wordlist from the given wordlist
    private void readFromPreMadeWords(String jsonLocation) {
        jsonReader = new JsonReader(jsonLocation);
        try {
            PlayersAndWordPairs saveState = jsonReader.read();
            List<WordPair> wordList = saveState.getWordPairs();
            playersAndWordPairs.setWordPairs(wordList);
            System.out.println("Loaded word list from " + jsonLocation);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + jsonLocation);
        }
    }
}
