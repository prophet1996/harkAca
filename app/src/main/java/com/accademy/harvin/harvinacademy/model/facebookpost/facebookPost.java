package com.accademy.harvin.harvinacademy.model.facebookpost;

import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Created by ishank on 1/8/17.
 */

public class facebookPost {
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Uri getImage() {
        return image;
    }

    public void setImage(Uri image) {
        this.image = image;
    }

    public Bitmap getPostImage() {
        return postImage;
    }

    public void setPostImage(Bitmap postImage) {
        this.postImage = postImage;
    }

    private String title;
    private String desc;
    private String id;
    private Uri image;
    private Bitmap postImage;
}
