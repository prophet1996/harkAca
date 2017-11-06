package com.accademy.harvin.harvinacademy;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.accademy.harvin.harvinacademy.utils.SharedPref;

import static com.accademy.harvin.harvinacademy.utils.Constants.FIRST_TIME_LOGIN;

public class OnBoardingActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    int page=-1;
    ImageButton nextButton;
    Button skipButton;
    Button finishButton;
    ImageView[] indicators;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        setFullScreen();
            setContentView(R.layout.activity_on_boarding);
        Log.d("onboarding","what");



            // Create the adapter that will return a fragment for each of the three
            // primary sections of the activity.
            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        final int color1 = ContextCompat.getColor(this, R.color.onboarding_green);
        final int color2 = ContextCompat.getColor(this, R.color.onboarding_blue);
        final int color3 = ContextCompat.getColor(this, R.color.onboarding_red);

       final  int[] colorList = new int[]{color1, color2, color3};

        // Set up the ViewPager with the sections adapter.
            mViewPager = findViewById(R.id.container);
            finishButton=findViewById(R.id.intro_btn_finish);
            nextButton=findViewById(R.id.intro_btn_next);
            skipButton=findViewById(R.id.intro_btn_skip);
            indicators=new ImageView[3];

                indicators[0]=findViewById(R.id.intro_indicator_0);
                indicators[1]=findViewById(R.id.intro_indicator_1);
                indicators[2]=findViewById(R.id.intro_indicator_2);


            mViewPager.setAdapter(mSectionsPagerAdapter);
            final ArgbEvaluator evaluator= new ArgbEvaluator();


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    /*
                    * COLOR UPDATE
                    * */
                    int colorUpdate= (Integer) evaluator.evaluate(positionOffset,colorList[position],colorList[position == 2 ? position : position + 1]);
                    mViewPager.setBackgroundColor(colorUpdate);

                }

                @Override
                public void onPageSelected(int position) {   page = position;
                    updateIndicators(page);

                    switch (position) {
                        case 0:
                            mViewPager.setBackgroundColor(color1);
                            break;
                        case 1:
                            mViewPager.setBackgroundColor(color2);
                            break;
                        case 2:
                            mViewPager.setBackgroundColor(color3);
                            break;
                    }

                    nextButton.setVisibility(position == 2 ? View.GONE : View.VISIBLE);
                    finishButton.setVisibility(position == 2 ? View.VISIBLE : View.GONE);


                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPref.setBooleanPref(OnBoardingActivity.this,FIRST_TIME_LOGIN,false);
                Intent i=new Intent(OnBoardingActivity.this,Login_Main.class);
                startActivity(i);
                finish();
            }
        });
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPref.setBooleanPref(OnBoardingActivity.this,FIRST_TIME_LOGIN,false);
                Intent i=new Intent(OnBoardingActivity.this,Login_Main.class);
                startActivity(i);
                finish();
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1);
            }
        });


    }

    private void setFullScreen() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY


        );
    }

    private void updateIndicators(int position){
        for(int i=0;i<indicators.length;i++){
            indicators[i].setBackgroundResource(i==position?R.drawable.indicator_selected
            :R.drawable.indicator_unselected);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_on_boarding, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private ImageView onboardingImageView;
        private  int sectionNumber;
        private AppCompatTextView onBoardingTextView;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.sectionNumber=sectionNumber;
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_on_boarding, container, false);
            onboardingImageView=rootView.findViewById(R.id.onboarding_imageView);
            onBoardingTextView=rootView.findViewById(R.id.onboarding_textView);
            switch (sectionNumber){
                case 1:
                    onboardingImageView.setImageResource(R.drawable.onboarding_green);
                    onBoardingTextView.setText(getResources().getString(R.string.onboarding_study));
                    break;
                case 2:
                    onboardingImageView.setImageResource(R.drawable.onboarding_blue);

                    onBoardingTextView.setText(getResources().getString(R.string.onboarding_mcq));
                    break;
                case 3:
                    onboardingImageView.setImageResource(R.drawable.onboarding_red);


                    onBoardingTextView.setText(getResources().getString(R.string.onboarding_assignment));
                    break;
            }


            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();

        setFullScreen();

    }
}
