
package com.accademy.harvin.harvinacademy.model.user;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Batches {

    @SerializedName("batches")
    @Expose
    private List<Batch> batches = null;

    public List<Batch> getBatches() {
        return batches;
    }

    public void setBatches(List<Batch> batches) {
        this.batches = batches;
    }

}
