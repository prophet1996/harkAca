
package com.accademy.harvin.harvinacademy.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Subjects {

    @SerializedName("subjects")
    @Expose
    private List<Subject> subjects = null;


    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }





}