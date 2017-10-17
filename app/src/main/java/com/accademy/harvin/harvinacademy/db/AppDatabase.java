/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.accademy.harvin.harvinacademy.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.util.Log;

import com.accademy.harvin.harvinacademy.db.dao.ProgressDao;
import com.accademy.harvin.harvinacademy.db.typeconvertor.AnswersConverter;
import com.accademy.harvin.harvinacademy.db.typeconvertor.TopicIdOfProgressConvetor;
import com.accademy.harvin.harvinacademy.model.Chapter;
import com.accademy.harvin.harvinacademy.model.File;
import com.accademy.harvin.harvinacademy.model.Topic;
import com.accademy.harvin.harvinacademy.model.Subject;
import com.accademy.harvin.harvinacademy.db.dao.ChapterDao;
import com.accademy.harvin.harvinacademy.db.dao.FileDao;
import com.accademy.harvin.harvinacademy.db.dao.QuestionDao;
import com.accademy.harvin.harvinacademy.db.dao.SubjectDao;
import com.accademy.harvin.harvinacademy.db.dao.QuestionPaperDao;
import com.accademy.harvin.harvinacademy.db.dao.TopicDao;
import com.accademy.harvin.harvinacademy.model.exam.Question;
import com.accademy.harvin.harvinacademy.model.exam.QuestionPaper;
import com.accademy.harvin.harvinacademy.model.user.Progress;

@TypeConverters({TopicIdOfProgressConvetor.class})
@Database(entities = {Chapter.class,Subject.class,Topic.class,File.class, QuestionPaper.class, Question.class, Progress.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;


    public abstract ChapterDao chapterModel();
    public abstract SubjectDao subjectModel();
    public abstract TopicDao topicModel();
    public abstract FileDao fileModel();
    public abstract QuestionPaperDao questionPaperModel();
    public abstract QuestionDao questionModel();
    public abstract ProgressDao progressModel();

    public static AppDatabase getInMemoryDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.inMemoryDatabaseBuilder(context.getApplicationContext(), AppDatabase.class)
                    // To simplify the codelab, allow queries on the main thread.
                    // Don't do this on a real app! See PersistenceBasicSample for an example.
                    .allowMainThreadQueries()
                    .build();
        }
        Log.d("room","room");
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}