
package com.accademy.harvin.harvinacademy.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;public class Topic {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("topicName")
    @Expose
    private String topicName;
    @SerializedName("topicDescription")
    @Expose
    private String topicDescription;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("files")
    @Expose
    private List<File> files = null;

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