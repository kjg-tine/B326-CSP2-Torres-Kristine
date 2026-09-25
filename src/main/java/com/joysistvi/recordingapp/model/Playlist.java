package com.joysistvi.recordingapp.model;

public class Playlist {

    private int id;
    private String dateCreated;
    private int userId;

    public Playlist(String dateCreated, int userId) {
        this.dateCreated = dateCreated;
        this.userId = userId;
    }

    public Playlist(int id, String dateCreated, int userId) {
        this.id = id;
        this.dateCreated = dateCreated;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "id=" + id +
                ", dateCreated='" + dateCreated + '\'' +
                ", userId=" + userId +
                '}';
    }
}