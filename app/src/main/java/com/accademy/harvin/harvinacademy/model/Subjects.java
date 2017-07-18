
package com.accademy.harvin.harvinacademy.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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

    public Subjects withSubjects(List<Subject> subjects) {
        this.subjects = subjects;
        return this;
    }

}
