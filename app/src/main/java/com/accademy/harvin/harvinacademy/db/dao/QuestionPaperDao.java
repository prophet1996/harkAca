package com.accademy.harvin.harvinacademy.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.accademy.harvin.harvinacademy.model.exam.QuestionPaper;
import com.accademy.harvin.harvinacademy.model.exam.QuestionPaperWithQuestion;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by ishank on 5/10/17.
 */
@Dao
public interface QuestionPaperDao {

    @Query("SELECT * FROM QuestionPaper")
    LiveData<List<QuestionPaperWithQuestion>> findAllQuesitonPapterWithQuestionSync();
    @Insert(onConflict = IGNORE)
    void insertTest(QuestionPaper questionPaper);
    @Update(onConflict = REPLACE)
    void updateTest(QuestionPaper questionPaper);
    @Query("DELETE FROM QuestionPaper")
    void deleteAll();

}
