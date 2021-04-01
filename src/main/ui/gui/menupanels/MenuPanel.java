package ui.gui.menupanels;

import model.PlayersAndWordPairs;

import javax.swing.*;

/*
a panel for one of the menus of word wolf
 */
public abstract class MenuPanel extends JPanel {
    protected PlayersAndWordPairs playersAndWordPairs;

    //constructor
    public MenuPanel(PlayersAndWordPairs playersAndWordPairs) {
        this.playersAndWordPairs = playersAndWordPairs;
    }

    //MODIFIES: this
    //EFFECTS: updates once a file has been read
    public abstract void updateJList();
}
