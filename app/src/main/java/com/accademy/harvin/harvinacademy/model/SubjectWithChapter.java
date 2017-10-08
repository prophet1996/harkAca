package com.accademy.harvin.harvinacademy.model;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;

/**
 * Created by ishank on 3/10/17.
 */

public class SubjectWithChapter {
    @Embedded
    public Subject subject;

    @Relation(parentColumn = "id",
            entityColumn = "subjectId",
            entity = Chapter.class)
    public List<Chapter> chapters;
}
