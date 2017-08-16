
package com.accademy.harvin.harvinacademy.model.user;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Batch {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("batchName")
    @Expose
    private String batchName;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("subjects")
    @Expose
    private List<String> subjects = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

}
