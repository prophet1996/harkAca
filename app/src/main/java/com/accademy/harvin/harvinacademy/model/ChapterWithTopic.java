package com.accademy.harvin.harvinacademy.model;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;

/**
 * Created by ishank on 4/10/17.
 */

public class ChapterWithTopic {
    @Embedded
    public Chapter chapter;

    @Relation(entity = Topic.class,
            parentColumn = "id",
            entityColumn = "chapterId")
    public List<Topic> topics;

}
