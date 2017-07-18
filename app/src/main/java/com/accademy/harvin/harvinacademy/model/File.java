
package com.accademy.harvin.harvinacademy.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class File {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("fileName")
    @Expose
    private String fileName;
    @SerializedName("fileType")
    @Expose
    private String fileType;
    @SerializedName("filePath")
    @Expose
    private String filePath;
    @SerializedName("uploadDate")
    @Expose
    private String uploadDate;
    @SerializedName("fileSize")
    @Expose
    private Integer fileSize;
    @SerializedName("subjectName")
    @Expose
    private String subjectName;
    @SerializedName("chapterName")
    @Expose
    private String chapterName;
    @SerializedName("topicName")
    @Expose
    private String topicName;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public File withId(String id) {
        this.id = id;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public File withFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public File withFileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public File withFilePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public File withUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
        return this;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    public File withFileSize(Integer fileSize) {
        this.fileSize = fileSize;
        return this;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public File withSubjectName(String subjectName) {
        this.subjectName = subjectName;
        return this;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public File withChapterName(String chapterName) {
        this.chapterName = chapterName;
        return this;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public File withTopicName(String topicName) {
        this.topicName = topicName;
        return this;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public File withV(Integer v) {
        this.v = v;
        return this;
    }

}