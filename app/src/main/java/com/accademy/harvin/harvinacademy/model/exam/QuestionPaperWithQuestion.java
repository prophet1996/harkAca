package com.accademy.harvin.harvinacademy.model.exam;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;

/**
 * Created by ishank on 5/10/17.
 */

public class QuestionPaperWithQuestion {
    @Embedded
    public QuestionPaper questionPaper;

    @Relation(entity =Question.class,
                parentColumn = "id",
                entityColumn = "questionPaperId"

    )
    public List<Question> question;

}
