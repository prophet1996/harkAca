
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


    public String getSubjectName() {
        return subjectName;
    }


    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }



    public List<Chapter> getChapters() {
        return chapters;
    }


}