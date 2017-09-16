package com.accademy.harvin.harvinacademy.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.accademy.harvin.harvinacademy.R;
import com.accademy.harvin.harvinacademy.adapters.SubjectAdapter;
import com.accademy.harvin.harvinacademy.model.Subject;
import com.accademy.harvin.harvinacademy.model.user.Progress;
import com.accademy.harvin.harvinacademy.model.user.Progresses;


/**
 * A simple {@link Fragment} subclass.
 */
public class StudyFragment extends Fragment {

    private  Subject mSubject;
    private  Context mContext;
    private  Progresses progresses;


    public StudyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("done", "done");
        View v = inflater.inflate(R.layout.fragment_study, container, false);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        final LinearLayoutManager linearLayoutManager=new LinearLayoutManager(container.getContext());
        if(progresses==null)
            Log.d("getting progress","null in oncreatefrag");
        RecyclerView.Adapter mSubjectAdapter = new SubjectAdapter(mSubject, progresses, mContext);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setPadding(10,10,10,10);
        recyclerView.setAdapter(mSubjectAdapter);
        ((SimpleItemAnimator)recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        return v;
    }
public void setProgress(Progresses progresses){
    this.progresses=progresses;
}

    public static StudyFragment getInstance(Subject mSubject, Context mContext){
        StudyFragment mStudyFragment= new StudyFragment();
        Log.d("done2",""+mSubject.getSubjectName());
        mStudyFragment.mSubject=mSubject;
        mStudyFragment.mContext=mContext;

        return mStudyFragment;
    }
}