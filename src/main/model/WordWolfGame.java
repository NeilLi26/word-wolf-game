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

    public WordWolfGame(List<Player> players, String minorityWord, String majorityWord) {
        this.players = players;
        this.minorityWord = minorityWord;
        this.majorityWord = majorityWord;
        findRoleAmounts();
        assignWords();
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

    public Role getVictor() {
        return victor;
    }

    //setters
    public void setVictor(Role victor) {
        this.victor = victor;
    }

    //MODIFIES: this
    //EFFECTS: find the number of players in each non majority role
    private void findRoleAmounts() {
        remainingWolfs = 0;
        remainingWhites = 0;
        for (Player p: players) {
            if (p.getRole().equals(Role.MINORITY)) {
                remainingWolfs++;
            } else if (p.getRole().equals(Role.MRWHITE)) {
                remainingWhites++;
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: gives the appropriate words to all the players
    private void assignWords() {
        for (Player p: players) {
            if (p.getRole() == Role.MRWHITE) {
                p.setWord("You are the Mr. White");
            } else if (p.getRole() == Role.MINORITY) {
                p.setWord(minorityWord);
            } else {
                p.setWord(majorityWord);
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: kick a player of the given name and return true if they are in players,
    //          else return false
    public Boolean kickPlayer(String name) {
        for (Player p: players) {
            if (p.getName().equals(name)) {
                players.remove(p);
                System.out.println(name + " has been voted out");

                if (p.getRole() == Role.MINORITY) {
                    remainingWolfs--;
                } else if (p.getRole() == Role.MRWHITE) {
                    remainingWhites--;
                }

                return true;
            }
        }

        System.out.println("No player of given name");
        return false;
    }
}
