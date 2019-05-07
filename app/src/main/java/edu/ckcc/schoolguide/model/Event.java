package edu.ckcc.schoolguide.model;

import com.google.gson.annotations.SerializedName;

public class Event {

    @SerializedName("title")
    private String title;

    @SerializedName("image")
    private String imageUrl;

    @SerializedName("photo")
    private String photoUrl;

    @SerializedName("description")
    private String description;

    @SerializedName("deadline")
    private String deadline;

    public Event(String title, String imageUrl, String photoUrl, String description, String deadline) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.photoUrl = photoUrl;
        this.description = description;
        this.deadline = deadline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPhotoUrl() { return photoUrl; }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
}