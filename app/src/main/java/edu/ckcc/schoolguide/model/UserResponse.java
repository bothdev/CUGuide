package edu.ckcc.schoolguide.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Computer on 10/17/2017.
 */

public class UserResponse {
    @SerializedName("code")
    public int code;
    @SerializedName("message")
    public String message;
    @SerializedName("id")
    public int id;
}
