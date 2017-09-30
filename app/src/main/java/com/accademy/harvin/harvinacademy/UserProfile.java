package com.accademy.harvin.harvinacademy;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.accademy.harvin.harvinacademy.views.CircleTransform;
import com.accademy.harvin.harvinacademy.views.CustomImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import org.w3c.dom.Text;

public class UserProfile extends AppCompatActivity {

    private static String ImageURL="http://www.rd.com/wp-content/uploads/sites/2/2016/02/02-train-cat-treats.jpg";
    private TextView username;


    ProgressDialog progressDoalog;
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        ImageView mProfilePhoto = (ImageView) findViewById(R.id.profilephotoact);


        Button deleteUser = (Button) findViewById(R.id.delete_user);
        username=(TextView)findViewById(R.id.username);
        final SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(UserProfile.this);
        String user=sharedPreferences.getString("username","N/A");
        username.setText(user);


        Glide
                .with(this)   // pass Context
                .load(ImageURL)
                .error(R.drawable.solidfill)// pass the image url// optional scaletype
                .placeholder(R.drawable.ic_menu_camera) // optional placeholder

                .into(mProfilePhoto); // the ImageView to which the image is to be
        deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(UserProfile.this,R.style.AlertDialogCustom);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showdailog();
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.remove("username");
                        editor.remove("password");
                        editor.commit();
                        Log.d("login","removed");

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                Intent i=new Intent(UserProfile.this,Login_Main.class);
                                startActivity(i);
                                finish();
                            }
                        },1000);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setTitle("Delete Profile");
                builder.setMessage("Are you sure?");
                AlertDialog dailog=builder.create();

                dailog.show();
            }
        });

    }
    private void showdailog() {
        progressDoalog =new ProgressDialog(UserProfile.this);
        progressDoalog.setIndeterminate(true);
        progressDoalog.setMessage("Logging out....");
        progressDoalog.setTitle("Harvin Academy Login");
        progressDoalog.setCancelable(false);
        progressDoalog.show();

    }

}
