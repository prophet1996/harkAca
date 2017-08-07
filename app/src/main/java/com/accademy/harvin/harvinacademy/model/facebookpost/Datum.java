
package com.accademy.harvin.harvinacademy.model.facebookpost;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("attachments")
    @Expose
    private Attachments attachments;
    @SerializedName("id")
    @Expose
    private String id;

    public Attachments getAttachments() {
        return attachments;
    }

    public void setAttachments(Attachments attachments) {
        this.attachments = attachments;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
