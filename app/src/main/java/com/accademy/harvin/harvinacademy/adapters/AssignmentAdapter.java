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
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.accademy.harvin.harvinacademy.R;
import com.accademy.harvin.harvinacademy.model.Assignment;
import com.accademy.harvin.harvinacademy.model.DownloadedPdf;
import com.accademy.harvin.harvinacademy.services.DownloadService;
import com.accademy.harvin.harvinacademy.utils.Internet;

import java.io.File;
import java.util.List;

import static com.accademy.harvin.harvinacademy.CourseActivity.MESSAGE_PROGRESS;

/**
 * Created by ishank on 23/10/17.
 */

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.ViewHolder> {
    private List<Assignment> assignmentList;
    public static final String MESSAGE_PROGRESS = "message_progress";
    private Context context;
    private boolean downloadable=false;
    private boolean downloading_progress[];
    private Integer downloading_progress_value[];

    public AssignmentAdapter(){}
    public AssignmentAdapter(List<Assignment> assignmentList,Context context){
        this.assignmentList=assignmentList;
        this.context=context;
        downloading_progress= new boolean[assignmentList.size()];
        downloading_progress_value= new Integer[assignmentList.size()];
        checkPermission();

        if(Internet.isAvailable(context)){
            Log.d("internet","availabel");

        }

        else {

            Log.d("internet","not availabel");
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.assignment_list,parent,false);
        registerReceiver();
        return new ViewHolder(v);
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

        private void registerReceiver(){

            LocalBroadcastManager bManager = LocalBroadcastManager.getInstance(context);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(MESSAGE_PROGRESS);
            bManager.registerReceiver(broadcastReceiver, intentFilter);

        }

    private void startDownload(int assignmentPosition,String assignmentName,String assignmentId){
        checkPermission();
        if(!Internet.isAvailable(context)){
            return ;
        }
        if(downloadable){
            Intent intent = new Intent(context,DownloadService.class);
            intent.putExtra("topicno",assignmentPosition);
            intent.putExtra("topicname",assignmentName);
            intent.putExtra("topicid",assignmentId);
            intent.putExtra("assignment",true);
            context.startService(intent);
            Log.d("download","start d");}
        else{
            Toast.makeText(
                    context,"Sorry dont have the permission to acces device storage",Toast.LENGTH_SHORT).show();

        }


    }



    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        int currPos=assignmentList.size()-position;

        holder.assignmentNumber.setText("0"+currPos);
        holder.assignmentName.setText(assignmentList.get(position).getAssignmentName());
        if(downloading_progress[position])
            holder.downloadingProgressBar.setVisibility(View.VISIBLE);
        else
            holder.downloadingProgressBar.setVisibility(View.INVISIBLE);
        holder.downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.downloadingProgressBar.setVisibility(View.VISIBLE);
                File pdfToOpen=new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS),assignmentList.get(position).getAssignmentName()+".pdf");
                if(pdfToOpen.exists()){
                    Toast.makeText(context,"Already downloaded.",Toast.LENGTH_SHORT).show();
                    holder.downloadingProgressBar.setVisibility(View.INVISIBLE);
                }
                else {
                    String id =assignmentList.get(position).getId();

                    Log.d("topicstest", id);
                    if (Internet.isAvailable(context)) {
                        startDownload(position, assignmentList.get(position).getAssignmentName(), id);
                    } else {
                        Toast.makeText(context, "No Connection", Toast.LENGTH_SHORT).show();

                    }
                    downloading_progress[position] = true;
                }
            }
        });
        holder.assignmentListLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                File pdfToOpen=new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS),assignmentList.get(position).getAssignmentName()+".pdf");
                if(pdfToOpen.exists()){
                    String mime = MimeTypeMap.getSingleton().getMimeTypeFromExtension(".pdf");
                    Uri path= FileProvider.getUriForFile(context,"com.accademy.harvin.harvinacademy",pdfToOpen);
                    Intent intent = new Intent();
                    intent.setAction(android.content.Intent.ACTION_VIEW);
                    intent.setDataAndType(path, mime);
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                    try{ context.startActivity(intent);}
                    catch (ActivityNotFoundException ae){ae.printStackTrace();}

                }
                else{
                    if(Internet.isAvailable(context)){
                        Log.d("internet","available");
                        holder.downloadingProgressBar.setVisibility(View.VISIBLE);


                        startDownload(position,assignmentList.get(position).getAssignmentName(),assignmentList  .get(position).getId());

                        downloading_progress[position]=true;}
                    else {
                        Toast.makeText(context,"No Connection",Toast.LENGTH_SHORT).show();
                        Log.d("internet","not available");
                    }

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return assignmentList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView assignmentName;
        TextView assignmentNumber;
        ProgressBar downloadingProgressBar;
        ImageButton downloadButton;
        RelativeLayout assignmentListLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            assignmentName=itemView.findViewById(R.id.assignment_name);
            assignmentNumber=itemView.findViewById(R.id.assignment_no);
            downloadingProgressBar=itemView.findViewById(R.id.downloading_progress);
            downloadButton=itemView.findViewById(R.id.download_pdf);
            assignmentListLayout=itemView.findViewById(R.id.assignment_card_layout);

        }
    }

    private boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED){
            downloadable=true;
            return true;

        } else {

            return false;
        }
    }
}
