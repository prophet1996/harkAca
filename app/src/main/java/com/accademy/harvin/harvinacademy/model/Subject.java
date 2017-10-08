
package com.accademy.harvin.harvinacademy.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
@Entity
public class Subject {

    @SerializedName("_id")
    @Expose
    @ColumnInfo(name = "id")
    public
    @PrimaryKey
    @NonNull
    String id;
    @SerializedName("subjectName")
    @Expose
    public String subjectName;
    @SerializedName("__v")
    @Expose
    public Integer v;
    @Ignore
    @SerializedName("chapters")
    @Expose
    public List<Chapter> chapters = null;
    @Expose
    @SerializedName("class")
    public String classid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
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