package com.accademy.harvin.harvinacademy.model;

/**
 * Created by ishank on 24/6/17.
 */

public class Chapter {
    private int chapter_id;
    private String chapter_desc;
    private String chapter_name;

    public Chapter(int chapter_id, String chapter_desc, String chapter_name) {
        this.chapter_id = chapter_id;
        this.chapter_desc = chapter_desc;
        this.chapter_name = chapter_name;
    }

    public int getChapter_id() {
        return chapter_id;
    }

    public String getChapter_desc() {
        return chapter_desc;
    }

    public String getChapter_name() {
        return chapter_name;
    }
}
