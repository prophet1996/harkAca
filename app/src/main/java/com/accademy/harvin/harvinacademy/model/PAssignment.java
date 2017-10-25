
package com.accademy.harvin.harvinacademy.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PAssignment {

    @SerializedName("assignments")
    @Expose
    private List<Assignment> assignments = null;

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

}
