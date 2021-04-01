package ui.gui.ingamepanels;

import model.Player;
import model.Role;
import model.WordWolfGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/*
JPanel for the voting portion of a game
 */
public class VotingPanel extends WordWolfGamePanel {
    private final EndPanel endPanel;

    private static final String END_PANEL_NAME = "endPanel";
    private static final String DISCUSSION_PANEL_NAME = "discussionPanel";

    //commands
    private static final String WOLF_WIN_COMMAND = "wolfWin";
    private static final String WHITE_WIN_COMMAND = "whiteWin";
    private static final String VOTE_COMMAND = "voteOut";
    private static final String SKIP_COMMAND = "skipVote";

    private DefaultListModel<String> playersListModel;
    private JList<String> playersJList;

    private JLabel remainingWolves;
    private JLabel remainingWhites;

    private WordWolfGame wordWolfGame;

    //EFFECTS: constructs a new voting panel
    public VotingPanel(StartGamePanel panelController, WordWolfGamePanel endPanel) {
        super(panelController);

        wordWolfGame = panelController.getWordWolfGame();

        this.endPanel = (EndPanel) endPanel;
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        generateListModel(gbc);
        generateRemainingWolvesAndWhites(gbc);
        generateLetWolfOrWhiteWin(gbc);
        generateVoteButtons(gbc);
    }

    //MODIFIES: this
    //EFFECTS: creates a list model from the player list and adds it to this
    private void generateListModel(GridBagConstraints gbc) {
        playersListModel = new DefaultListModel<>();

        for (Player p: wordWolfGame.getPlayers()) {
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

    //MODIFIES: this
    //EFFECTS: generates the panel containing information on remaining wolfs and whites
    private void generateRemainingWolvesAndWhites(GridBagConstraints gbc) {
        JPanel panelWithRemaining = new JPanel();
        remainingWolves = new JLabel("Remaining wolves: " + wordWolfGame.getRemainingWolfs());
        remainingWhites = new JLabel("Remaining whites: " + wordWolfGame.getRemainingWhites());
        panelWithRemaining.add(remainingWolves);
        panelWithRemaining.add(remainingWhites);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        add(panelWithRemaining, gbc);
    }

    //MODIFIES: this
    //EFFECTS: generates panel for letting the wolf or white win if they guessed the word
    private void generateLetWolfOrWhiteWin(GridBagConstraints gbc) {
        JPanel panelWithLetMinorWin = new JPanel();
        JButton letWolfWinButton = new JButton("Wolf Win");
        letWolfWinButton.setActionCommand(WOLF_WIN_COMMAND);
        letWolfWinButton.addActionListener(this);
        JButton letWhiteWinButton = new JButton("White Win");
        letWhiteWinButton.setActionCommand(WHITE_WIN_COMMAND);
        letWhiteWinButton.addActionListener(this);

        panelWithLetMinorWin.add(letWolfWinButton);
        panelWithLetMinorWin.add(letWhiteWinButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridheight = 1;
        add(panelWithLetMinorWin, gbc);
    }

    //MODIFIES: this
    //EFFECTS: generates the buttons for voting or skipping vote and adds them to this
    private void generateVoteButtons(GridBagConstraints gbc) {
        JButton voteButton = new JButton("Vote Kick");
        voteButton.setActionCommand(VOTE_COMMAND);
        voteButton.addActionListener(this);
        JButton skipButton = new JButton("Skip Vote");
        skipButton.setActionCommand(SKIP_COMMAND);
        skipButton.addActionListener(this);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridheight = 1;
        add(voteButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridheight = 1;
        add(skipButton, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case WOLF_WIN_COMMAND:
                wordWolfGame.setVictor(Role.MINORITY);
                endPanel.setEndMessage();
                panelController.showGivenPanel(END_PANEL_NAME);
                break;
            case WHITE_WIN_COMMAND:
                wordWolfGame.setVictor(Role.MRWHITE);
                endPanel.setEndMessage();
                panelController.showGivenPanel(END_PANEL_NAME);
                break;
            case SKIP_COMMAND:
                panelController.showGivenPanel(DISCUSSION_PANEL_NAME);
                break;
            default:
                int removedPos = playersJList.getSelectedIndex();
                String removedPlayerName = wordWolfGame.getPlayers().get(removedPos).getName();
                playersListModel.remove(removedPos);
                handleVote(removedPlayerName);
                break;
        }
    }

    //MODIFIES: this
    //EFFECTS: handles a vote, and sets a victor if one emerges
    private void handleVote(String removedPlayerName) {
        wordWolfGame.kickPlayer(removedPlayerName);

        String nextPanel = DISCUSSION_PANEL_NAME;

        double remainingMinorities = wordWolfGame.getRemainingWhites() + wordWolfGame.getRemainingWolfs();
        double remainingTotalPlayers = wordWolfGame.getPlayers().size();

        if (remainingMinorities >= remainingTotalPlayers / 2) {
            wordWolfGame.setVictor(Role.MINORITY);
            nextPanel = END_PANEL_NAME;
            endPanel.setEndMessage();
        } else if (remainingMinorities == 0) {
            wordWolfGame.setVictor(Role.MAJORITY);
            nextPanel = END_PANEL_NAME;
            endPanel.setEndMessage();
        }

        remainingWolves.setText("Remaining wolves: " + wordWolfGame.getRemainingWolfs());
        remainingWhites.setText("Remaining whites: " + wordWolfGame.getRemainingWhites());

        panelController.showGivenPanel(nextPanel);
    }
}
