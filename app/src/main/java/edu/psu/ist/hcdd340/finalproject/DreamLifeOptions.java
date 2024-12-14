package edu.psu.ist.hcdd340.finalproject;

public class DreamLifeOptions {
    //fields
    private final String name;
    private final int imageID;

    //constructor


    public DreamLifeOptions(String name, int imageID) {
        this.name = name;
        this.imageID = imageID;
    }

    //getters

    public String getName() {
        return name;
    }

    public int getImageID() {
        return imageID;
    }
}
