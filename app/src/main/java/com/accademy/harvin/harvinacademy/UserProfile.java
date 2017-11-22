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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.accademy.harvin.harvinacademy.utils.SharedPref;
import com.accademy.harvin.harvinacademy.views.CircleTransform;
import com.accademy.harvin.harvinacademy.views.CustomImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import org.w3c.dom.Text;

import static com.accademy.harvin.harvinacademy.utils.Constants.BATCH_KEY;
import static com.accademy.harvin.harvinacademy.utils.Constants.PASSWORD_KEY;
import static com.accademy.harvin.harvinacademy.utils.Constants.USERNAME_KEY;

public class UserProfile extends AppCompatActivity {

    private static String ImageURL="http://45.55.154.27/images/user.png";
    private TextView username;
    private TextView email_textView;
    private TextView password_textView;


    ProgressDialog progressDoalog;
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        ImageView mProfilePhoto =findViewById(R.id.profilephotoact);


        Button deleteUser =  findViewById(R.id.delete_user);
        username=findViewById(R.id.username);
        email_textView=findViewById(R.id.profil_email);
        password_textView=findViewById(R.id.profile_password);

        final SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(UserProfile.this);
        String user=sharedPreferences.getString("username","N/A");
        String email=sharedPreferences.getString(BATCH_KEY,"N/A");
        String password=sharedPreferences.getString("password","N/A");

        email_textView.setText(email);
        password_textView.setText(password);
        username.setText(user);



        Glide
                .with(this)   // pass Context
                .load(ImageURL)
                .error(R.drawable.solidfill)// pass the image url// optional scaletype
                .placeholder(R.drawable.solidfill) // optional placeholder
                .transform(new CircleTransform(this))
                .into(mProfilePhoto); // the ImageView to which the image is to be
        deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(UserProfile.this,R.style.AlertDialogCustom);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.remove(USERNAME_KEY);
                        editor.remove(PASSWORD_KEY);
                        editor.remove(BATCH_KEY);
                        editor.commit();
                        Log.d("login","removed");

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                Intent i=new Intent(UserProfile.this,Login_Main.class);



                                startActivity(i);
                                finish();

                            }
                        },500);
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

    }}