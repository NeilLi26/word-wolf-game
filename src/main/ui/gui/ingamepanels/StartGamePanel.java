package ui.gui.ingamepanels;

import exceptions.NotEnoughPlayersException;
import exceptions.PlayerWithNoRoleInGameException;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
panel for starting a game of word wolf, with a card layout for the other panels for when the game starts
 */
public class StartGamePanel extends JPanel implements ActionListener {
    //Commands:
    private static final String START_COMMAND = "startGame";

    //Card Layout
    private CardLayout cardLayout = new CardLayout();

    //Game panels with their names
    private JPanel panelContainer;
    private JPanel startPanel = new JPanel();
    private static final String START_PANEL_NAME = "startPanel";
    private WordWolfGamePanel seeWordPanel;
    private static final String SEE_WORD_PANEL_NAME = "seeWord";
    private WordWolfGamePanel discussionPanel;
    private static final String DISCUSSION_PANEL_NAME = "discussionPanel";
    private WordWolfGamePanel voteKickPanel;
    private static final String VOTE_KICK_PANEL_NAME = "voteKickPanel";
    private EndPanel endScreenPanel;
    private static final String END_PANEL_NAME = "endPanel";

    //related buttons, JLists, fields, etc
    private JButton startButton;

    private PlayersAndWordPairs playersAndWordPairs;
    private Random rand;
    private WordWolfGame wordWolfGame;

    //EFFECTS: constructs a new StartGamePanel
    public StartGamePanel(PlayersAndWordPairs playersAndWordPairs) {
        this.playersAndWordPairs = playersAndWordPairs;
        panelContainer = new JPanel();
        panelContainer.setLayout(cardLayout);
        initStartPanel();
        cardLayout.show(panelContainer, START_PANEL_NAME);
        add(panelContainer);
    }

    //EFFECTS: shows the panel with the given name
    public void showGivenPanel(String name) {
        cardLayout.show(panelContainer, name);
    }

    //MODIFIES: this
    //EFFECTS: removes all panels except for the first one, then shows it
    public void resetPanels() {
        panelContainer.remove(seeWordPanel);
        panelContainer.remove(discussionPanel);
        panelContainer.remove(voteKickPanel);
        panelContainer.remove(endScreenPanel);
        cardLayout.show(panelContainer, START_PANEL_NAME);
    }

    //MODIFIES: this
    //EFFECTS: initiates the starting panel
    private void initStartPanel() {
        startButton = new JButton("start");
        startButton.setActionCommand(START_COMMAND);
        startButton.addActionListener(this);
        startPanel.add(startButton);
        panelContainer.add(startPanel, START_PANEL_NAME);
    }

    //MODIFIES: this
    //EFFECTS: creates a new wordWolfGame and adds the game panels in
    private void startGame() {
        try {
            initGame(playersAndWordPairs.getPlayers(), playersAndWordPairs.getWordPairs());
        } catch (PlayerWithNoRoleInGameException e) {
            System.out.println("A player was not assigned a role");
            e.printStackTrace();
        } catch (NotEnoughPlayersException e) {
            System.out.println("You need at least 3 players to start the game");
        }
        initGamePanels();
    }

    //MODIFIES: this
    //EFFECTS: assigns the roles randomly to players, and then assign words of a given word pair accordingly
    private void initGame(java.util.List<Player> players, java.util.List<WordPair> wordList)
            throws NotEnoughPlayersException, PlayerWithNoRoleInGameException {
        this.rand = new Random();
        List<Player> assignedPlayers = assignRoles(copyPlayers(players));
        WordPair majorMinorPair = chooseWordPair(wordList);//the first one will be the major word, second one the minor
        wordWolfGame = new WordWolfGame(assignedPlayers, majorMinorPair.getFirstWord(), majorMinorPair.getSecondWord());
    }

    //EFFECTS: creates a copy of the player list
    private List<Player> copyPlayers(List<Player> players) {
        List<Player> playersCopy = new ArrayList<>();

        for (Player p: players) {
            playersCopy.add(new Player(p.getName()));
        }

        return playersCopy;
    }

    //MODIFIES: this
    //EFFECTS: throws a
    // otherwise choose the right amount of players for each of the minority roles
    // given the current number of players, and assign roles to them accordingly
    private List<Player> assignRoles(List<Player> players) throws NotEnoughPlayersException {
        if (players.size() < 3) {
            throw new NotEnoughPlayersException();
        }

        int totalWolves;
        int totalWhites;

        if (players.size() >= 9) {
            totalWolves = 2;
        } else {
            totalWolves = 1;
        }

        if (players.size() >= 6) {
            totalWhites = 1;
        } else {
            totalWhites = 0;
        }

        return assignRoleGivenRoleQuantity(totalWolves, totalWhites, players);
    }

    //MODIFIES: this
    //EFFECTS: assign roles to players
    private List<Player> assignRoleGivenRoleQuantity(int totalWolves, int totalWhites, List<Player> players) {
        assignGivenRoleToGivenAmountOfPeople(Role.MINORITY, totalWolves, players);
        assignGivenRoleToGivenAmountOfPeople(Role.MRWHITE, totalWhites, players);

        for (Player p: players) {
            if (p.getRole().equals(Role.NOTHING_YET)) {
                p.setRole(Role.MAJORITY);
            }
        }

        return players;
    }

    //MODIFIES: this
    //EFFECTS: randomly assign the given role to the given amount of people
    private void assignGivenRoleToGivenAmountOfPeople(Role role, int desiredAmount, List<Player> players) {
        int currentAmount = 0;
        int random;
        Player playerAtRand;

        while (currentAmount < desiredAmount) {
            random = rand.nextInt(players.size());
            playerAtRand = players.get(random);

            if (playerAtRand.getRole().equals(Role.NOTHING_YET)) {
                playerAtRand.setRole(role);
                currentAmount++;
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: chooses the Minority and Majority word pair randomly from a given wordlist
    private WordPair chooseWordPair(List<WordPair> wordList) {
        int index = rand.nextInt(wordList.size());

        WordPair usedWordPair = wordList.get(index);
        String firstWord = usedWordPair.getFirstWord();
        String secondWord = usedWordPair.getSecondWord();

        int random = rand.nextInt(2);

        if (random == 0) {
            return new WordPair(firstWord, secondWord);
        } else {
            return new WordPair(secondWord, firstWord);
        }
    }

    //MODIFIES: this
    //EFFECTS: adds the game panels past the start panel to the game
    private void initGamePanels() {
        seeWordPanel = new SeeWordPanel(this, wordWolfGame);
        discussionPanel = new DiscussionPanel(this, wordWolfGame);
        endScreenPanel = new EndPanel(this, wordWolfGame);
        voteKickPanel = new VotingPanel(this, wordWolfGame, endScreenPanel);
        panelContainer.add(seeWordPanel, SEE_WORD_PANEL_NAME);
        panelContainer.add(discussionPanel, DISCUSSION_PANEL_NAME);
        panelContainer.add(voteKickPanel, VOTE_KICK_PANEL_NAME);
        panelContainer.add(endScreenPanel, END_PANEL_NAME);
        cardLayout.show(panelContainer, SEE_WORD_PANEL_NAME);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals(START_COMMAND)) {
            startGame();
        }
    }
}
