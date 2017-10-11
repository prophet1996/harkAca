
package com.accademy.harvin.harvinacademy.model.user;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity
public class Progress {

    @SerializedName("_id")
    @Expose
    @PrimaryKey
    @NonNull
    private String id;
    @SerializedName("chapter")
    @Expose
    private String chapter;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("completed")
    @Expose
    private String completed;
    @Ignore
    public List<String> completedTopicsIds;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }

}