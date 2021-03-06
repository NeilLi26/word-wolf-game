package persistence;

import model.Player;
import model.PlayersAndWordPairs;
import model.WordPair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/*
Reads from a Json file

the code in this class was modeled after the JsonReader class of the JsonSerializationDemo
https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 */
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads players and word list from file and returns it;
    // throws IOException if an error occurs reading data from file
    public PlayersAndWordPairs read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePlayersAndWordPairs(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses PlayersAndWordPairs from JSON object and returns it
    private PlayersAndWordPairs parsePlayersAndWordPairs(JSONObject jsonObject) {
        List<WordPair> wordPairList = new ArrayList<>();
        addWordPairs(wordPairList, jsonObject);
        List<Player> players = new ArrayList<>();
        addPlayers(players, jsonObject);
        return new PlayersAndWordPairs(players, wordPairList);
    }

    // MODIFIES: wordPairList
    // EFFECTS: parses wordPairs from JSON object and adds them to wordPairs
    private void addWordPairs(List<WordPair> wordPairList, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("wordPairs");
        for (Object json : jsonArray) {
            JSONObject nextWordPair = (JSONObject) json;
            addWordPair(wordPairList, nextWordPair);
        }
    }

    // MODIFIES: wordPairList
    // EFFECTS: parses a single wordPair from JSON object and adds it to wordPairs
    private void addWordPair(List<WordPair> wordPairList, JSONObject jsonObject) {
        String firstWord = jsonObject.getString("firstWord");
        String secondWord = jsonObject.getString("secondWord");
        WordPair wordPair = new WordPair(firstWord, secondWord);
        wordPairList.add(wordPair);
    }

    // MODIFIES: players
    // EFFECTS: parses Players from JSON object and adds them to players
    private void addPlayers(List<Player> players, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("players");
        for (Object json : jsonArray) {
            JSONObject nextWordPair = (JSONObject) json;
            addPlayer(players, nextWordPair);
        }
    }

    // MODIFIES: players
    // EFFECTS: parses a single player from JSON object and adds it to players
    private void addPlayer(List<Player> players, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Player player = new Player(name);
        players.add(player);
    }
}
