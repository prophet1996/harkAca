package com.accademy.harvin.harvinacademy;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.accademy.harvin.harvinacademy.adapters.CustomDrawerAdapter;
import com.accademy.harvin.harvinacademy.adapters.FacebookPostAdapter;
import com.accademy.harvin.harvinacademy.fragment.FaceBookPostFragement;
import com.accademy.harvin.harvinacademy.fragment.StudyFragment;
import com.accademy.harvin.harvinacademy.model.DrawerItem;
import com.accademy.harvin.harvinacademy.model.Subjects;
import com.accademy.harvin.harvinacademy.network.RetrofitInterface;
import com.accademy.harvin.harvinacademy.utils.Constants;
import com.accademy.harvin.harvinacademy.views.CircleTransform;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,TabLayout.OnTabSelectedListener{
    private static TabLayout tb;
    private static View navHeader;
    private NavigationView navigationView;
    private ListView mDrawerList;
    private List<String> mSubjectList=null;
    private Subjects mSubjects;
    private static ImageView mProfilePhoto;
    List<DrawerItem> dataList;
    TabLayout.Tab tab_dynamic;
    CustomDrawerAdapter customDrawerAdapter;

    private static String ImageURL="http://www.rd.com/wp-content/uploads/sites/2/2016/02/02-train-cat-treats.jpg";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        dataList = new ArrayList<>();
        adddrawerItems();

        customDrawerAdapter= new CustomDrawerAdapter(MainActivity.this,R.layout.custom_drawer_item,dataList);
         navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        mDrawerList=(ListView)headerView.findViewById(R.id.drawer_list_view);
        mDrawerList.setAdapter(customDrawerAdapter);

        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);

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
        tb=(TabLayout)findViewById(R.id.mainTablayout);
        tb.addOnTabSelectedListener(this);
        mSubjectList= new ArrayList<>();
        getSubjectListFromServer();
        replaceFacebookPosts(FaceBookPostFragement.getInstance(this));



    }

    private  void getSubjectListFromServer() {
        Log.d("getting subjects","first");
        int cachesize=10*1024*1024;
        Cache cache=new Cache(getCacheDir(),cachesize);
        OkHttpClient okHttpClient= new OkHttpClient.Builder()
                .cache(cache)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(Constants.BASE_URL)
                                .client(okHttpClient)
                                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
        RetrofitInterface client=retrofit.create(RetrofitInterface.class);
        Observable<Subjects> call =client.getSubjectList();
        call
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Subjects>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d("getting subjects","on subscribe");
                    }
                    @Override
                    public void onNext(@NonNull Subjects subjects) {
                        mSubjects=subjects;
                        if(mSubjectList!=null)
                        for (int i=0;i<subjects.getSubjects().size();i++)
                            mSubjectList.add(i,subjects.getSubjects().get(i).getSubjectName());
                        addTabs();
                        replaceFragment(StudyFragment.getInstance(mSubjects.getSubjects().get(1),MainActivity.this));
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("getting subjects","on error");
                    }

                    @Override
                    public void onComplete() {
                        Log.d("getting subjects","on complete");
                    }
                });
    }

    private void addTabs() {
        for (int i=0;i<mSubjectList.size();i++){
            tab_dynamic=tb.newTab();
            tab_dynamic.setText(mSubjectList.get(i));
            tb.addTab(tab_dynamic);
       }
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

        if (id == R.id.home_drawer) {
            // Handle the camera action
        } else if (id == R.id.exams_drawer) {

        } else if (id == R.id.assignments_drawer) {

        } else if (id == R.id.test_drawer) {

        } else if (id == R.id.share_drawer) {

        } else if (id == R.id.report_drawer) {

        }
DrawerLayout drawer=(DrawerLayout)findViewById(R.id.drawer_layout);
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
                replaceFragment(StudyFragment.getInstance(mSubjects.getSubjects().get(pos),MainActivity.this));
                Log.d("done2",""+pos);

                break;
            case 1:
                replaceFragment(StudyFragment.getInstance(mSubjects.getSubjects().get(pos),MainActivity.this));
                Log.d("done2",""+pos);

                break;
            case 2:
                replaceFragment(StudyFragment.getInstance(mSubjects.getSubjects().get(pos),MainActivity.this));
                Log.d("done2",""+pos);

                break;
            case 3:
                replaceFragment(StudyFragment.getInstance(mSubjects.getSubjects().get(pos),MainActivity.this));
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


    public void replaceFragment(StudyFragment sf){

        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction mFragmentTranscation=fragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
        mFragmentTranscation.replace(R.id.fragment_container,sf);
        mFragmentTranscation.commit();
    }
    public void replaceFacebookPosts(FaceBookPostFragement fb){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
        fragmentTransaction.replace(R.id.facebook_posts_frame,fb);
        fragmentTransaction.commit();
    }
    private void adddrawerItems(){
        dataList.add(new DrawerItem("Home", R.drawable.ic_action_home));
        dataList.add(new DrawerItem("Exams", R.drawable.ic_map_black_1));
        dataList.add(new DrawerItem("Assignment", R.drawable.ic_import_contacts_black_1));
        dataList.add(new DrawerItem("Report", R.drawable.ic_content_paste_black_1));
        dataList.add(new DrawerItem("Profile", R.drawable.ic_person_black_1));
        dataList.add(new DrawerItem("Classroom", R.drawable.ic_people_black_1));
        dataList.add(new DrawerItem("Settings", R.drawable.ic_settings_black_1));
          }
}

