package com.accademy.harvin.harvinacademy.db.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.accademy.harvin.harvinacademy.model.exam.QuestionPaper;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by ishank on 5/10/17.
 */
@Dao
public interface QuestionPaperDao {

    @Insert(onConflict = IGNORE)
    void insertTest(QuestionPaper questionPaper);
    @Update(onConflict = REPLACE)
    void updateTest(QuestionPaper questionPaper);
    @Query("DELETE FROM QuestionPaper")
    void deleteAll();

}
