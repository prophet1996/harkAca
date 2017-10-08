package com.accademy.harvin.harvinacademy.model.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.accademy.harvin.harvinacademy.db.AppDatabase;
import com.accademy.harvin.harvinacademy.model.exam.QuestionPaperWithQuestion;

import java.util.List;

/**
 * Created by ishank on 5/10/17.
 */

public class QuestionPaperWithQuestionViewModel extends AndroidViewModel {

    public LiveData<List<QuestionPaperWithQuestion>> questionPaperWithQuestionViewModel;
    private AppDatabase db;
    public QuestionPaperWithQuestionViewModel(Application application) {
        super(application);
        createdb();
    }

    private void createdb() {
    db=AppDatabase.getInMemoryDatabase(this.getApplication());

    }
}
