package ui.gui.ingamepanels;

import model.WordWolfGame;

import javax.swing.*;
import java.awt.event.ActionEvent;

/*
JPanel for the discussion portion of the game
 */
public class DiscussionPanel extends WordWolfGamePanel {

    private static final String VOTE_KICK_PANEL_NAME = "voteKickPanel";
    private static final String CONTINUE_COMMAND = "continue";

    JLabel discussionMessage;
    JButton continueButton;

    //EFFECTS: constructs a new discussion panel
    public DiscussionPanel(StartGamePanel controller, WordWolfGame wordWolfGame) {
        super(controller, wordWolfGame);
        generateAndAddEndMessage();
        generateAndAddEndButton();
    }

    //MODIFIES: this
    //EFFECTS: generates the end message and adds it to the panel
    private void generateAndAddEndMessage() {
        discussionMessage = new JLabel("Discuss among yourselves to find out who might be the word wolves");
        add(discussionMessage);
    }

    //MODIFIES: this
    //EFFECTS: generates the end button and adds it to the panel
    private void generateAndAddEndButton() {
        continueButton = new JButton("Continue");
        continueButton.setActionCommand(CONTINUE_COMMAND);
        continueButton.addActionListener(this);
        add(continueButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals(CONTINUE_COMMAND)) {
            panelController.showGivenPanel(VOTE_KICK_PANEL_NAME);
        }
    }
}
