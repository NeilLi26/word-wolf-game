package model;

/*
A pair of similar words
 */
public class WordPair {
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
}
