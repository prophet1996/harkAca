package com.accademy.harvin.harvinacademy.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.text.TextUtilsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.accademy.harvin.harvinacademy.R;
import com.accademy.harvin.harvinacademy.adapters.TopicAdapter;
import com.accademy.harvin.harvinacademy.db.AppDatabase;
import com.accademy.harvin.harvinacademy.model.Chapter;
import com.accademy.harvin.harvinacademy.model.ChapterWithTopic;
import com.accademy.harvin.harvinacademy.model.Topic;
import com.accademy.harvin.harvinacademy.model.TopicWithFile;
import com.accademy.harvin.harvinacademy.model.user.Progress;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by ishank on 19/7/17.
 */

public class ChapterFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private static TopicAdapter mTopicAdapter;
    private static int subjectNo;
   // private AppDatabase db;
    private List<ChapterWithTopic> chapterWithTopic;
    private List<TopicWithFile> topicWithFiles;
    private List<Topic> topics;
    private String chapterId;
    private int chapterPosition;
    private ProgressBar chapterProgressBar;
        private TextView chapterTextView;
    private int chapterProgress=-1;



    public ChapterFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_chapter,container,false);
        mRecyclerView =  v.findViewById(R.id.topic_recycler_view);
        chapterProgressBar=v.findViewById(R.id.chapter_progress_progressbar);
        chapterTextView=v.findViewById(R.id.chapter_progress_textview);


        final LinearLayoutManager linearLayoutManager=new LinearLayoutManager(container.getContext(),LinearLayoutManager.VERTICAL,true);
        linearLayoutManager.setReverseLayout(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
      //  db= AppDatabase.getInMemoryDatabase(container.getContext());

        setRecyclerViewItemTouchListener();
        initTopics();
        //mTopicAdapter=new TopicAdapter(topics,chapterPosition,chapterId,container.getContext());

//        mTopicAdapter.setProgressCheckClickedListener(new TopicAdapter.ProgressCheckClickedListener() {
//            @Override
//            public void onProgressClicked(int position) {
//
//              //  chapterProgressBar.setProgress(chapterProgressBar.getProgress()+(100/topicWithFiles.size()));
//               // chapterTextView.setText(chapterProgressBar.getProgress()+"");
//            }
//
//            @Override
//            public void onProgressUnclicked(int position) {
//               // chapterProgressBar.setProgress(chapterProgressBar.getProgress()-(100/topicWithFiles.size()));
//               // chapterTextView.setText(chapterProgressBar.getProgress()+"");
//            }
//        });
        mRecyclerView.setAdapter(mTopicAdapter);

        return v;
    }
    private void initTopics(){
//chapterWithTopic=db.chapterModel().findChaptersWithTopic();
       // Log.d("chap prob",""+chapterWithTopic.get(chapterPosition).topics.size());
      //  topicWithFiles=db.topicModel().getTopicWithChapter(chapterId);

//        Log.d("chapterfrag",chapterWithTopic.topics.get(0).getTopicName()+"");
//        Log.d("chapterfrag",chapterWithTopic.topics.size()+"");
//        Log.d("chapterfrag",topicWithFiles.size()+"");
//        Log.d("chapterfrag",topicWithFiles.get(0).topic.getTopicName()+"");

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



    public static ChapterFragment getInstance(String chapterId,List<Topic> topics ,int chapterposition,int chapterProgress){
        ChapterFragment mChapterFragemnt= new ChapterFragment();
        mChapterFragemnt.chapterPosition=chapterposition;
        mChapterFragemnt.topics=topics;
        mChapterFragemnt.chapterId=chapterId;
        mChapterFragemnt.chapterProgress=chapterProgress;
        Log.d("getInstance",""+topics.size());

        return  mChapterFragemnt;

    }

    private void saveProgressToDb(){
       // chapterProgressBar.getProgress

    }
    @Override
    public void onPause() {
        super.onPause();
        saveProgressToDb();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
