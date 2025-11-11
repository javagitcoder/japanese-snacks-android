package com.example.snacklearn.db;

public class Snack {
    private int id;
    private String title;
    private String japanese;
    private String english;
    private String description;
    private String imageName;

    public Snack() {}

    public Snack(int id, String title, String japanese, String english,
                 String description, String imageName) {
        this.id = id;
        this.title = title;
        this.japanese = japanese;
        this.english = english;
        this.description = description;
        this.imageName = imageName;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getJapanese() { return japanese; }
    public void setJapanese(String japanese) { this.japanese = japanese; }

    public String getEnglish() { return english; }
    public void setEnglish(String english) { this.english = english; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageName() { return imageName; }
    public void setImageName(String imageName) { this.imageName = imageName; }
}