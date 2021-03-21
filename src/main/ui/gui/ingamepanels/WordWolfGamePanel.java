package ui.gui.ingamepanels;

import model.WordWolfGame;

import javax.swing.*;
import java.awt.event.ActionListener;

/*
standard JPanel for a panel in word wolf game
 */
public abstract class WordWolfGamePanel extends JPanel implements ActionListener {
    StartGamePanel panelController;
    WordWolfGame wordWolfGame;

    //EFFECTS: constructs a new WordWolfGamePanel
    public WordWolfGamePanel(StartGamePanel panelController, WordWolfGame wordWolfGame) {
        this.panelController = panelController;
        this.wordWolfGame = wordWolfGame;
    }

    //MODIFIES: this
    //EFFECTS: sets the panel shown to the desired one
    public void showPanel(String name) {
        panelController.showGivenPanel(name);
    }

}
