package model;

import org.json.JSONObject;
import persistence.Writable;

/*
A pair of similar words
 */
public class WordPair implements Writable {
    private String firstWord;
    private String secondWord;

    public WordPair(String firstWord, String secondWord) {
        this.firstWord = firstWord;
        this.secondWord = secondWord;
    }

    //getters
    public String getFirstWord() {
        return firstWord;
    }

    public String getSecondWord() {
        return secondWord;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("firstWord", firstWord);
        json.put("secondWord", secondWord);
        return json;
    }
}
