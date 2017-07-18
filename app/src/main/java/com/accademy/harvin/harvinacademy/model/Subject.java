
package com.accademy.harvin.harvinacademy.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Subject {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("subjectName")
    @Expose
    private String subjectName;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("chapters")
    @Expose
    private List<Chapter> chapters = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Subject withId(String id) {
        this.id = id;
        return this;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Subject withSubjectName(String subjectName) {
        this.subjectName = subjectName;
        return this;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public Subject withV(Integer v) {
        this.v = v;
        return this;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    public Subject withChapters(List<Chapter> chapters) {
        this.chapters = chapters;
        return this;
    }

}