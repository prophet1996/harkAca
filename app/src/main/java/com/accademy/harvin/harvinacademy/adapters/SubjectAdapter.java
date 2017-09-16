package com.accademy.harvin.harvinacademy.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.accademy.harvin.harvinacademy.CourseActivity;
import com.accademy.harvin.harvinacademy.R;
import com.accademy.harvin.harvinacademy.model.Chapter;
import com.accademy.harvin.harvinacademy.model.Subject;
import com.accademy.harvin.harvinacademy.model.user.Progress;
import com.accademy.harvin.harvinacademy.model.user.Progresses;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static com.accademy.harvin.harvinacademy.utils.Constants.SUBJECT_KEY;

/**
 * Created by ishank on 24/6/17.
 */

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder> {


     PopupMenu popup;

    private Subject mSubject;
    private Gson  GSON= new Gson();
    private Context context;
    private Progresses progresses;
    private HashMap<String,Progress> progMap;



    public SubjectAdapter(Subject mSubject,@NonNull Progresses progresses, Context context){
        Log.d("adapter", "done");
        this.progresses=progresses;
        this.mSubject=mSubject;
        this.context = context;
try{        progMap=new HashMap<>(progresses.getProgresses().size());}
catch(NullPointerException ne){
    ne.printStackTrace();
}

     //   mapProgressAndChapters();
      initmap();

    }




    @Override
    public SubjectAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.chapter_list,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final SubjectAdapter.ViewHolder holder, final int position) {

        Chapter ch=mSubject.getChapters().get(position);
        holder.id.setText("0"+(position+1));
        holder.desc.setText(ch.getChapterDescription());
        holder.chapter_name.setText(ch.getChapterName());
        holder.chapter.setText("Chapter");
        holder.chapter.setOnClickListener(gotchapteractivityListener(position));
        holder.chapter_name.setOnClickListener(gotchapteractivityListener(position));
        holder.id.setOnClickListener(gotchapteractivityListener(position));
        holder.desc.setOnClickListener(gotchapteractivityListener(position));
        String progStr;
         int prog=0;
        Progress progress = null;
        try{progress=progMap.get(ch.getId());
            progStr=progress.getCompleted();

            prog=Integer.parseInt(progStr);
        Log.d("getting progress",progStr);
        }
        catch (NullPointerException ne){
            ne.printStackTrace();
        }
if(holder.progressBar.isIndeterminate()){
            holder.progressBar.setIndeterminate(false);
            Log.d("getting progress","was indeterminate");
        }
        Handler progressBarHandler = new Handler();

        holder.progressBar.setMax(100);
        holder.progressBar.setProgress(prog);
        final int prog5=holder.progressBar.getProgress();
        Log.d("getting progress","get progress" + prog5);
        progressBarHandler .post(new Runnable() {

            public void run() {
                holder.progressBar.setProgress(prog5);
            }
        });


        holder.progPercentage.setText(prog+"%");
        if(progress!=null) {
            switch (progress.getStatus()) {
                case "revision":holder.badgeOrange.setVisibility(View.VISIBLE);
                    break;
                case "new":holder.badgeBlue.setVisibility(View.VISIBLE);
                    break;
                case "completed":holder.badgeGreen.setVisibility(View.VISIBLE);
                    break;

            }
        }







    }



    private View.OnClickListener gotchapteractivityListener(final int position) {

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences mPrefs=context.getSharedPreferences(SUBJECT_KEY,MODE_PRIVATE);
                SharedPreferences.Editor mPrefsEditor=mPrefs.edit();
                mPrefsEditor.putString(SUBJECT_KEY,GSON.toJson(mSubject.getChapters().get(position)));

                //mPrefsEditor.putString();
                mPrefsEditor.commit();
                Intent i = new Intent(context,CourseActivity.class);
                i.putExtra(SUBJECT_KEY,position);
                //i.putExtra(CHAPTER_KEY,chapter_list.getChapter_name());
                context.startActivity(i);
            }
        };
    }

    @Override
    public int getItemCount() {
        return mSubject.getChapters().size();
    }



     class ViewHolder extends RecyclerView.ViewHolder{
        public TextView id;
        TextView chapter_name;
        TextView desc;
       TextView chapter;//remains the same
         TextView badgeGreen;
         TextView badgeBlue;
         TextView badgeOrange;

        ProgressBar progressBar;
         TextView progPercentage;
        ViewHolder(View itemView) {
            super(itemView);
            id=(TextView)itemView.findViewById(R.id.chapter_no);
            chapter_name=(TextView)itemView.findViewById(R.id.chapter_title);
            progPercentage=(TextView)itemView.findViewById(R.id.progress_percentage);
            desc=(TextView)itemView.findViewById(R.id.chapter_description);
            chapter=(TextView)itemView.findViewById(R.id.chapter);
            badgeBlue=(TextView)itemView.findViewById(R.id.badge_blue);
            badgeOrange=(TextView)itemView.findViewById(R.id.badge_orange);
            badgeGreen=(TextView)itemView.findViewById(R.id.badge_green);

            progressBar=(ProgressBar) itemView.findViewById(R.id.progress_bar_chapter);
            progressBar.setIndeterminate(false);



        }

    }
    private void mapProgressAndChapters(){

    }
    private void initmap(){
       List<Progress> progList=progresses.getProgresses();
        for(Progress p:progList){
        progMap.put(p.getChapter(),p);
    Log.d("progress",progMap.toString());

        }}


}
