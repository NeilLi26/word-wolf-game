package ui.gui.menupanels;

import model.PlayersAndWordPairs;
import model.WordPair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
panel for adding or removing word pairs
 */
public class WordsMenu extends MenuPanel implements ActionListener {
    private static final String ADD_WORD_PAIR_COMMAND = "addWordPair";
    private static final String REMOVE_WORD_PAIR_COMMAND = "removeWordPair";

    //Java swing fields
    private DefaultListModel<String> wordPairsListModel;
    private JList<String> wordPairsJList;
    private JTextField firstWordField;
    private JTextField secondWordField;

    //EFFECTS: constructs a new WordsMenu
    public WordsMenu(PlayersAndWordPairs playersAndWordPairs) {
        super(playersAndWordPairs);

        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        generateListModel(gbc);
        generateAddingWordPairPortion(gbc);
        generateRemovingWordPairPortion(gbc);
    }

    @Override
    public void updateJList() {
        wordPairsListModel = new DefaultListModel<>();

        for (WordPair wordPair : playersAndWordPairs.getWordPairs()) {
            wordPairsListModel.addElement(wordPair.getFirstWord() + " and " + wordPair.getSecondWord());
        }

        wordPairsJList.setModel(wordPairsListModel);
    }

    //MODIFIES: this
    //EFFECTS: creates a list model from the wordPair list and adds it to this
    private void generateListModel(GridBagConstraints gbc) {
        wordPairsListModel = new DefaultListModel<>();

        for (WordPair wordPair : playersAndWordPairs.getWordPairs()) {
            wordPairsListModel.addElement(wordPair.getFirstWord() + " and " + wordPair.getSecondWord());
        }


        wordPairsJList = new JList<>(wordPairsListModel);
        wordPairsJList.setSelectedIndex(0);
        wordPairsJList.setSize(WIDTH, HEIGHT * 2 / 3);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        add(wordPairsJList, gbc);
    }

    //MODIFIES: this
    //EFFECTS: creates the JPanel for adding a word pair and adds it to this
    private void generateAddingWordPairPortion(GridBagConstraints gbc) {
        JPanel addPlayerPanel = new JPanel();

        firstWordField = new JTextField(12);
        secondWordField = new JTextField(12);

        JLabel inBetweenLabel = new JLabel(" and ");

        JButton addingWordPairButton = new JButton("Add");
        addingWordPairButton.setActionCommand(ADD_WORD_PAIR_COMMAND);
        addingWordPairButton.addActionListener(this);

        addPlayerPanel.add(firstWordField);
        addPlayerPanel.add(inBetweenLabel);
        addPlayerPanel.add(secondWordField);
        addPlayerPanel.add(addingWordPairButton);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        add(addPlayerPanel, gbc);
    }

    //EFFECTS: creates the JPanel for removing a word pair and adds it to this
    private void generateRemovingWordPairPortion(GridBagConstraints gbc) {
        JPanel removingWordPair = new JPanel();
        JButton removeWordPairButton = new JButton("Remove");
        removeWordPairButton.setActionCommand(REMOVE_WORD_PAIR_COMMAND);
        removeWordPairButton.addActionListener(this);
        removingWordPair.add(removeWordPairButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridheight = 1;
        add(removingWordPair, gbc);
    }

    //EFFECTS: handles action events
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals(ADD_WORD_PAIR_COMMAND)) {
            String firstWord = firstWordField.getText();
            String secondWord = secondWordField.getText();
            playersAndWordPairs.addWordPair(new WordPair(firstWord, secondWord));
            wordPairsListModel.addElement(firstWord + " and " + secondWord);
            firstWordField.setText("");
            secondWordField.setText("");
            displayWordPairs();
        } else if (command.equals(REMOVE_WORD_PAIR_COMMAND)) {
            int removedPos = wordPairsJList.getSelectedIndex();
            System.out.println(removedPos);
            wordPairsListModel.remove(removedPos);
            playersAndWordPairs.removeWordPair(removedPos);
            displayWordPairs();
        }
    }

    //EFFECTS: prints out all the word pairs in the wordlist
    private void displayWordPairs() {
        System.out.println("Words:");
        for (WordPair wp : playersAndWordPairs.getWordPairs()) {
            System.out.println(wp.getFirstWord() + " and " + wp.getSecondWord());
        }
    }
}
