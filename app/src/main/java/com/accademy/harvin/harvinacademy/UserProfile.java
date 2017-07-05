package com.accademy.harvin.harvinacademy;

import android.media.Image;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.accademy.harvin.harvinacademy.views.CircleTransform;
import com.bumptech.glide.Glide;

public class UserProfile extends AppCompatActivity {
    private ImageView mProfilePhoto;
    private static String ImageURL="http://www.rd.com/wp-content/uploads/sites/2/2016/02/02-train-cat-treats.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mProfilePhoto=(ImageView)findViewById(R.id.profilephotoact);

        Glide
                .with(this)   // pass Context
                .load(ImageURL)
                .error(R.drawable.solidfill)// pass the image url// optional scaletype
                .placeholder(R.drawable.ic_menu_camera) // optional placeholder
                .bitmapTransform(new CircleTransform(this))
                .into(mProfilePhoto); // the ImageView to which the image is to be loaded

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
