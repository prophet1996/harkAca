package com.accademy.harvin.harvinacademy.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.accademy.harvin.harvinacademy.R;
import com.accademy.harvin.harvinacademy.adapters.TopicAdapter;
import com.accademy.harvin.harvinacademy.model.Chapter;
import com.accademy.harvin.harvinacademy.model.Topic;
import com.accademy.harvin.harvinacademy.model.user.Progress;

/**
 * Created by ishank on 19/7/17.
 */

public class ChapterFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private static TopicAdapter mTopicAdapter;
    private static Chapter mChapter;
    private static Context mContext;
    private static int subjectNo;


    public ChapterFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_chapter,container,false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.topic_recycler_view);
        final LinearLayoutManager linearLayoutManager=new LinearLayoutManager(container.getContext());
        linearLayoutManager.setReverseLayout(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        setRecyclerViewItemTouchListener();

        mRecyclerView.setAdapter(mTopicAdapter);


        return v;
    }
    public void setRecyclerViewItemTouchListener(){

        ItemTouchHelper.SimpleCallback itemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder1) {
                //2
                return false;
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                int position=viewHolder.getAdapterPosition();




            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                //3
                int position = viewHolder.getAdapterPosition();
                viewHolder.itemView.setVisibility(View.INVISIBLE);
                mRecyclerView.getAdapter().notifyItemRemoved(position);
            }
        };

        //4
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    private String getTopicState(String chapterId){
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(mContext);

        return pref.getString(chapterId,"-1");



    }


    public static ChapterFragment getInstance(Chapter mChapter,int subjectNo, Context mContext){
        ChapterFragment mChapterFragemnt= new ChapterFragment();
        mChapterFragemnt.mChapter=mChapter;
        mChapterFragemnt.mContext=mContext;
        mChapterFragemnt.subjectNo=subjectNo;

        mTopicAdapter=new TopicAdapter(mChapter,subjectNo,mContext);
        return  mChapterFragemnt;

    }
}
