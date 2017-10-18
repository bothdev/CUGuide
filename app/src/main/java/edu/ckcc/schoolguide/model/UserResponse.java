package edu.ckcc.schoolguide.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Computer on 10/17/2017.
 */

public class UserResponse {
    @SerializedName("_code")
    public int code;
    @SerializedName("_message")
    public String message;
    @SerializedName("_id")
    public int id;
}
