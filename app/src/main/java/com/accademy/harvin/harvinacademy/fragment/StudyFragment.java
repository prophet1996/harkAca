package com.accademy.harvin.harvinacademy.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.accademy.harvin.harvinacademy.R;
import com.accademy.harvin.harvinacademy.adapters.SubjectAdapter;
import com.accademy.harvin.harvinacademy.model.Subject;


/**
 * A simple {@link Fragment} subclass.
 */
public class StudyFragment extends Fragment {


    private static Subject mSubject;
    private static Context mContext;


    //recyclerview objects
    private RecyclerView recyclerView;
    private static RecyclerView.Adapter mSubjectAdapter;
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
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        final LinearLayoutManager linearLayoutManager=new LinearLayoutManager(container.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setPadding(10,10,10,10);
        recyclerView.setAdapter(mSubjectAdapter);
        return v;
    }


    public static StudyFragment getInstance(Subject mSubject,Context mContext){
        StudyFragment mStudyFragment= new StudyFragment();
        Log.d("done2",""+mSubject.getSubjectName());
        mSubjectAdapter = new SubjectAdapter(mSubject, mContext);
        mStudyFragment.mSubject=mSubject;
        mStudyFragment.mContext=mContext;
        return mStudyFragment;
    }
}