package edu.psu.ist.hcdd340.finalproject;

final class VisionBoard {
    private String userName;
    private String vbTitle;
    private String createdOn;
    private DreamHouse dreamHouse;
    private DreamJob dreamJob;
    private DreamYou dreamYou;
    private DreamPet dreamPet;
    public static final String VISION_BOARD_TAG = "VISION_BOARD";

    public VisionBoard(String userName, String vbTitle, String createdOn, DreamHouse dreamHouse, DreamJob dreamJob, DreamYou dreamYou, DreamPet dreamPet) {
        this.userName = userName;
        this.vbTitle = vbTitle;
        this.createdOn = createdOn;
        this.dreamHouse = dreamHouse;
        this.dreamJob = dreamJob;
        this.dreamYou = dreamYou;
        this.dreamPet = dreamPet;
    }

    public String getVbTitle() {
        return vbTitle;
    }

    public String getDate() {
        return createdOn;
    }
}
