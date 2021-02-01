package model;

/*
the word wolf game with a list of players, possible words pairs to choose from,
and number of wolfs remaining
 */

import java.util.List;

public class WordWolfGame {
    private List<Player> players;
    private String minorityWord;
    private String majorityWord;
    private int remainingWolfs;
    private int remainingWhites;
    private Role victor;

    public WordWolfGame(List<Player> players, String minorityWord, String majorityWord,
            int totalWolves, int totalWhites) {
        this.players = players;
        this.minorityWord = minorityWord;
        this.majorityWord = majorityWord;
        this.remainingWhites = totalWolves;
        this.remainingWhites = totalWhites;
    }

    //getters
    public List<Player> getPlayers() {
        return players;
    }

    public String getMajorityWord() {
        return majorityWord;
    }

    public String getMinorityWord() {
        return minorityWord;
    }

    public int getRemainingWolfs() {
        return remainingWolfs;
    }

    public int getRemainingWhites() {
        return remainingWhites;
    }



}
