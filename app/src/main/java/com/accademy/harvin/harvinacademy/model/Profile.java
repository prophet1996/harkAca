package com.accademy.harvin.harvinacademy.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ishank on 30/6/17.
 */

public class Profile {
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @SerializedName("username")
    @Expose
    String username;
    @SerializedName("password")
    @Expose
    String password;
    @SerializedName("batch")
    @Expose
    String batch;
    @SerializedName("phone")
    @Expose
    String phone;
    @SerializedName("emailId")
    @Expose
    String email;
    @SerializedName("fullName")
    @Expose
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Profile(String username, String password, String batch, String phone, String email, String name) {
        this.username = username;
        this.password = password;
        this.batch = batch;
        this.phone = phone;
        this.email = email;
        this.name=name;
    }
}
