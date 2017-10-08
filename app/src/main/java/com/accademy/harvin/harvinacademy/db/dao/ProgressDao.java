package com.accademy.harvin.harvinacademy.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.accademy.harvin.harvinacademy.model.user.Progress;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by ishank on 7/10/17.
 */
@Dao
public interface ProgressDao {
    @Insert(onConflict = IGNORE)
    void insertProgress(Progress progress);

    @Update(onConflict = REPLACE)
    void updateProgress(Progress progeress);

    @Query("DELETE FROM progress")
    void deleteAll();

    @Query("SELECT * FROM progress WHERE chapter=:chapterId")
    Progress getProgressWithChapterId(String chapterId);
}
