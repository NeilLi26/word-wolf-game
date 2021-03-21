package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.List;

public class PlayersAndWordPairs implements Writable {
    private List<Player> players;
    private List<WordPair> wordPairs;

    //EFFECTS: constructs a new PlayersAndWordPairs
    public PlayersAndWordPairs(List<Player> players, List<WordPair> wordPairs) {
        this.players = players;
        this.wordPairs = wordPairs;
    }

    //getters
    public List<Player> getPlayers() {
        return players;
    }

    public List<WordPair> getWordPairs() {
        return wordPairs;
    }

    //setters
    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void setWordPairs(List<WordPair> wordPairs) {
        this.wordPairs = wordPairs;
    }

    //adding

    //MODIFIES: this
    //EFFECTS: adds a player to the player list
    public void addPlayer(Player player) {
        players.add(player);
    }

    //MODIFIES: this
    //EFFECTS: adds a word pair to the word pair list
    public void addWordPair(WordPair wordPair) {
        wordPairs.add(wordPair);
    }

    //MODIFIES: this
    //EFFECTS: adds a player to the player list
    public void removePlayer(int index) {
        players.remove(index);
    }

    //MODIFIES: this
    //EFFECTS: adds a word pair to the word pair list
    public void removeWordPair(int index) {
        wordPairs.remove(index);
    }

    // EFFECTS: returns this as JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("wordPairs", wordPairToJson(wordPairs));
        json.put("players", playersToJson(players));
        return json;
    }

    //EFFECTS: returns the Word Pairs to a JSON array
    private JSONArray wordPairToJson(List<WordPair> wordPairList) {
        JSONArray jsonArray = new JSONArray();

        for (WordPair wp: wordPairList) {
            jsonArray.put(wp.toJson());
        }

        return jsonArray;
    }

    //EFFECTS: returns the Word Pairs to a JSON array
    private JSONArray playersToJson(List<Player> players) {
        JSONArray jsonArray = new JSONArray();

        for (Player p: players) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }
}
