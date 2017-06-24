package com.accademy.harvin.harvinacademy.model;

/**
 * Created by ishank on 24/6/17.
 */

public class Subject {
    private int sub_id;
    private String sub_desc;
    private String sub_name;

    public Subject(int sub_id, String sub_desc, String sub_name) {
        this.sub_id = sub_id;
        this.sub_desc = sub_desc;
        this.sub_name = sub_name;
    }

    public int getSub_id() {
        return sub_id;
    }

    public String getSub_desc() {
        return sub_desc;
    }

    public String getSub_name() {
        return sub_name;
    }
}
