
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

    public Topic withId(String id) {
        this.id = id;
        return this;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public Topic withTopicName(String topicName) {
        this.topicName = topicName;
        return this;
    }

    public String getTopicDescription() {
        return topicDescription;
    }

    public void setTopicDescription(String topicDescription) {
        this.topicDescription = topicDescription;
    }

    public Topic withTopicDescription(String topicDescription) {
        this.topicDescription = topicDescription;
        return this;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public Topic withV(Integer v) {
        this.v = v;
        return this;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public Topic withFiles(List<File> files) {
        this.files = files;
        return this;
    }

}