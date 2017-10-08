package com.accademy.harvin.harvinacademy.model;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;

/**
 * Created by ishank on 4/10/17.
 */

public class TopicWithFile {
    @Embedded
    public Topic topic;

    @Relation(entity = File.class,
            entityColumn = "topicId",
            parentColumn = "id"
        )
    public List<File> file;
}
