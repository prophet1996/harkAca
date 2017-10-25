package com.accademy.harvin.harvinacademy.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.accademy.harvin.harvinacademy.CourseActivity;
import com.accademy.harvin.harvinacademy.R;
import com.accademy.harvin.harvinacademy.model.DownloadedPdf;
import com.accademy.harvin.harvinacademy.network.RetrofitInterface;
import com.accademy.harvin.harvinacademy.utils.Constants;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by ishank on 20/7/17.
 */

public class DownloadService extends IntentService {

    private NotificationCompat.Builder notificationBuilder;
    private NotificationManager notificationManager;
    private int totalFileSize;
    private int downloadingtopicPosition;
    private String downloadingtopicname;
    private String downloadingid;
    private boolean assignment;


    public DownloadService() {
        super("Download Service");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        downloadingtopicPosition=intent.getIntExtra("topicno",-1);
        Log.d("download1",downloadingtopicPosition+"");
        downloadingtopicname=intent.getStringExtra("topicname");
        Log.d("download1",downloadingtopicname);
        assignment=intent.getBooleanExtra("assignment",false);
        Log.d("download1",assignment+"");
        downloadingid =intent.getStringExtra("topicid");
        Log.d("download2", downloadingid);

        notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        notificationBuilder=new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_download)
                .setContentTitle(downloadingtopicname)
                .setContentText("Downloading PDF")
                .setAutoCancel(true);

        notificationManager.notify(0,notificationBuilder.build());
        initDowload();

    }
    private void initDowload(){
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .build();

        RetrofitInterface client=retrofit.create(RetrofitInterface.class);

        if(!assignment)
        {
        Call<ResponseBody> request=client.downloadFile(downloadingid);

        try{
            Log.d("download","retorfit1");

            downloadfile(request.execute().body());
            Log.d("download","retorfit2");

        }catch (IOException e){
            e.printStackTrace();
            Log.d("download","failed");

        }}
else {
            Call<ResponseBody> request=client.downloadAssignment(downloadingid);

            try{
                Log.d("download","retorfit1");

                downloadfile(request.execute().body());
                Log.d("download","retorfit2");

            }catch (IOException e){
                e.printStackTrace();
                Log.d("download","failed");

            }}


    }
    private void downloadfile(ResponseBody body) throws IOException {
        int count;
        byte data[]= new byte[1024*4];
        long fileSize=body.contentLength();
        Log.d("download2",fileSize+"");
        InputStream bis=new BufferedInputStream(body.byteStream(),1024*8);
        File outputfile=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),downloadingtopicname+".pdf");
        OutputStream output= null;
        try {
            output = new FileOutputStream(outputfile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.d("download","failed opening file");


        }
        long total=0;
        long startTime= System.currentTimeMillis();
        int timeCount=1;
        Log.d("download","retorfit3");

        while((count=bis.read(data))!=-1){
            total+=count;
            Log.d("download",data.toString());
            totalFileSize = (int) (fileSize / (Math.pow(1024, 2)));

            double current = Math.round(total / (Math.pow(1024, 2)));

            int progress = (int) ((total * 100) / fileSize);

            long currentTime = System.currentTimeMillis() - startTime;

            DownloadedPdf download = new DownloadedPdf();
            download.setTotalFileSize(totalFileSize);
            Log.d("download","retorfit1");

            if (currentTime > 1000 * timeCount) {
                download.setCurrentFileSize((int) current);
                download.setProgress(progress);
                sendNotification(download);
                timeCount++;
            }

            output.write(data, 0, count);
        }
        Log.d("download","download completed");
        onDownloadComplete();
        output.flush();
        output.close();
        bis.close();

    }

    private void sendNotification(DownloadedPdf download){

        sendIntent(download);
        notificationBuilder.setProgress(100,download.getProgress(),false);
        notificationBuilder.setContentText("Downloading file "+ download.getCurrentFileSize() +"/"+totalFileSize +" MB");
        notificationManager.notify(0, notificationBuilder.build());
    }

    private void sendIntent(DownloadedPdf download){

        Intent intent = new Intent(CourseActivity.MESSAGE_PROGRESS);
        intent.putExtra("download",download);
        intent.putExtra("topicpos",downloadingtopicPosition);
        Log.d("send intent","sendingdownloadcomplete intent");
        LocalBroadcastManager.getInstance(DownloadService.this).sendBroadcast(intent);
    }

    private void onDownloadComplete(){

        DownloadedPdf download = new DownloadedPdf();
        download.setProgress(100);
        sendIntent(download);

        notificationManager.cancel(0);
        notificationBuilder.setProgress(0,0,false);
        notificationBuilder.setContentText("File Downloaded");
        notificationManager.notify(0, notificationBuilder.build());

    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        notificationManager.cancel(0);
    }
}
