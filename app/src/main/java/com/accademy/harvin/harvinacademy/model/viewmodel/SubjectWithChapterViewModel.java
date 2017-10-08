package com.accademy.harvin.harvinacademy.model.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.accademy.harvin.harvinacademy.db.AppDatabase;
import com.accademy.harvin.harvinacademy.model.Subject;
import com.accademy.harvin.harvinacademy.model.SubjectWithChapter;

import java.util.List;

/**
 * Created by ishank on 5/10/17.
 */

public class SubjectWithChapterViewModel extends AndroidViewModel {
    public LiveData<List<SubjectWithChapter>> subjectWithChapterViewModel;
    private AppDatabase appDatabase;
    public SubjectWithChapterViewModel(Application application) {
        super(application);
        createdb();
        subjectWithChapterViewModel=appDatabase.subjectModel().findAllSubjectWithChapterLiveSync();
    }

    private void createdb() {
        appDatabase=AppDatabase.getInMemoryDatabase(this.getApplication());
    }
}
