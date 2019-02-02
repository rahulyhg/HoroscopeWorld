package com.example.kar.horoscope.world;

import android.content.Context;
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
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Forecast extends AppCompatActivity {

    private int day, month, year, week;
    static String yesterday, tomorrow, stringWeek, today;
    private static DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        Intent intent = getIntent();

        int id = intent.getIntExtra("Title", 0 );

        String[] Titles = getResources().getStringArray(R.array.Zodiacs);
        setTitle( Titles[id] );

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();


        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), getApplicationContext() );
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(myPagerAdapter);
        tabLayout.setTabsFromPagerAdapter(myPagerAdapter);
        viewPager.setCurrentItem(1, true);

        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        String date = getDate();
        ///Use Date in Firebase Search

    }

    private String getDate() {

        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        day = calendar.get ( Calendar.DAY_OF_MONTH );
        month = calendar.get ( Calendar.MONTH );
        year = calendar.get ( Calendar.YEAR );
        week = calendar.get ( Calendar.WEEK_OF_YEAR);
        month += 1;
        String formatDay = day + "";
        String formatMonth = month + "";

        if ( day < 10 )     formatDay = "0" + day;
        if ( month < 10 )   formatMonth = "0" + month;
        today =formatDay + "/" + formatMonth + "/" + year;

        calendar.add(Calendar.DATE, -1);
        yesterday = dateFormat.format(calendar.getTime());

        calendar.add ( Calendar.DATE, +2 );

        tomorrow = dateFormat.format(calendar.getTime());
        stringWeek = week + "";



        String formattedDate = day + "/" + month + "/" + year;
        return formattedDate;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if ( id == android.R.id.home ) {
            Intent intent = new Intent( this, MainActivity.class );
            startActivity ( intent );
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
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
            assert arguments != null;
            int pageNumber = arguments.getInt(ARG_PAGE);
            TextView myText = new TextView(getActivity());
            if ( pageNumber == 1 )                 SetYesterday(myText);
            else if ( pageNumber == 2 )            SetToday(myText);
            else if ( pageNumber == 3 )            SetTomorrow(myText);
            else if ( pageNumber == 4 )            SetWeek( myText );
            else if ( pageNumber == 5 )            SetMonth( myText );
            else if ( pageNumber == 6 )            SetYear( myText );

            myText.setGravity(Gravity.CENTER);
            myText.setTextColor(Color.WHITE);
            myText.setTextSize( 16 );
            myText.setPadding(15, 0, 15, 0 );
            return myText;
        }

    }

    private static void SetYear(TextView myText) {

    }

    private static void SetMonth(TextView myText) {

    }

    private static void SetWeek(TextView myText) {

    }

    private static void SetTomorrow(TextView myText) {
        query( tomorrow, myText );
    }

    private static void SetToday(TextView myText) {
        query( today, myText );
    }

    private static void SetYesterday(TextView myText) {
        query ( yesterday, myText );
    }

    public static void query(final String date, final TextView textView) {

        Query query = databaseReference.child("Leo").orderByChild("date").equalTo(date);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    Text text = postSnapshot.getValue(Text.class);
                    textView.setText(text.text);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

class MyPagerAdapter extends FragmentStatePagerAdapter {

    Context context;
    private String[] dates;


    MyPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        dates = context.getResources().getStringArray(R.array.date);
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



