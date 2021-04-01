package ui.gui.ingamepanels;

import model.Player;
import model.WordWolfGame;
import ui.gui.sound.MidiSoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/*
the JPanel for when the players see their words
 */
public class SeeWordPanel extends WordWolfGamePanel {
    private static final String HIDE_OR_SHOW_COMMAND = "hideOrShow";
    private static final String CONTINUE_COMMAND = "continue";
    private static final String DISCUSSION_PANEL_NAME = "discussionPanel";

    //Java swing fields
    private DefaultListModel<String> playersListModel;
    private JList<String> playersJList;
    private JLabel wordShower;

    boolean wordShowing;

    //sound player
    private MidiSoundPlayer midiSoundPlayer;

    //EFFECTS: constructs a new SeeWordPanel
    public SeeWordPanel(StartGamePanel panelController) {
        super(panelController);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        wordShowing = false;

        generateListModel(gbc);
        initWordShower(gbc);
        generateShowOrHideButton(gbc);
        generateContinueButton(gbc);
        midiSoundPlayer = new MidiSoundPlayer();
    }

    //MODIFIES: this
    //EFFECTS: creates a list model from the player list and adds it to this
    private void generateListModel(GridBagConstraints gbc) {
        playersListModel = new DefaultListModel<>();

        for (Player p: panelController.getWordWolfGame().getPlayers()) {
            playersListModel.addElement(p.getName());
        }

        playersJList = new JList<>(playersListModel);
        playersJList.setSelectedIndex(0);
        playersJList.setSize(WIDTH, HEIGHT * 2 / 3);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        add(playersJList, gbc);
    }

    private void initWordShower(GridBagConstraints gbc) {
        wordShower = new JLabel("Your word is: ");
        add(wordShower);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        add(wordShower, gbc);
    }

    //MODIFIES: this
    //EFFECTS: adds a show or hide button to this
    private void generateShowOrHideButton(GridBagConstraints gbc) {
        JButton hideOrShowButton = new JButton("Show/Hide word");
        hideOrShowButton.setActionCommand(HIDE_OR_SHOW_COMMAND);
        hideOrShowButton.addActionListener(this);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridheight = 1;
        add(hideOrShowButton, gbc);
    }

    //MODIFIES: this
    //EFFECTS: adds a show or hide button to this
    private void generateContinueButton(GridBagConstraints gbc) {
        JButton continueButton = new JButton("Continue");
        continueButton.setActionCommand(CONTINUE_COMMAND);
        continueButton.addActionListener(this);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridheight = 1;
        add(continueButton, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals(HIDE_OR_SHOW_COMMAND)) {
            if (!wordShowing) {
                wordShower.setText("Your word is: "
                        + panelController.getWordWolfGame().getPlayers()
                        .get(playersJList.getSelectedIndex()).getWord());
                wordShowing = true;
                updateUI();
                midiSoundPlayer.playSoundAscending();
            } else {
                wordShower.setText("Your word is: ");
                wordShowing = false;
                updateUI();
                midiSoundPlayer.playSoundDescending();
            }
        } else if (command.equals(CONTINUE_COMMAND)) {
            panelController.showGivenPanel(DISCUSSION_PANEL_NAME);
        }
    }
}
