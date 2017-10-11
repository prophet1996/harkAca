package com.accademy.harvin.harvinacademy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import android.widget.TextView;
import android.widget.Toast;

import com.accademy.harvin.harvinacademy.adapters.CustomDrawerAdapter;
import com.accademy.harvin.harvinacademy.db.AppDatabase;
import com.accademy.harvin.harvinacademy.db.utils.DatabaseInitializer;
import com.accademy.harvin.harvinacademy.fragment.FaceBookPostFragement;
import com.accademy.harvin.harvinacademy.fragment.StudyFragment;
import com.accademy.harvin.harvinacademy.model.Chapter;
import com.accademy.harvin.harvinacademy.model.DrawerItem;
import com.accademy.harvin.harvinacademy.model.Subject;
import com.accademy.harvin.harvinacademy.model.Subjects;
import com.accademy.harvin.harvinacademy.model.Topic;
import com.accademy.harvin.harvinacademy.model.user.Progresses;
import com.accademy.harvin.harvinacademy.network.HTTPclient;
import com.accademy.harvin.harvinacademy.network.RetrofitBuilder;
import com.accademy.harvin.harvinacademy.network.RetrofitInterface;
import com.accademy.harvin.harvinacademy.views.CircleTransform;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,TabLayout.OnTabSelectedListener{
    private static TabLayout tb;
    private static View navHeader;
    private NavigationView navigationView;
    private ListView mDrawerList;
    private List<String> mSubjectList=null;
    private Subjects mSubjects;
    private Progresses progresses;
    private static ImageView mProfilePhoto;
    private static TextView mProfilename;
    private static TextView mProfileusername;
    private static boolean addedTabs=false;
    private AppDatabase appDatabase;
    private Gson  GSON= new Gson();
    List<DrawerItem> dataList = new ArrayList<>();;
    TabLayout.Tab tab_dynamic;
    CustomDrawerAdapter customDrawerAdapter;

    private static String ImageURL="http://www.rd.com/wp-content/uploads/sites/2/2016/02/02-train-cat-treats.jpg";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        adddrawerItems();

        customDrawerAdapter= new CustomDrawerAdapter(MainActivity.this,R.layout.custom_drawer_item,dataList);
        navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        mDrawerList=headerView.findViewById(R.id.drawer_list_view);
        mDrawerList.setAdapter(customDrawerAdapter);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navHeader = navigationView.getHeaderView(0);


        mProfileusername=navHeader.findViewById(R.id.navbar_usernaame);
        mProfileusername.setText(getUsername());

        tb=findViewById(R.id.mainTablayout);
        tb.addOnTabSelectedListener(this);
        mSubjectList= new ArrayList<>();
        getProgressFromServer();
        if(!addedTabs) {
            addTabs();


        }
        replaceFacebookPosts(FaceBookPostFragement.getInstance(this));



    }

    private  void getSubjectListFromServer() {
        Log.d("getting subjects","first");

        OkHttpClient okHttpClient= HTTPclient.getClient(this);
        Retrofit retrofit = RetrofitBuilder.getRetrofit(this,okHttpClient);
        RetrofitInterface client=retrofit.create(RetrofitInterface.class);
        String username=getUsername();
        Log.d("getting subjects",username);
        Observable<Subjects> call =client.getSubjectList(username);
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
                        Log.d("tabs",mSubjects.getSubjects().get(0).getSubjectName()+"");
                        if(mSubjectList!=null)
                        for (int i=0;i<subjects.getSubjects().size();i++)
                            mSubjectList.add(i,subjects.getSubjects().get(i).getSubjectName());
                        addTabs();
                        addedTabs=true;
                       initdb();

                        StudyFragment sf=StudyFragment.getInstance(mSubjects.getSubjects().get(0).getId(),0);
                        sf.setProgress(progresses);
                        replaceFragment(sf);

                      //   ProgressUtil.saveProgress(mSubjects.getProgresses(),MainActivity.this);
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("getting subjects","on error");
                        e.printStackTrace();

                    }

                    @Override
                    public void onComplete() {
                        Log.d("getting subjects","on complete");
                    }
                });
    }

    private void initdb() {
        appDatabase = AppDatabase.getInMemoryDatabase(getApplicationContext());
        populateDb();


    }

    private void populateDb() {
        DatabaseInitializer.populateAsyncSubject(appDatabase,mSubjects.getSubjects());

        for(Subject sub:mSubjects.getSubjects()) {
            Log.d("roomSubject", "insterting " + sub.getSubjectName());
            DatabaseInitializer.populateAsyncChapter(appDatabase, sub.getChapters());
            for (Chapter chapter : sub.getChapters()){
                DatabaseInitializer.populateAsyncTopic(appDatabase, chapter.getTopics());
                Log.d("roomtopic", "insterting " + chapter.getTopics().size());

                for(Topic top :chapter.getTopics()){
                    Log.d("roomtopic", "insterting " + top.getFiles().get(0).chapterId);


                    DatabaseInitializer.populateAsyncFile(appDatabase,top.getFiles());
                }
            Log.d("room","topic main");}
        }
        Log.d("roomProg","prog inserted");
DatabaseInitializer.populateAsyncProgress(appDatabase,progresses.getProgresses());

    }
    @Override
    protected void onDestroy() {
        AppDatabase.destroyInstance();
        super.onDestroy();
    }


    private  void getProgressFromServer() {
        Log.d("getting progress","first");
        OkHttpClient okHttpClient= HTTPclient.getClient(this);

        Retrofit retrofit = RetrofitBuilder.getRetrofit(this,okHttpClient);
        RetrofitInterface client=retrofit.create(RetrofitInterface.class);
        String username=getUsername();
        Log.d("getting progress",username);
        Observable<Progresses> call =client.getProgress(username);
        call
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Progresses>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d("getting progress","on subscribe");
                    }
                    @Override
                    public void onNext(@NonNull Progresses newProgresses) {
                        progresses=newProgresses;
                        Log.d("getting progress",progresses.getProgresses().toString());
                        if(progresses!=null)

                       //   ProgressUtil.saveProgress(progresses,MainActivity.this);

                        getSubjectListFromServer();

                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("getting progress","on error");
                        Log.d("geting progress",e.toString());

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
            Log.d("tabs",""+mSubjectList.get(i));
            tab_dynamic.setText(mSubjectList.get(i));
            try{
                tb.addTab(tab_dynamic);
            }
            catch (NullPointerException np){np.printStackTrace();
                Toast.makeText(MainActivity.this,"Can't connect right now please try again later..",Toast.LENGTH_LONG).show();}
       }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
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

            if(!addedTabs) {
                getSubjectListFromServer();
                addTabs();
            Log.d("addedTabs","refreshed and added");

            }
            replaceFacebookPosts(FaceBookPostFragement.getInstance(this));

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

DrawerLayout drawer=findViewById(R.id.drawer_layout);
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
                StudyFragment sf=StudyFragment.getInstance(mSubjects.getSubjects().get(pos).getId(),pos);
                sf.setProgress(progresses);

                replaceFragment(sf);
                Log.d("done2",""+pos);

                break;
            case 1:
                 sf=StudyFragment.getInstance(mSubjects.getSubjects().get(pos).getId(),pos);
                sf.setProgress(progresses);

                replaceFragment(sf);
                Log.d("done2",""+pos);

                break;
            case 2:
                sf=StudyFragment.getInstance(mSubjects.getSubjects().get(pos).getId(),pos);
                sf.setProgress(progresses);

                replaceFragment(sf);
                Log.d("done2",""+pos);

                break;
            case 3:
                sf=StudyFragment.getInstance(mSubjects.getSubjects().get(pos).getId(),pos);
                sf.setProgress(progresses);

                replaceFragment(sf);
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
try{        mFragmentTranscation.commit();}
catch (IllegalStateException ise){
    ise.printStackTrace();
}
    }
    public void replaceFacebookPosts(FaceBookPostFragement fb){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
        fragmentTransaction.replace(R.id.facebook_posts_frame,fb);
        fragmentTransaction.commit();
    }
    private void adddrawerItems(){
        dataList.add(new DrawerItem("Exams", R.drawable.ic_map_black_1));
        dataList.add(new DrawerItem("Assignment", R.drawable.ic_import_contacts_black_1));
        dataList.add(new DrawerItem("Report", R.drawable.ic_content_paste_black_1));
        dataList.add(new DrawerItem("Profile", R.drawable.ic_person_black_1));
        dataList.add(new DrawerItem("Classroom", R.drawable.ic_people_black_1));

          }

    public String getUsername() {
        String username;
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

            username = sharedPreferences.getString("username", "z");
        Log.d("username",username);
        return username;
    }
}

