package com.accademy.harvin.harvinacademy.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.accademy.harvin.harvinacademy.model.Topic;
import com.accademy.harvin.harvinacademy.model.TopicWithFile;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by ishank on 4/10/17.
 */
@Dao
public interface TopicDao {
    @Query("SELECT * FROM Topic")
    List<Topic> getAllTopics();

    @Query("SELECT * FROM Topic")
    List<TopicWithFile> getTopicWithFile();

    @Query("SELECT * FROM Topic WHERE id=:id")
    List<TopicWithFile> getTopicWithFileId(String id);

    @Insert(onConflict = IGNORE)
    void insertTopic(Topic topic);


    @Query("SELECT * FROM Topic WHERE chapterId=:chapterId ORDER BY topicName DESC")
    List<Topic> getTopicWithChapterId(String chapterId);


    @Update(onConflict = REPLACE)
    void updateTopic(Topic topic);

    @Query("DELETE FROM Topic")
    void deleteAll();



}
