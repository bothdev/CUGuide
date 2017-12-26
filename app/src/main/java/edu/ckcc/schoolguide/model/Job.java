package edu.ckcc.schoolguide.model;

import com.google.gson.annotations.SerializedName;

public class Job {

    @SerializedName("title")
    private String title;

    @SerializedName("image")
    private String imageUrl;

    @SerializedName("photo")
    private String photoUrl;

    @SerializedName("description")
    private String description;

    @SerializedName("closingdate")
    private String closingdate;

    public Job(String title, String imageUrl, String photoUrl, String description, String closingdate) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.photoUrl = photoUrl;
        this.description = description;
        this.closingdate = closingdate;
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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClosingdate() {
        return closingdate;
    }

    public void setClosingdate(String closingdate) {
        this.closingdate = closingdate;
    }
}