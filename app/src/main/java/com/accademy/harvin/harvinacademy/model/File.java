
package com.accademy.harvin.harvinacademy.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
@Entity
public class File {

    @SerializedName("_id")
    @Expose
    public
    @PrimaryKey
    @NonNull
    String id;
    @SerializedName("fileName")
    @Expose
    public String fileName;
    @SerializedName("fileType")
    @Expose
    public String fileType;
    @SerializedName("filePath")
    @Expose
    public String filePath;
    @SerializedName("uploadDate")
    @Expose
    public String uploadDate;
    @SerializedName("fileSize")
    @Expose
    public Integer fileSize;
    @SerializedName("subject")
    @Expose
    public String subjectId;
    @SerializedName("chapter")
    @Expose
    public String chapterId;

    @SerializedName("__v")
    @Expose
    public Integer v;
    @Expose
    @SerializedName("topic")
    @ColumnInfo(name="topicId")
    public String topicId;
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

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public File withSubjectName(String subjectName) {
        this.subjectId = subjectName;
        return this;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public File withChapterName(String chapterName) {
        this.chapterId = chapterName;
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