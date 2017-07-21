package com.accademy.harvin.harvinacademy.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.accademy.harvin.harvinacademy.R;
import com.accademy.harvin.harvinacademy.adapters.TopicAdapter;
import com.accademy.harvin.harvinacademy.model.Chapter;
import com.accademy.harvin.harvinacademy.model.DownloadedPdf;
import com.accademy.harvin.harvinacademy.model.Topic;

import static com.accademy.harvin.harvinacademy.CourseActivity.MESSAGE_PROGRESS;

/**
 * Created by ishank on 19/7/17.
 */

public class ChapterFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private static TopicAdapter mTopicAdapter;
    private static Chapter mChapter;
    private static Context mContext;

    public ChapterFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_chapter,container,false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.topic_recycler_view);
        final LinearLayoutManager linearLayoutManager=new LinearLayoutManager(container.getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mTopicAdapter);


        return v;
    }


    public static ChapterFragment getInstance(Chapter mChapter , Context mContext){
        ChapterFragment mChapterFragemnt= new ChapterFragment();
        mChapterFragemnt.mChapter=mChapter;
        mChapterFragemnt.mContext=mContext;
        mTopicAdapter=new TopicAdapter(mChapter,mContext);
        return  mChapterFragemnt;

    }
}
