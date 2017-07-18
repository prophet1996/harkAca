
package com.accademy.harvin.harvinacademy.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Chapter {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("chapterName")
    @Expose
    private String chapterName;
    @SerializedName("chapterDescription")
    @Expose
    private String chapterDescription;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("topics")
    @Expose
    private List<Topic> topics = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Chapter withId(String id) {
        this.id = id;
        return this;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public Chapter withChapterName(String chapterName) {
        this.chapterName = chapterName;
        return this;
    }

    public String getChapterDescription() {
        return chapterDescription;
    }

    public void setChapterDescription(String chapterDescription) {
        this.chapterDescription = chapterDescription;
    }

    public Chapter withChapterDescription(String chapterDescription) {
        this.chapterDescription = chapterDescription;
        return this;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public Chapter withV(Integer v) {
        this.v = v;
        return this;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    public Chapter withTopics(List<Topic> topics) {
        this.topics = topics;
        return this;
    }

}