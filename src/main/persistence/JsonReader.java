package persistence;

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

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public List<WordPair> read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWordPairList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private List<WordPair> parseWordPairList(JSONObject jsonObject) {
        List<WordPair> wordPairList = new ArrayList<>();
        addWordPairs(wordPairList, jsonObject);
        return wordPairList;
    }

    // MODIFIES: wr
    // EFFECTS: parses thingies from JSON object and adds them to workroom
    private void addWordPairs(List<WordPair> wordPairList, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("wordPairs");
        for (Object json : jsonArray) {
            JSONObject nextWordPair = (JSONObject) json;
            addWordPair(wordPairList, nextWordPair);
        }
    }

    // MODIFIES: wr
    // EFFECTS: parses thingy from JSON object and adds it to workroom
    private void addWordPair(List<WordPair> wordPairList, JSONObject jsonObject) {
        String firstWord = jsonObject.getString("firstWord");
        String secondWord = jsonObject.getString("secondWord");
        WordPair wordPair = new WordPair(firstWord, secondWord);
        wordPairList.add(wordPair);
    }
}
