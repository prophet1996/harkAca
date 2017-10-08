package com.accademy.harvin.harvinacademy.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.accademy.harvin.harvinacademy.model.File;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by ishank on 4/10/17.
 */
@Dao
public interface FileDao {
    @Insert(onConflict = IGNORE)
    void insertFile(File file);

 @Query("SELECT * FROM File")
 List<File> findAllFiles();

    @Query("SELECT * FROM File WHERE chapterId=:chapterId")
    List<File> findAllFilesWithChapterId(String chapterId);

}
