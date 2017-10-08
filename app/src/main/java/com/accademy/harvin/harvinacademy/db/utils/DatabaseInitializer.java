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

package com.accademy.harvin.harvinacademy.db.utils;

import android.os.AsyncTask;
import android.util.Log;

import com.accademy.harvin.harvinacademy.db.AppDatabase;
import com.accademy.harvin.harvinacademy.model.Chapter;
import com.accademy.harvin.harvinacademy.model.File;
import com.accademy.harvin.harvinacademy.model.Subject;
import com.accademy.harvin.harvinacademy.model.Topic;
import com.accademy.harvin.harvinacademy.model.exam.Question;
import com.accademy.harvin.harvinacademy.model.exam.QuestionPaper;
import com.accademy.harvin.harvinacademy.model.user.Progress;

import java.util.List;

public class DatabaseInitializer {

    // Simulate a blocking operation delaying each Loan insertion with a delay:

    public static void populateAsyncChapter(final AppDatabase db, List<Chapter> chapter) {

        PopulateDbAsync task = new PopulateDbAsync(db);
        task.setChapters(chapter);
        task.execute();
    }

    public static void populateAsyncProgress(final AppDatabase db, List<Progress> progress) {

        PopulateDbAsync task = new PopulateDbAsync(db);
        task.setProgresses(progress);
        task.execute();
    }

    public static void populateAsyncTopic(final AppDatabase db, List<Topic> topic) {

        PopulateDbAsync task = new PopulateDbAsync(db);
        task.setTopics(topic);
        task.execute();
    }
    public static void populateAsyncSubject(final AppDatabase db, List<Subject> subject) {

        PopulateDbAsync task = new PopulateDbAsync(db);
        task.setSubjects(subject);
        task.execute();
    }
    public static void populateAsyncFile(final AppDatabase db ,List<File> file){
        PopulateDbAsync task= new PopulateDbAsync(db);
        task.setFiles(file);
        task.execute();
    }
    public static void populateAsyncQuestionPaper(final AppDatabase db ,QuestionPaper questionPaper){
        PopulateDbAsync task= new PopulateDbAsync(db);
        task.setQuestionPaper(questionPaper);
        task.execute();
    }
    public static void populateAsyncQuestion(final AppDatabase db ,List<Question> question){
        PopulateDbAsync task= new PopulateDbAsync(db);
        task.setQuestion(question);
        task.execute();
    }
    public static void populateSyncTopic(final AppDatabase db,List<Topic> topic){
        populateTopic(db,topic);
    }
    public static void populateSyncChapter(final AppDatabase db, List<Chapter> chapter) {

        populateChapter(db,chapter);
    }

    public static void populateSyncSubject(final AppDatabase db, List<Subject> subject) {
populateSubject(db,subject);
    }

    private static Topic addTopic(final AppDatabase db,final Topic topic){
        db.topicModel().insertTopic(topic);
        return topic;

    }
    private static Chapter addChapter(final AppDatabase db, final Chapter chapter){

        db.chapterModel().insertChapter(chapter);
        return chapter;

    }
    private static Subject addSubject(final AppDatabase db, final Subject subject){
        db.subjectModel().insertSubject(subject);
        return subject;
    }
    private static File addFile(final AppDatabase db,final File  file){
        db.fileModel().insertFile(file);
        return file;
    }
    private static QuestionPaper addQuestionPaper(final AppDatabase db,final QuestionPaper questionPaper){
        db.questionPaperModel().insertTest(questionPaper);
        return questionPaper;
    }
    private static Question addQuestion(final AppDatabase db, final Question question){
        db.questionModel().insertQuestion(question);
        return question;
    }
    private static Progress addProgress(final AppDatabase db, final Progress progress){
        db.progressModel().insertProgress(progress);
        return progress;
    }
    private static void populateQuestion(AppDatabase db,List<Question> question){
        for(Question ques:question){
            Log.d("roomQuestionSync","inserting "
                    +ques.getQuestion()

            );
            addQuestion(db,ques);
        }
    }
    private static void populateQuestionPaper(AppDatabase db,List<QuestionPaper> questionPaper){
        for(QuestionPaper quesp:questionPaper){
            Log.d("roomQuestionPaperSync","inserting "
                    +quesp.describeContents()

            );
            addQuestionPaper(db,quesp);
        }

    }
    private static void populateFile(AppDatabase db,List<File> file){
        for(File fil:file){
            Log.d("roomFileSync","inserting "
                    +fil.getFileName()+fil.chapterId

            );
            addFile(db,fil);
        }
    }
    private static void populateTopic(AppDatabase db,List<Topic> topic){
        for(Topic top:topic){
            Log.d("roomTopicSync","inserting "
                    +top.getTopicName()

            );
            addTopic(db,top);
        }

    }
    private static void populateChapter(AppDatabase db,List<Chapter> chapter){
        for(Chapter ch:chapter){
        Log.d("roomChapterSsync","inserting "
                +ch.getChapterName()

        );
        addChapter(db,ch);}

    }
    private static void populateSubject(AppDatabase db,List<Subject> subjects){
        for(Subject sub:subjects){
            Log.d("roomSubjectAsync","inserting "
                    +sub.getSubjectName()
                    +sub.getId()
            );
            addSubject(db,sub);
        }

    }
    private static void populateProgress(AppDatabase db,List<Progress> progress){
        for(Progress prog:progress){
            Log.d("roomProgAsync","inserting "
                    +prog.getChapter()
                    +prog.getId()
            );
            addProgress(db,prog);
        }
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase mDb;
        private  List<Chapter> chapters;
        private List<Subject> subjects;
        private List<Topic> topics;
        private List<File> files;
        private QuestionPaper questionPapers;
        private List<Question> question;
        private List<Progress> progresses;

        PopulateDbAsync(AppDatabase db) {
            mDb = db;
        }
        void setChapters(List<Chapter> chapters){
            this.chapters=chapters;
        }
        void setSubjects(List<Subject> subjects){
            this.subjects=subjects;
        }
        void setTopics(List<Topic> topics){this.topics=topics;}
        void setFiles(List<File> files){this.files=files;}
        void setQuestion(List<Question> question){this.question=question;}
        void setQuestionPaper(QuestionPaper questionPapers){this.questionPapers=questionPapers;}
        void setProgresses(List<Progress> progresses){this.progresses=progresses;}
        protected Void doInBackground(final Void... params) {
            if(chapters!=null)
                populateChapter(mDb,chapters);
            if(subjects!=null)
                populateSubject(mDb,subjects);
            if(topics!=null)
                populateTopic(mDb,topics);
            if(files!=null)
                populateFile(mDb,files);
            if(questionPapers!=null)
                addQuestionPaper(mDb,questionPapers);
            if(question!=null){
                populateQuestion(mDb,question);
            }
            if(progresses!=null)
                populateProgress(mDb,progresses);
            return null;
        }

    }
}
