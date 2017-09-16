package com.accademy.harvin.harvinacademy.exceptionHandler;

/**
 * Created by ishank on 3/9/17.
 */

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.accademy.harvin.harvinacademy.Login_Main;
import com.accademy.harvin.harvinacademy.MyApplication;

import java.lang.Thread.UncaughtExceptionHandler;
public class DefaultExceptionHandler implements UncaughtExceptionHandler {
    private UncaughtExceptionHandler defaultHandler;
    Activity activity;
    public DefaultExceptionHandler(Activity activity) {
        this.activity = activity;
    }

    @SuppressWarnings("WrongConstant")
    @Override
    public void uncaughtException(Thread t, Throwable ex) {
        try {

            Intent intent = new Intent(activity, Login_Main.class);

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK
                    | Intent.FLAG_ACTIVITY_NEW_TASK);

            PendingIntent pendingIntent = PendingIntent.getActivity(
                    MyApplication.getInstance().getBaseContext(), 0, intent, intent.getFlags());

            //Following code will restart your application after 2 seconds
            AlarmManager mgr = (AlarmManager) MyApplication.getInstance().getBaseContext()
                    .getSystemService(Context.ALARM_SERVICE);
            mgr.set(AlarmManager.RTC, System.currentTimeMillis() ,
                    pendingIntent);


            //This will finish your activity manually
            activity.finish();
            //This will stop your application and take out from it.
            System.exit(2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    }

