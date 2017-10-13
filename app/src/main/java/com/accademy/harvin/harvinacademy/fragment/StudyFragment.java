package com.accademy.harvin.harvinacademy.fragment;


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
import com.accademy.harvin.harvinacademy.model.user.Progresses;


/**
 * A simple {@link Fragment} subclass.
 */
public class StudyFragment extends Fragment {


    private  Progresses progresses;
    private int subjectPosition;
    private String subjectId;
    private RecyclerView.Adapter mSubjectAdapter;



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
        RecyclerView recyclerView = v.findViewById(R.id.recycler_view);
        final LinearLayoutManager linearLayoutManager=new LinearLayoutManager(container.getContext());
        if(progresses==null)
            Log.d("getting progress","null in oncreatefrag");
         mSubjectAdapter = new SubjectAdapter(progresses, subjectId,subjectPosition, getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setPadding(10,10,10,10);
        recyclerView.setAdapter(mSubjectAdapter);
        ((SimpleItemAnimator)recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        return v;
    }

public void setProgress(Progresses progresses){
    this.progresses=progresses;
}

    public static StudyFragment getInstance(String subjectId,int subjectPosition){
        StudyFragment mStudyFragment= new StudyFragment();

        mStudyFragment.subjectId=subjectId;
        mStudyFragment.subjectPosition=subjectPosition;

        return mStudyFragment;
    }
}