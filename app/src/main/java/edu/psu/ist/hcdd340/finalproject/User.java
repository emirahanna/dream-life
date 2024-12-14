package edu.psu.ist.hcdd340.finalproject;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String password;
    private List<VisionBoard> visionBoardList; // Optional: If you associate storyboards with users.

    // Constructor
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.visionBoardList = new ArrayList<>();
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<VisionBoard> getVisionBoardList() {
        return visionBoardList;
    }

    public void setVisionBoardList(List<VisionBoard> visionBoardList) {
        this.visionBoardList = visionBoardList;
    }
}
