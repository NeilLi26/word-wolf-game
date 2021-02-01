package ui;

import model.Player;
import model.WordPair;

import java.util.List;

public class Main {

    private List<Player> players;
    private List<WordPair> wordList;

    public static void main(String[] args) {
        System.out.println("Welcome to Neil's Word Wolf game!");
        System.out.println("1: Add players");
        System.out.println("2: Add words");
        System.out.println("3: Start game");
        System.out.println("4: Exit");
    }

    //TODO:1 finish the things that will be seen if they want to create a player
    public void addingPlayers() {
        System.out.println("What would you like to name this player?");
        System.out.println("What ");
    }

    //TODO:2 finish the things that will be seen if they want to add words
    public void addWordPairs() {
        System.out.println("What is the first word that you would like to add?");
        System.out.println("What is the second word that you would like to add?");
        System.out.println("Words added");
    }

    //TODO:3 make a default list of similar words
    public List<WordPair> makeDefaultWordlist() {
        return null;
    }
}
