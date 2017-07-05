package com.accademy.harvin.harvinacademy;

import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.accademy.harvin.harvinacademy.adapters.CustomDrawerAdapter;
import com.accademy.harvin.harvinacademy.fragment.StudyFragment;
import com.accademy.harvin.harvinacademy.model.DrawerItem;
import com.accademy.harvin.harvinacademy.views.CircleTransform;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.toggle;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,TabLayout.OnTabSelectedListener{
    private static TabLayout tb;
private static View navHeader;
    private DrawerLayout drawer;
    private ListView mDrawerList;

    private static ImageView mProfilePhoto;
    List<DrawerItem> dataList;
    CustomDrawerAdapter customDrawerAdapter;

    private static String ImageURL="http://www.rd.com/wp-content/uploads/sites/2/2016/02/02-train-cat-treats.jpg";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        dataList = new ArrayList<DrawerItem>();
        adddrawerItems();

        customDrawerAdapter= new CustomDrawerAdapter(MainActivity.this,R.layout.custom_drawer_item,dataList);
         drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList=(ListView)drawer.findViewById(R.id.drawer_list_view);
        mDrawerList.setAdapter(customDrawerAdapter);
        drawer.setDrawerElevation(4);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navHeader = navigationView.getHeaderView(0);

        mProfilePhoto=(ImageView)navHeader.findViewById(R.id.profilephoto);
        Glide
                .with(this)
                .load(ImageURL)
                .error(R.drawable.solidfill)// pass the image url// optional scaletype
                .placeholder(R.drawable.ic_menu_camera) // optional placeholder
                .bitmapTransform(new CircleTransform(this))
                .into(mProfilePhoto); // the ImageView to which the image is to be loaded


        Log.d("imageView",""+mProfilePhoto);
        mProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(MainActivity.this,UserProfile.class);
                startActivity(i);
            }
        });


        /*Starting the code */
        tb=(TabLayout)findViewById(R.id.mainTablayout);
        tb.addOnTabSelectedListener(this);

        replaceFragment(new StudyFragment(),0);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        Log.d("done","done");
        int pos=tb.getSelectedTabPosition();
        Log.d("done",""+pos);
        switch (pos){
            case 0:
                replaceFragment(new StudyFragment(),pos);
                Log.d("done2",""+pos);

                break;
            case 1:
                replaceFragment(new StudyFragment(),pos);
                Log.d("done2",""+pos);

                break;
            case 2:
                replaceFragment(new StudyFragment(),pos);
                Log.d("done2",""+pos);

                break;
            case 3:
                replaceFragment(new StudyFragment(),pos);
                Log.d("done2",""+pos);
                break;



        }

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    public void replaceFragment(StudyFragment sf,int pos){
        Bundle pos1=new Bundle();
        pos1.putInt("key",pos);
        sf.setArguments(pos1);
        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction mFragmentTranscation=fragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
        mFragmentTranscation.replace(R.id.fragment_container,sf);
        mFragmentTranscation.commit();
    }
    private void adddrawerItems(){
        dataList.add(new DrawerItem("Home", R.drawable.ic_action_home));
        dataList.add(new DrawerItem("Exams", R.drawable.ic_map_black__1));
        dataList.add(new DrawerItem("Assignment", R.drawable.ic_import_contacts_black__1));
        dataList.add(new DrawerItem("Report", R.drawable.ic_content_paste_black__1));
        dataList.add(new DrawerItem("Profile", R.drawable.ic_person_black__1));
        dataList.add(new DrawerItem("Classroom", R.drawable.ic_people_black__1));
        dataList.add(new DrawerItem("Setting", R.drawable.ic_settings_black__1));
          }
}

