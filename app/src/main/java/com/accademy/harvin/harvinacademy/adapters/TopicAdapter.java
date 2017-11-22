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
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.accademy.harvin.harvinacademy.R;
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
    private String TAG="TopicAdapter";
    private Context mContext;

    private boolean downloading_progress[];
    private Integer downloading_progress_value[];
    private List<String> progressForTopics;
    private boolean downloadable=false;
    private List<Topic> topics;
    private List<com.accademy.harvin.harvinacademy.model.File> files;
    private int chapterPosition;
    private ProgressCheckClickedListener progressCheckClickedListener;
    private TopicDownloadListener topicDownloadListener;

    public interface TopicDownloadListener{
        void downloadCompleted();
    }
    public interface ProgressCheckClickedListener{
        void onProgressClicked(int position,String topicId);
        void onProgressUnclicked(int position,String topicId);
    }
    public void setDownloadCompletedListener(TopicDownloadListener topicDownloadListener){this.topicDownloadListener=topicDownloadListener;}
    public void setProgressCheckClickedListener(ProgressCheckClickedListener progressCheckClickedListener){this.progressCheckClickedListener=progressCheckClickedListener;}
    public void removeProgressCheckClickedListener(){
        if(this.progressCheckClickedListener!=null)
            this.progressCheckClickedListener=null;
    }
    public TopicAdapter(List<Topic> topics, List<com.accademy.harvin.harvinacademy.model.File> files, int chapterPosition, String chapterId, Context mContext) {
        this.mContext = mContext;
        this.chapterPosition=chapterPosition;
        this.topics=topics;
        Log.d("topics prob",""+this.topics.size());
        this.files=files;
        downloading_progress= new boolean[this.topics.size()];
        downloading_progress_value= new Integer[this.topics.size()];
        checkPermission();

        if(Internet.isAvailable(mContext)){Log.d("internet","availabel");}

        else {Log.d("internet","not availabel");}

    }
public void setTopics(List<Topic> topics){
    this.topics=topics;
    Log.d("topics prob2",""+this.topics.size());


    downloading_progress= new boolean[this.topics.size()];
    downloading_progress_value= new Integer[this.topics.size()];
    checkPermission();

    if(Internet.isAvailable(mContext)){Log.d("internet","availabel");}
    else {Log.d("internet","not availabel");}
}
public void setProgressForTopics(List<String> progressForTopics){
    this.progressForTopics=progressForTopics;
}

    @Override
    public TopicAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_list,parent,false);
        registerReceiver();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final TopicAdapter.ViewHolder holder, final int position) {
        Log.d("files",files.get(position).getFileName());
        if(downloading_progress[position])
            holder.progressBar.setVisibility(View.VISIBLE);
        else
            holder.progressBar.setVisibility(View.INVISIBLE);
        holder.topic_name.setText(topics.get(position).getTopicName());
        int currPos=topics.size()-position;
        int currCh=chapterPosition+1;
        holder.topic_no.setText(""+currCh+"."+currPos);
        if(progressForTopics!=null)
            if(progressForTopics.contains(topics.get(position).getId())){
                holder.checkBox.setChecked(true);
                progressCheckClickedListener.onProgressClicked(position,topics.get(position).getId());
        }

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
                    String id =files.get(0).getId();

                    Log.d("topicstest", id);
                    if (Internet.isAvailable(mContext)) {
                        startDownload(position, topics.get(position).getTopicName(), id);
                    } else {
                        Toast.makeText(mContext, "No Connection", Toast.LENGTH_SHORT).show();

                    }
                    downloading_progress[position] = true;
                }
            }
        });
        holder.topicListLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                File pdfToOpen=new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS),topics.get(position).getTopicName()+".pdf");
                    if(pdfToOpen.exists()){
                        String mime = MimeTypeMap.getSingleton().getMimeTypeFromExtension(".pdf");
//                        Log.d(TAG,mime);
                        Uri path= FileProvider.getUriForFile(mContext,"com.accademy.harvin.harvinacademy",pdfToOpen);
                        Intent intent = new Intent();
                        intent.setAction(android.content.Intent.ACTION_VIEW);
                        intent.setDataAndType(path, mime);
                        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                       try{ mContext.startActivity(intent);}
                       catch (ActivityNotFoundException ae){ae.printStackTrace();}

                    }
                    else{
                        if(Internet.isAvailable(mContext)){
                            Log.d("internet","available");
                        holder.progressBar.setVisibility(View.VISIBLE);


                            startDownload(position,topics.get(position).getTopicName(),files.get(0).getId());

                        downloading_progress[position]=true;}
                        else {
                            Toast.makeText(mContext,"No Connection",Toast.LENGTH_SHORT).show();
                            Log.d("internet","not available");
                        }

                    }
            }
        });
holder.checkBox.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        if(holder.checkBox.isChecked()){
            progressCheckClickedListener.onProgressClicked(position,topics.get(position).getId());
        }
        else{
            progressCheckClickedListener.onProgressUnclicked(position,topics.get(position).getId());
        }

    }
});


    }

    @Override
    public int getItemCount() {
        return topics.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView topic_no;
        TextView topic_name;
        ImageButton download;
        ProgressBar progressBar;
        RelativeLayout topicListLayout;
        CheckBox checkBox;


        public ViewHolder(View itemView) {
            super(itemView);
            topic_no=itemView.findViewById(R.id.topic_no);
            topic_name=itemView.findViewById(R.id.topic_name);
            download=itemView.findViewById(R.id.download_pdf);
            progressBar=itemView.findViewById(R.id.downloading_progress);
           checkBox=itemView.findViewById(R.id.mark_as_done);
            topicListLayout=itemView.findViewById(R.id.topic_list_layout);


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


    }



    private void registerReceiver(){

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

                topicDownloadListener.downloadCompleted();
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
