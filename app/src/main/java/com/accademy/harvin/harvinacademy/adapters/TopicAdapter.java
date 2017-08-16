package com.accademy.harvin.harvinacademy.adapters;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.accademy.harvin.harvinacademy.R;
import com.accademy.harvin.harvinacademy.model.Chapter;
import com.accademy.harvin.harvinacademy.model.DownloadedPdf;
import com.accademy.harvin.harvinacademy.services.DownloadService;

import java.io.File;

import static com.accademy.harvin.harvinacademy.CourseActivity.MESSAGE_PROGRESS;

/**
 * Created by ishank on 19/7/17.
 */

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.ViewHolder> {

    private Context mContext;
    private Chapter mChapter;
    private boolean downloading_progress[];

    public TopicAdapter( Chapter mChapter,Context mContext) {
        this.mContext = mContext;
        this.mChapter = mChapter;
        downloading_progress= new boolean[mChapter.getTopics().size()];

    }

    @Override
    public TopicAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_list,parent,false);
        registerReceiver();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final TopicAdapter.ViewHolder holder, final int position) {
        if(downloading_progress[position]==true)
            holder.progressBar.setVisibility(View.VISIBLE);
        else
            holder.progressBar.setVisibility(View.INVISIBLE);

        holder.topic_name.setText(mChapter.getTopics().get(position).getTopicName());
        holder.topic_no.setText("1."+position);
        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.progressBar.setVisibility(View.VISIBLE);

                    startDownload(position,mChapter.getTopics().get(position).getTopicName());
                downloading_progress[position]=true;

            }
        });
        holder.topic_name.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                File pdfToOpen=new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS),mChapter.getTopics().get(position).getTopicName()+".pdf");
                    if(pdfToOpen.exists()){
                        String mime = MimeTypeMap.getSingleton().getMimeTypeFromExtension(".pdf");

                        Intent intent = new Intent();
                        intent.setAction(android.content.Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.fromFile(pdfToOpen), mime);
                        mContext.startActivity(intent);

                    }
                    else{  holder.progressBar.setVisibility(View.VISIBLE);

                        startDownload(position,mChapter.getTopics().get(position).getTopicName());
                        downloading_progress[position]=true;

                    }
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
        ProgressBar progressBar;

        public ViewHolder(View itemView) {
            super(itemView);
            topic_no=(TextView)itemView.findViewById(R.id.topic_no);
            topic_name=(TextView)itemView.findViewById(R.id.topic_name);
            download=(Button)itemView.findViewById(R.id.download_pdf);
            progressBar=(ProgressBar) itemView.findViewById(R.id.downloading_progress);



        }
    }
    private void startDownload(int topicPosition,String topicName){

        Intent intent = new Intent(mContext,DownloadService.class);
        intent.putExtra("topic",topicPosition);
        intent.putExtra("topic",topicName);
        mContext.startService(intent);
        Log.d("download","start d");


    } private void registerReceiver(){

        LocalBroadcastManager bManager = LocalBroadcastManager.getInstance(mContext);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MESSAGE_PROGRESS);
        bManager.registerReceiver(broadcastReceiver, intentFilter);

    }
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent.getAction().equals(MESSAGE_PROGRESS)){

                DownloadedPdf download = intent.getParcelableExtra("download");
                if(download.getProgress()==100)
                {
                    Log.d("send intent","progress 100 intent recieved");

                    downloading_progress[intent.getIntExtra("topic",0)]=false;
                    notifyItemChanged(intent.getIntExtra("topic",0));
                }

            }
        }
    };




}
