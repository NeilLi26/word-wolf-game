package model;

/*
A Player is a player in the game, who choose their name on screen,
have an assigned role, whether or not their current word is visible,
and may or may not have been voted out.
*/

@SuppressWarnings("checkstyle:RightCurly")
public class Player {
    private String name;
    private Role role;
    private boolean wordVisible;
    private boolean isActive;

    public Player(String name) {
        this.name = name;
        wordVisible = false;
        isActive = false;
    }

    //getters
    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }

    public boolean isWordVisible() {
        return wordVisible;
    }

    public boolean isActive() {
        return isActive;
    }

    //setters
    public void setRole(Role role) {
        this.role = role;
    }

    public void seeWord() {
        this.wordVisible = true;
    }

    public void hideWord() {
        this.wordVisible = false;
    }

    public void activate() {
        isActive = true;
    }

    public void deActivate() {
        isActive = false;
    }
}
