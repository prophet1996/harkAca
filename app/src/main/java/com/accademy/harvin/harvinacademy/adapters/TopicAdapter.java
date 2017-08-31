package com.accademy.harvin.harvinacademy.adapters;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.accademy.harvin.harvinacademy.CourseActivity;
import com.accademy.harvin.harvinacademy.R;
import com.accademy.harvin.harvinacademy.model.Chapter;
import com.accademy.harvin.harvinacademy.model.DownloadedPdf;
import com.accademy.harvin.harvinacademy.model.Topic;
import com.accademy.harvin.harvinacademy.services.DownloadService;
import com.accademy.harvin.harvinacademy.utils.Internet;

import java.io.File;
import java.util.List;

import static com.accademy.harvin.harvinacademy.CourseActivity.MESSAGE_PROGRESS;

/**
 * Created by ishank on 19/7/17.
 */

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.ViewHolder> {

    private Context mContext;
    private Chapter mChapter;
    private boolean downloading_progress[];
    private Integer downloading_progress_value[];
    private boolean downloadable=false;
    private List<Topic> topics;

    public TopicAdapter( Chapter mChapter,Context mContext) {
        this.mContext = mContext;
        this.mChapter = mChapter;
        topics=mChapter.getTopicsAndReverse();
        downloading_progress= new boolean[topics.size()];
        downloading_progress_value= new Integer[topics.size()];
        checkPermission();

        if(Internet.isAvailable(mContext)){
            Log.d("internet","availabel");

        }

        else {

            Log.d("internet","not availabel");
        }


    }

    @Override
    public TopicAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_list,parent,false);
        registerReceiver();

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final TopicAdapter.ViewHolder holder, final int position) {
        if(downloading_progress[position])
            holder.progressBar.setVisibility(View.VISIBLE);


        else
            holder.progressBar.setVisibility(View.INVISIBLE);


        holder.topic_name.setText(topics.get(position).getTopicName());
        holder.topic_no.setText("1."+position);
        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.progressBar.setVisibility(View.VISIBLE);
                File pdfToOpen=new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS),topics.get(position).getTopicName()+".pdf");
                if(pdfToOpen.exists()){
                    Toast.makeText(mContext,"Already downloaded.",Toast.LENGTH_SHORT).show();
                    holder.progressBar.setVisibility(View.INVISIBLE);

                }
                else {
                    String id = topics.get(position).getFiles().get(0).getId();
                    Log.d("topicstest", id);
                    String name = topics.get(position).getTopicName();
                    Log.d("topicstest", name);
                    if (Internet.isAvailable(mContext)) {
                        startDownload(position, topics.get(position).getTopicName(), topics.get(position).getFiles().get(0).getId());
                    } else {
                        Toast.makeText(mContext, "No Connection", Toast.LENGTH_SHORT).show();

                    }
                    downloading_progress[position] = true;
                }
            }
        });
        holder.topic_name.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                File pdfToOpen=new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS),topics.get(position).getTopicName()+".pdf");
                    if(pdfToOpen.exists()){
                        String mime = MimeTypeMap.getSingleton().getMimeTypeFromExtension(".pdf");

                        Intent intent = new Intent();
                        intent.setAction(android.content.Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.fromFile(pdfToOpen), mime);
                       try{ mContext.startActivity(intent);}
                       catch (ActivityNotFoundException ae){ae.printStackTrace();}

                    }
                    else{
                        if(Internet.isAvailable(mContext)){
                            Log.d("internet","available");
                        holder.progressBar.setVisibility(View.VISIBLE);


                            startDownload(position,topics.get(position).getTopicName(),topics.get(position).getFiles().get(0).getId());

                        downloading_progress[position]=true;}
                        else {
                            Toast.makeText(mContext,"No Connection",Toast.LENGTH_SHORT).show();
                            Log.d("internet","not available");
                        }

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
        ImageButton download;
        ProgressBar progressBar;

        public ViewHolder(View itemView) {
            super(itemView);
            topic_no=(TextView)itemView.findViewById(R.id.topic_no);
            topic_name=(TextView)itemView.findViewById(R.id.topic_name);
            download=(ImageButton) itemView.findViewById(R.id.download_pdf);
            progressBar=(ProgressBar) itemView.findViewById(R.id.downloading_progress);



        }
    }
    private void startDownload(int topicPosition,String topicName,String topicId){
        checkPermission();
        if(!Internet.isAvailable(mContext)){
            return ;
        }
        if(downloadable){
        Intent intent = new Intent(mContext,DownloadService.class);
        intent.putExtra("topicno",topicPosition);
        intent.putExtra("topicname",topicName);
        intent.putExtra("topicid",topicId);
        mContext.startService(intent);
        Log.d("download","start d");}
        else{
            Toast.makeText(
                    mContext,"Sorry dont have the permission to acces device storage",Toast.LENGTH_SHORT).show();

        }


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
                int intentPosition=intent.getIntExtra("topicpos",-1);
                int progress=download.getProgress();
                downloading_progress_value[intentPosition]=progress;
                Log.d("intentprog",""+progress);
                if(progress==100)
                {
                    Log.d("send intent","progress 100 intent recieved");

                    downloading_progress[intentPosition]=false;


int itemchangepos=intent.getIntExtra("topicpos",-1);

                notifyItemChanged(itemchangepos);}

            }
        }
    };

    private boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED){
            downloadable=true;
            return true;

        } else {

            return false;
        }
    }



}
