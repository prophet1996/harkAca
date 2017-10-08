package com.accademy.harvin.harvinacademy.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.accademy.harvin.harvinacademy.model.exam.Question;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

/**
 * Created by ishank on 5/10/17.
 */
@Dao
public interface QuestionDao {
    @Insert(onConflict = IGNORE)
    void insertQuestion(Question question);

    @Query("UPDATE Question SET isClicked=:isClicked WHERE id=:questionId")
    void updateQuestionClicked(boolean isClicked,String questionId);



}
