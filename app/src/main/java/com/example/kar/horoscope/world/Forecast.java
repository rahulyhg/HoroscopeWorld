package com.example.kar.horoscope.world;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Forecast extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MyPagerAdapter myPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        Intent intent = getIntent();
        String activity_title = intent.getStringExtra("Title");
        setTitle(activity_title);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(myPagerAdapter);
        tabLayout.setTabsFromPagerAdapter(myPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if ( id == android.R.id.home ) {
            Intent intent = new Intent( this, MainActivity.class );
            startActivity ( intent );
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class MyFragment extends Fragment {

        private static final java.lang.String ARG_PAGE = "arg_page";

        public MyFragment() {

        }

        public static MyFragment newInstance ( int pageNumber ) {
            MyFragment myFragment = new MyFragment();
            Bundle arguments = new Bundle();

            arguments.putInt(ARG_PAGE, pageNumber + 1 );
            myFragment.setArguments( arguments );
            return myFragment;
        }


        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            Bundle arguments = getArguments();
            int pageNumber = arguments.getInt(ARG_PAGE);
            TextView myText = new TextView(getActivity());
            myText.setText("Hello I am the text inside this Fragment " + pageNumber);
            myText.setGravity(Gravity.CENTER);
            myText.setTextColor(Color.WHITE);
            return myText;
        }
    }
}

class MyPagerAdapter extends FragmentStatePagerAdapter {
    private String[] dates = {"Yesterday", "Today", "Tomorrow", "Week", "Month", "2019"};


    MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return Forecast.MyFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return dates[position];
    }
}



