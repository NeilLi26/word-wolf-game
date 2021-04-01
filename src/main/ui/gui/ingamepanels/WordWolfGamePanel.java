package ui.gui.ingamepanels;

import model.WordWolfGame;

import javax.swing.*;
import java.awt.event.ActionListener;

/*
standard JPanel for a panel in word wolf game
 */
public abstract class WordWolfGamePanel extends JPanel implements ActionListener {
    protected StartGamePanel panelController;

    //EFFECTS: constructs a new WordWolfGamePanel
    public WordWolfGamePanel(StartGamePanel panelController) {
        this.panelController = panelController;
    }
}
