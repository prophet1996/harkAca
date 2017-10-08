
package com.accademy.harvin.harvinacademy.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
@Entity
public class Topic {

    @SerializedName("_id")
    @Expose
    public
    @PrimaryKey
    @NonNull
    String id;
    @SerializedName("topicName")
    @Expose
    public String topicName;
    @SerializedName("topicDescription")
    @Expose
    public String topicDescription;
    @SerializedName("__v")
    @Expose
    public Integer v;
    @SerializedName("files")
    @Ignore
    @Expose
    public List<File> files = null;
    @Expose
    @SerializedName("chapter")
    public String chapterId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getTopicName() {
        return topicName;
    }



    public String getTopicDescription() {
        return topicDescription;
    }



    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }


    public List<File> getFiles() {
        return files;
    }



}