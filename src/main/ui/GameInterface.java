package ui;

import model.Player;
import model.Role;
import model.WordPair;
import model.WordWolfGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

/*
the interface of a single round of the word wolf game
 */
public class GameInterface {
    private static final int PRINT_NAME_SPACING = 25;

    private Scanner input;
    private GameState gameState;
    private WordWolfGame currentGame;
    private Random rand = new Random();

    private enum GameState {
        SHOWING_WORDS, DESCRIBING_WORD, PLAYERS_VOTING, END
    }

    public GameInterface(List<Player> players, List<WordPair> wordList) {
        initGame(players, wordList);
        runCurrentGame();
    }

    //MODIFIES: this
    //EFFECTS: choose the right amount of players for each of the minority roles given the current number of players,
    // and assign roles to them accordingly
    private List<Player> assignRoles(List<Player> players) {
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

    //EFFECTS: runs a single round of the word wolf game
    private void runCurrentGame() {
        boolean gameRunning = true;

        while (gameRunning) {
            if (gameState == GameState.SHOWING_WORDS) {
                displayPLayerWordMenu();
                showPlayerWord();
            } else if (gameState == GameState.DESCRIBING_WORD) {
                displayDescribingPlayers();
            } else if (gameState == GameState.PLAYERS_VOTING) {
                displayVotingMenu();
                boolean continueGame = processVote();
                if (continueGame) {
                    gameState = GameState.DESCRIBING_WORD;
                } else {
                    gameState = GameState.END;
                }
            } else if (gameState == GameState.END) {
                displayEndOfRound();
                gameRunning = false;
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: assigns the roles randomly to players, and then assign words of a given wordpair accordingly
    private void initGame(List<Player> players, List<WordPair> wordList) {
        input = new Scanner(System.in);
        List<Player> assignedPlayers = assignRoles(copyPlayers(players));
        WordPair majorMinorPair = chooseWordPair(wordList);//the first one will be the major word, second one the minor
        currentGame = new WordWolfGame(assignedPlayers, majorMinorPair.getFirstWord(), majorMinorPair.getSecondWord());
        gameState = GameState.SHOWING_WORDS;
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
    //EFFECTS: display the menu for when the players get to see their words
    private void displayPLayerWordMenu() {
        System.out.println("Who's word would you like to see?");
        displayPlayers();
        System.out.println("Enter continue to start the game");
    }

    //EFFECTS: prints out the players, with two on each row
    private void displayPlayers() {
        int counter = 0;

        for (Player p: currentGame.getPlayers()) {
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

            for (int x = 0; x < PRINT_NAME_SPACING - p.getName().length(); x++) {
                System.out.print(" ");
            }

        } else {
            System.out.println(p.getName());
        }
    }

    //EFFECTS: prints out the players words, with two on each row
    private void displayWords() {
        int counter = 0;

        for (Player p: currentGame.getPlayers()) {
            spaceWords(counter, p);
            counter++;
        }

        if (counter % 2 == 1) {
            System.out.println();
        }
    }


    //EFFECTS: prints the words with proper spacing and alignment
    private void spaceWords(int counter, Player p) {
        if (counter % 2 == 0) {
            System.out.print(p.getWord());

            for (int x = 0; x < PRINT_NAME_SPACING - p.getWord().length(); x++) {
                System.out.print(" ");
            }

        } else {
            System.out.println(p.getWord());
        }
    }

    //EFFECTS: displays the player whos name of given name, and return true if
    // a player of given name is found, return false otherwise
    private boolean showPlayerWord() {
        String name = input.next();

        if (name.equals("continue")) {
            gameState = GameState.DESCRIBING_WORD;
            return true;
        } else if (name.equals("savemetimeohgreatneil")) {
            System.out.println("I suppose I must - neil");
            displayPlayers();
            displayWords();
            return true;
        }

        return displayPlayerWord(name);
    }

    //EFFECTS: display the name of the player and return true if a player with given name is found, and false otherwise
    private boolean displayPlayerWord(String name) {
        for (Player p: currentGame.getPlayers()) {
            if (p.getName().equals(name)) {
                System.out.println("Your word is:" + p.getWord());
                String command;
                System.out.println("Enter anything to continue:");
                command = input.next();
                for (int x = 0; x < 20; x++) {
                    System.out.println();
                }
                return true;
            }
        }
        System.out.println("There is no player with such a name");
        return false;
    }

    //MODIFIES: this
    //EFFECTS: display the players who will be describing their word, return true when the describing has been completed
    private boolean displayDescribingPlayers() {
        String command;

        for (Player p: currentGame.getPlayers()) {
            System.out.println(p.getName() + "'s turn to describe their word:");

            do {
                System.out.println("enter continue to go proceed to next player");
                command = input.next();

                if (command.equals("savemetimeohgreatneil")) {
                    System.out.println("Time saved -neil");
                    gameState = GameState.PLAYERS_VOTING;
                    return true;
                }

            } while (!command.equals("continue"));

            for (int x = 0; x < 10; x++) {
                System.out.println();
            }
        }

        System.out.println("everyone has had their turn");
        gameState = GameState.PLAYERS_VOTING;
        return true;
    }

    //MODIFIES: this
    //EFFECTS: display the voting menu for the players
    private void displayVotingMenu() {
        System.out.println("Vote for a player, skip, wolves guess word, or Mr. White guess words:");
        System.out.println("Remaining Wolves: " + currentGame.getRemainingWolfs());
        if (currentGame.getRemainingWhites() > 0) {
            System.out.println("The mr. White is still present");
        }
        displayPlayers();
        System.out.println("Enter skip to skip this round of voting");
        System.out.println("Enter wolf for wolf guess word");
        System.out.println("Enter white for white guess word");
    }

    //EFFECTS: processes the vote, return true if there is no victor decided after this stage, and false otherwise
    private boolean processVote() {
        String vote = input.next();

        if (vote.equals("skip")) {
            System.out.println("voting round skipped");
        } else if (vote.equals("wolf")) {
            return wolfGuess();
        } else if (vote.equals("white")) {
            return whiteGuess();
        } else {
            currentGame.kickPlayer(vote);
        }

        if (currentGame.getRemainingWhites() + currentGame.getRemainingWolfs() >= currentGame.getPlayers().size() / 2) {
            currentGame.setVictor(Role.MINORITY);
            return false;
        } else if (currentGame.getRemainingWolfs() == 0 && currentGame.getRemainingWhites() == 0) {
            currentGame.setVictor(Role.MAJORITY);
            return false;
        }

        return true;
    }

    //EFFECTS: wolf player makes a guess as to the majority word, return true if correct, false otherwise
    private boolean wolfGuess() {
        String guess;
        String guesser;

        System.out.println("Who is making the guess?");
        guesser = input.next();
        System.out.println("What do you think is the word?");
        guess = input.next();

        return processWolfGuess(guesser, guess);
    }

    //EFFECTS: processes the guess of the wolf
    private boolean processWolfGuess(String guesser, String guess) {
        if (guess.equals(currentGame.getMajorityWord())) {
            System.out.println("That is the correct word!");
            currentGame.setVictor(Role.MINORITY);
            return false;
        } else {
            return processGuessWrong(guesser);
        }
    }

    //EFFECTS: displays the console for the Mr white to make a guess, and asks for their guesses
    private boolean whiteGuess() {
        String guessMajor;
        String guessMinor;
        String guesser;

        System.out.println("Who is making the guess?");
        guesser = input.next();
        System.out.println("What do you think is the word for the Majority?");
        guessMajor = input.next();
        System.out.println("What do you think is the word for the Minority?");
        guessMinor = input.next();

        return processWhiteGuess(guesser, guessMajor, guessMinor);
    }

    //EFFECTS: processes the guess of the Mr White
    private boolean processWhiteGuess(String guesser, String guessMajor, String guessMinor) {
        if (guessMajor.equals(currentGame.getMajorityWord()) && guessMinor.equals(currentGame.getMinorityWord())) {
            System.out.println("Those are the correct words!");
            currentGame.setVictor(Role.MRWHITE);
            return false;
        } else {
            return processGuessWrong(guesser);
        }
    }

    //EFFECTS: processes the guess if the guess was wrong
    private boolean processGuessWrong(String player) {
        System.out.println("That was not correct, but does everyone accept this answer?");
        System.out.println("Enter yes for accept, and no for not accept");
        String accept = input.next();

        if (accept.equals("yes")) {
            System.out.println("The guess has been accepted");
            currentGame.kickPlayer(player);
            return false;
        } else {
            System.out.println("The guess has not been accepted");
            return true;
        }
    }

    //EFFECTS: displays the victor and ends the game
    private void displayEndOfRound() {
        if (currentGame.getVictor().equals(Role.MAJORITY)) {
            System.out.println("The Majority has won!!!");
        } else if  (currentGame.getVictor().equals(Role.MINORITY)) {
            System.out.println("The Wolfs have won!!!");
        } else if  (currentGame.getVictor().equals(Role.MRWHITE)) {
            System.out.println("Mr White has won!!!");
        }

    }
}
