
package com.accademy.harvin.harvinacademy.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Assignment {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("assignmentName")
    @Expose
    private String assignmentName;
    @SerializedName("uploadDate")
    @Expose
    private String uploadDate;
    @SerializedName("lastSubDate")
    @Expose
    private String lastSubDate;
    @SerializedName("filePath")
    @Expose
    private String filePath;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getLastSubDate() {
        return lastSubDate;
    }

    public void setLastSubDate(String lastSubDate) {
        this.lastSubDate = lastSubDate;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}
