package ui.gui.ingamepanels;

import model.Role;
import model.WordWolfGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/*
panel for the end of a game of word wolf
 */
public class EndPanel extends WordWolfGamePanel {
    private static final String END_COMMAND = "endGame";

    private JLabel endMessage;
    private JButton endButton;

    //EFFECTS: constructs an EndPanel
    public EndPanel(StartGamePanel panelController) {
        super(panelController);

        setLayout(new GridLayout(2, 1, 6, 6));
        generateAndAddEndMessage();
        generateAndAddEndButton();
    }

    //MODIFIES: this
    //EFFECTS: generates the end message and adds it to the panel
    private void generateAndAddEndMessage() {
        endMessage = new JLabel("Something went wrong");

        add(endMessage);
    }

    //MODIFIES: this
    //EFFECTS: generates the end button and adds it to the panel
    private void generateAndAddEndButton() {
        endButton = new JButton("End Game");
        endButton.setActionCommand(END_COMMAND);
        endButton.addActionListener(this);
        add(endButton);
    }

    //MODIFIES: this
    //EFFECTS: modifies the endMessage given the victor
    public void setEndMessage() {
        Role victor = panelController.getWordWolfGame().getVictor();

        if (victor.equals(Role.MAJORITY)) {
            endMessage.setText("The Majority has won!!!");
        } else if  (victor.equals(Role.MINORITY)) {
            endMessage.setText("The Wolfs have won!!!");
        } else if  (victor.equals(Role.MRWHITE)) {
            endMessage.setText("Mr White has won!!!");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals(END_COMMAND)) {
            panelController.resetPanels();
        }
    }
}
