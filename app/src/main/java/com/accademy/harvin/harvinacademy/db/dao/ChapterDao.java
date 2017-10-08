package com.accademy.harvin.harvinacademy.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;

import com.accademy.harvin.harvinacademy.model.Chapter;
import com.accademy.harvin.harvinacademy.model.ChapterWithTopic;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by ishank on 1/10/17.
 */
@Dao
public interface ChapterDao {
    @Query("SELECT * FROM Chapter")
    List<Chapter> findAllChapter();


    @Query("SELECT * FROM Chapter ")
    List<ChapterWithTopic> findChaptersWithTopic();

    @Query("SELECT * FROM Chapter WHERE id=:chapterId")
    Chapter findChapterWithChapterId(String chapterId);

    @Query("SELECT * FROM Chapter")
    List<Chapter> findAllChaptersSync();

    @Query("SELECT * FROM Chapter WHERE subjectId = :id")
    List<Chapter> findChaptersBySubjectId(String id);

    @Insert(onConflict = IGNORE)
    void insertChapter(Chapter chapter);

    @Update(onConflict = REPLACE)
    void updateChapter(Chapter chapter);

    @Query("DELETE FROM Chapter")
    void deleteAll();
}
