package com.accademy.harvin.harvinacademy.model;

/**
 * Created by ishank on 28/6/17.
 */

public class Topics {


    private int _id;
    private String topic_name;
    private String sub_name;
    private String description;
    private  String data_uri;

    public String getTopic_name() {
        return topic_name;
    }

    public String getSub_name() {
        return sub_name;
    }

    public Topics(int _id, String subject_name, String topic_name, String description, String data_uri) {
        this._id = _id;
        this.topic_name =topic_name;
        this.sub_name=subject_name;

        this.description = description;
        this.data_uri = data_uri;
    }

    public int get_id() {
        return _id;
    }

    public String getDescription() {
        return description;
    }

    public String getData_uri() {
        return data_uri;
    }
}
