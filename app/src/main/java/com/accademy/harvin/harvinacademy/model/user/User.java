
package com.accademy.harvin.harvinacademy.model.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.accademy.harvin.harvinacademy.MainActivity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("profile")
    @Expose
    private Profile profile;
    @SerializedName("isAdmin")
    @Expose
    private Boolean isAdmin;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public static String getSharedUsername(Context context) {


    String username;
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

    username =sharedPreferences.getString("username","z");
        Log.d("username",username);
        return username;
}

}
