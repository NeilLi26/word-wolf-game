package ui.gui.menupanels;

import model.Player;
import model.PlayersAndWordPairs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
menu for adding and removing players
 */
public class PlayersMenu extends MenuPanel implements ActionListener {
    private static final String ADD_PLAYER_COMMAND = "addPlayer";
    private static final String REMOVE_PLAYER_COMMAND = "removePlayer";
    private static final int MAX_PLAYERS_SIZE = 10;

    //Java swing fields
    private DefaultListModel<String> playersListModel;
    private JList<String> playersJList;
    private JTextField field;

    //EFFECTS: creates a new PlayersMenu
    public PlayersMenu(PlayersAndWordPairs playersAndWordPairs) {
        super(playersAndWordPairs);

        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        generateListModel(gbc);
        generateAddingPlayerPortion(gbc);
        generateRemovingPlayerPortion(gbc);
    }

    @Override
    public void updateJList() {
        playersListModel = new DefaultListModel<>();

        for (Player p: playersAndWordPairs.getPlayers()) {
            playersListModel.addElement(p.getName());
        }

        playersJList.setModel(playersListModel);
    }

    //MODIFIES: this
    //EFFECTS: creates a list model from the player list and adds it to this
    private void generateListModel(GridBagConstraints gbc) {
        playersListModel = new DefaultListModel<>();

        for (Player p: playersAndWordPairs.getPlayers()) {
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
    //EFFECTS: creates the JPanel for adding a player and adds it to this
    private void generateAddingPlayerPortion(GridBagConstraints gbc) {
        JPanel addPlayerPanel = new JPanel();
        field = new JTextField(12);
        JButton addingPlayerButton = new JButton("Add");
        addingPlayerButton.setActionCommand(ADD_PLAYER_COMMAND);
        addingPlayerButton.addActionListener(this);
        addPlayerPanel.add(field);
        addPlayerPanel.add(addingPlayerButton);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        add(addPlayerPanel, gbc);
    }

    //MODIFIES: this
    //EFFECTS: creates the JPanel for removing a player and adds it to this
    private void generateRemovingPlayerPortion(GridBagConstraints gbc) {
        JPanel removingPlayerPanel = new JPanel();
        JButton removePlayerButton = new JButton("Remove");
        removePlayerButton.setActionCommand(REMOVE_PLAYER_COMMAND);
        removePlayerButton.addActionListener(this);
        removingPlayerPanel.add(removePlayerButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridheight = 1;
        add(removingPlayerPanel, gbc);
    }

    //EFFECTS: handles action events
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals(ADD_PLAYER_COMMAND)) {
            if (playersAndWordPairs.getPlayers().size() < MAX_PLAYERS_SIZE) {
                playersAndWordPairs.addPlayer(new Player(field.getText()));
                playersListModel.addElement(field.getText());
                field.setText("");
            } else {
                field.setText("Too many players");
            }
            displayPlayers();
        } else if (command.equals(REMOVE_PLAYER_COMMAND)) {
            int removedPos = playersJList.getSelectedIndex();
            System.out.println(removedPos);
            playersListModel.remove(removedPos);
            playersAndWordPairs.removePlayer(removedPos);
            displayPlayers();
        }
    }

    //EFFECTS: displays the players in players and words
    private void displayPlayers() {
        int counter = 0;

        for (Player p: playersAndWordPairs.getPlayers()) {
            spaceNames(counter, p);
            counter++;
        }

        if (counter % 2 == 1) {
            System.out.println();
        }
    }

    //EFFECTS: prints the names with proper spacing and alignment
    private void spaceNames(int counter, Player p) {
        if (counter % 2 == 0) {
            System.out.print(p.getName());

            for (int x = 0; x < 10 - p.getName().length(); x++) {
                System.out.print(" ");
            }

        } else {
            System.out.println(p.getName());
        }
    }
}
