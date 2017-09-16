package com.accademy.harvin.harvinacademy.model.user;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Progresses {

    @SerializedName("progresses")
    @Expose
    private List<Progress> progresses = null;

    public List<Progress> getProgresses() {
        return progresses;
    }

    public void setProgresses(List<Progress> progresses) {
        this.progresses = progresses;
    }

}