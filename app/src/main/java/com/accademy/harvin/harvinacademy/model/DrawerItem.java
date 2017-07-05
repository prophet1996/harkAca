package com.accademy.harvin.harvinacademy.model;

/**
 * Created by ishank on 5/7/17.
 */

public class DrawerItem {
    String ItemName;
    int imgResId;

    public DrawerItem(String itemName, int imgResId) {
        ItemName = itemName;
        this.imgResId = imgResId;
    }

    public String getItemName() {
        return ItemName;
    }

    public int getImgResId() {
        return imgResId;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public void setImgResId(int imgResId) {
        this.imgResId = imgResId;
    }
}
