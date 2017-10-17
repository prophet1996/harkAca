package com.accademy.harvin.harvinacademy.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.accademy.harvin.harvinacademy.CourseActivity;
import com.accademy.harvin.harvinacademy.R;
import com.accademy.harvin.harvinacademy.db.AppDatabase;
import com.accademy.harvin.harvinacademy.model.Chapter;
import com.accademy.harvin.harvinacademy.model.SubjectWithChapter;
import com.accademy.harvin.harvinacademy.model.user.Progress;
import com.accademy.harvin.harvinacademy.model.user.Progresses;

import java.util.HashMap;
import java.util.List;

import static com.accademy.harvin.harvinacademy.utils.Constants.CHAPTER_KEY;

/**
 * Created by ishank on 24/6/17.
 */

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder> {


    private static String TAG="SubjectAdapter.class";
    private Context context;
    private Progresses progresses;
    private HashMap<String,Progress> progMap;
    private AppDatabase appDatabase;
    private List<SubjectWithChapter> chaptersWithSubject;
    private String subjectId;
    private int subjectPosition;




    public SubjectAdapter( @NonNull Progresses progresses, String subjectId,int subjectPosition, Context context){

        Log.d(TAG, "constructor");
        Log.d("adapter", "done");
        this.progresses=progresses;
        this.subjectId=subjectId;
        Log.d("room subjectid=",""+subjectId);
        this.context = context;

        this.subjectPosition=subjectPosition;
        appDatabase= AppDatabase.getInMemoryDatabase(context.getApplicationContext());

        try{        progMap=new HashMap<>(progresses.getProgresses().size());
        Log.d("progmap","init");

        }
catch(NullPointerException ne){
    ne.printStackTrace();
}

     //   mapProgressAndChapters();
      //initmap();
        fetchChaptersFromDb();

    }


    private void fetchChaptersFromDb() {
        Log.d(TAG, "fetchChapterFromDb");

        chaptersWithSubject=appDatabase.subjectModel().findAllSubjectWithChapterSync();
        Log.d("room","ch= now");
        try{
            for(int i=0;i<chaptersWithSubject.size();i++)
                for(int j=0;j<chaptersWithSubject.get(i).chapters.size();j++){
                    Log.d("room result","chapter "+Integer.toString(i)
                            +chaptersWithSubject.get(i).subject.getSubjectName()
                            +chaptersWithSubject.get(i).chapters.get(j).getChapterName());

                }
            }
        catch (Exception ne){
            Log.d("room result error",ne.getLocalizedMessage().toString());
        }
    }

    @Override
    public SubjectAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.chapter_list,parent,false);
        Log.d(TAG, "onCreateViewHolder");
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final SubjectAdapter.ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder");
        Chapter ch=chaptersWithSubject.get(subjectPosition).chapters.get(position);
        holder.id.setText("0"+(position+1));
        holder.desc.setText(ch.getChapterDescription());
        holder.chapter_name.setText(ch.getChapterName());
        holder.chapter.setText("Chapter");
        holder.relativeLayout.setOnClickListener(gotchapteractivityListener(position));
        Progress currProg=appDatabase.progressModel().getProgressWithChapterId(ch.getId());
        if(currProg!=null){
            holder.progressBar.setProgress(Integer.parseInt(currProg.getCompleted()));

            holder.progPercentage.setText(currProg.getCompleted()+"%");

        }
        else{
            holder.progressBar.setProgress(0);

            holder.progPercentage.setText(0+"%");
        }
if(holder.progressBar.isIndeterminate()){
            holder.progressBar.setIndeterminate(false);
            Log.d("getting progress","was indeterminate");
        }






        if(currProg!=null) {
            switch (currProg.getStatus()) {
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
                Intent i = new Intent(context,CourseActivity.class);
                String chId=chaptersWithSubject.get(subjectPosition).chapters.get(position).getId();
                Log.d("ishank room",""+chId);
                Log.d(TAG, "Clicked on one of the chapters moving to new activity");

                Progress currProg=appDatabase.progressModel().getProgressWithChapterId(chId);

                i.putExtra(CHAPTER_KEY,chId);
                i.putExtra(CHAPTER_KEY+"pos",position);

                try{
                i.putExtra(CHAPTER_KEY+"prog",currProg.getCompleted());}
                catch (NullPointerException ne){
                    ne.printStackTrace();
                }
                context.startActivity(i);
            }
        };
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount");
        return chaptersWithSubject.get(subjectPosition).chapters.size();
    }



     class ViewHolder extends RecyclerView.ViewHolder{
        public TextView id;
        TextView chapter_name;
        TextView desc;
       TextView chapter;//remains the same
         TextView badgeGreen;
         TextView badgeBlue;
         TextView badgeOrange;
        RelativeLayout relativeLayout;
        ProgressBar progressBar;
         TextView progPercentage;
        ViewHolder(View itemView) {
            super(itemView);
            id=itemView.findViewById(R.id.chapter_no);
            chapter_name=itemView.findViewById(R.id.chapter_title);
            progPercentage=itemView.findViewById(R.id.progress_percentage);
            desc=itemView.findViewById(R.id.exam_type);
            chapter=itemView.findViewById(R.id.chapter);
            badgeBlue=itemView.findViewById(R.id.badge_blue);
            badgeOrange=itemView.findViewById(R.id.badge_orange);
            badgeGreen=itemView.findViewById(R.id.badge_green);
            relativeLayout=itemView.findViewById(R.id.chapter_card_layout);
            progressBar=itemView.findViewById(R.id.progress_bar_chapter);
            progressBar.setIndeterminate(false);
            Log.d(TAG, "Viewholder constructor");


        }

    }
}
