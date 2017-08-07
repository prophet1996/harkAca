package com.accademy.harvin.harvinacademy;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.accademy.harvin.harvinacademy.views.CircleTransform;
import com.accademy.harvin.harvinacademy.views.CustomImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

public class UserProfile extends AppCompatActivity {
    private ImageView mProfilePhoto;
    private ImageView custommageView;
    private Button deleteUser;
    private static String ImageURL="http://www.rd.com/wp-content/uploads/sites/2/2016/02/02-train-cat-treats.jpg";
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mProfilePhoto=(ImageView)findViewById(R.id.profilephotoact);

        deleteUser=(Button)findViewById(R.id.delete_user);

        Glide
                .with(this)   // pass Context
                .load(ImageURL)
                .error(R.drawable.solidfill)// pass the image url// optional scaletype
                .placeholder(R.drawable.ic_menu_camera) // optional placeholder
                                      .bitmapTransform(new CircleTransform(this))
                .into(mProfilePhoto); // the ImageView to which the image is to be
        deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences= UserProfile.this.getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.remove("username");
                editor.remove("password");
                editor.commit();
                Log.d("login","removed");

            }
        });




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
