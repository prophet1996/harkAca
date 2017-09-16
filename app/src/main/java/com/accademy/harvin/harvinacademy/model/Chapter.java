
package com.accademy.harvin.harvinacademy.model;

import java.util.Collections;
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