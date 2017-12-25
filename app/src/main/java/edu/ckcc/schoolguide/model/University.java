package edu.ckcc.schoolguide.model;

import com.google.gson.annotations.SerializedName;

public class University {

    @SerializedName("title")
    private String title;

    @SerializedName("image")
    private String image;

    @SerializedName("photo")
    private String photo;

    @SerializedName("description")
    private String description;

    @SerializedName("tel")
    private String tel;

    @SerializedName("email")
    private String email;

    @SerializedName("address")
    private String address;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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