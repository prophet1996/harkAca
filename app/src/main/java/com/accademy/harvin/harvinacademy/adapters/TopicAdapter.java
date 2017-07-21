package com.accademy.harvin.harvinacademy.adapters;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.accademy.harvin.harvinacademy.CourseActivity;
import com.accademy.harvin.harvinacademy.R;
import com.accademy.harvin.harvinacademy.model.Chapter;
import com.accademy.harvin.harvinacademy.services.DownloadService;

/**
 * Created by ishank on 19/7/17.
 */

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.ViewHolder> {

    private Context mContext;
    private Chapter mChapter;

    public TopicAdapter( Chapter mChapter,Context mContext) {
        this.mContext = mContext;
        this.mChapter = mChapter;
    }

    @Override
    public TopicAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_list,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TopicAdapter.ViewHolder holder, int position) {
        holder.topic_name.setText(mChapter.getTopics().get(position).getTopicName());
        holder.topic_no.setText("1."+position);
        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    startDownload();

            }
        });

    }

    @Override
    public int getItemCount() {
        return mChapter.getTopics().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView topic_no;
        TextView topic_name;
        Button download;
        public ViewHolder(View itemView) {
            super(itemView);
            topic_no=(TextView)itemView.findViewById(R.id.topic_no);
            topic_name=(TextView)itemView.findViewById(R.id.topic_name);
            download=(Button)itemView.findViewById(R.id.download_pdf);



        }
    }
    private void startDownload(){

        Intent intent = new Intent(mContext,DownloadService.class);
        mContext.startService(intent);
        Log.d("download","start d");


    }


}
