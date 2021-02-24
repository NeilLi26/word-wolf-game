package persistence;

import model.WordPair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of workroom to file
    public void write(List<WordPair> wordPairList) {
        JSONObject json = new JSONObject();
        json.put("wordPairs", wordPairToJson(wordPairList));

        saveToFile(json.toString(TAB));
    }

    //EFFECTS: returns the Word Pairs to a JSON array
    private JSONArray wordPairToJson(List<WordPair> wordPairList) {
        JSONArray jsonArray = new JSONArray();

        for (WordPair wp: wordPairList) {
            jsonArray.put(wp.toJson());
        }

        return jsonArray;
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
