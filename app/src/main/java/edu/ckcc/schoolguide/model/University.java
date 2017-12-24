package edu.ckcc.schoolguide.model;

import com.google.gson.annotations.SerializedName;

public class University {

    @SerializedName("title")
    private String title;

    @SerializedName("image")
    private String imageUrl;

    @SerializedName("photo")
    private String photoUrl;

    @SerializedName("description")
    private String description;

    @SerializedName("tel")
    private String tel;

    @SerializedName("email")
    private String email;

    @SerializedName("address")
    private String address;

    public University(String title, String imageUrl, String photoUrl, String description, String tel, String email, String address) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.photoUrl = photoUrl;
        this.description = description;
        this.tel = tel;
        this.email = email;
        this.address = address;
    }

    public String getTitle() { return title; }

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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}