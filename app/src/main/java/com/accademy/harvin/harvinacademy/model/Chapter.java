
package com.accademy.harvin.harvinacademy.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Relation;
import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
@Entity
public class Chapter {

    @SerializedName("_id")
    @Expose
    public
    @ColumnInfo(name="id")
    @PrimaryKey
    @NonNull
    String id;
    @SerializedName("chapterName")
    @Expose
    public String chapterName;

    @SerializedName("chapterDescription")
    @Expose
    public String chapterDescription;
    @SerializedName("__v")
    @Expose
    public Integer v;
    @SerializedName("topics")
    @Ignore
    @Expose
    public List<Topic> topics = null;
    @ColumnInfo(name="subjectId")
    @Expose
    @SerializedName("subject")
    public String subjectId;

    public String getSubjectId() {
        return subjectId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getChapterName() {
        return chapterName;
    }



    public String getChapterDescription() {
        return chapterDescription;
    }



    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }



    public List<Topic> getTopics() {
        return topics;
    }
public List<Topic> getTopicsAndReverse(){
    Collections.reverse(topics);
    return topics;

}


}