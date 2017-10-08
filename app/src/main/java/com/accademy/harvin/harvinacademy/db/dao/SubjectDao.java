package com.accademy.harvin.harvinacademy.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;

import com.accademy.harvin.harvinacademy.model.SubjectWithChapter;
import com.accademy.harvin.harvinacademy.model.Subject;

import java.util.List;

import io.reactivex.Flowable;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by ishank on 2/10/17.
 */
@Dao
public interface SubjectDao {
    @Query("SELECT * FROM Subject")
    List<Subject> findAllChapter();


    @Query("SELECT * FROM Subject")
    public List<Subject> findAllChaptersSync();


    @Query("SELECT * FROM Subject")
    List<SubjectWithChapter> findAllSubjectWithChapterSync();

    @Query("SELECT * FROM Subject")
    LiveData<List<SubjectWithChapter>> findAllSubjectWithChapterLiveSync();

    @Query("SELECT * FROM Subject WHERE id=:subjectId")
    List<Subject> findChaptersBySubjectId(String subjectId);

    @Insert(onConflict = IGNORE)
    void insertSubject(Subject subject);

    @Update(onConflict = REPLACE)
    void updateSubject(Subject subject);

    @Query("DELETE FROM Subject")
    void deleteAll();
}
