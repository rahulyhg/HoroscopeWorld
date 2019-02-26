package com.kar.horoscope.world;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codesgood.views.JustifiedTextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class Forecast extends AppCompatActivity {

    private int day, month, year, week;
    static String yesterday, tomorrow, stringWeek, today, stringMonth, stringYear;
    static String titleZodiac;
    private static DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        Intent intent = getIntent();

        int id = intent.getIntExtra("Title", 0 );

        String[] Titles = getResources().getStringArray(R.array.Zodiacs);
        titleZodiac = Titles[id];
        setTitle( Titles[id] );

        ImageView img = findViewById(R.id.forecastimage);
        if (titleZodiac.equals("Aries")) GlideApp.with(getApplicationContext()).load(R.drawable.aries).into(img);
        if (titleZodiac.equals("Taurus")) GlideApp.with(getApplicationContext()).load(R.drawable.taurus).into(img);
        if (titleZodiac.equals("Gemini")) GlideApp.with(getApplicationContext()).load(R.drawable.gemini).into(img);
        if (titleZodiac.equals("Cancer")) GlideApp.with(getApplicationContext()).load(R.drawable.cancer).into(img);
        if (titleZodiac.equals("Leo")) GlideApp.with(getApplicationContext()).load(R.drawable.leo).into(img);
        if (titleZodiac.equals("Virgo")) GlideApp.with(getApplicationContext()).load(R.drawable.virgo).into(img);
        if (titleZodiac.equals("Libra")) GlideApp.with(getApplicationContext()).load(R.drawable.libra).into(img);
        if (titleZodiac.equals("Scorpio")) GlideApp.with(getApplicationContext()).load(R.drawable.scorpio).into(img);
        if (titleZodiac.equals("Sagittarius")) GlideApp.with(getApplicationContext()).load(R.drawable.sagittarius).into(img);
        if (titleZodiac.equals("Capricorn")) GlideApp.with(getApplicationContext()).load(R.drawable.capricorn).into(img);
        if (titleZodiac.equals("Aquarius")) GlideApp.with(getApplicationContext()).load(R.drawable.aquarius).into(img);
        if (titleZodiac.equals("Pisces")) GlideApp.with(getApplicationContext()).load(R.drawable.pisces).into(img);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReference.keepSynced(true);

        MainPagerAdapter myPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), getApplicationContext() );
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(myPagerAdapter);
        viewPager.setCurrentItem(1, true);
        viewPager.setOffscreenPageLimit(4);

        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        String date = getDate();
    }


    private String getDate() {

        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        calendar.add(Calendar.WEEK_OF_YEAR, 1);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        day = calendar.get ( Calendar.DAY_OF_MONTH );
        month = calendar.get ( Calendar.MONTH );
        year = calendar.get ( Calendar.YEAR );
        week = calendar.get ( Calendar.WEEK_OF_YEAR);
        stringWeek = week + "";

        month += 1; stringMonth = month + "";
        stringYear = year + "";


        String formatDay = day + "";
        String formatMonth = month + "";

        if ( day < 10 )     formatDay = "0" + day;
        if ( month < 10 )   formatMonth = "0" + month;
        today =formatDay + "/" + formatMonth + "/" + year;


        Log.d("Today---->", today );

        calendar.add(Calendar.DATE, -1);
        yesterday = dateFormat.format(calendar.getTime());

        calendar.add ( Calendar.DATE, +2 );

        tomorrow = dateFormat.format(calendar.getTime());


        return day + "/" + month + "/" + year;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent( this, MainActivity.class );
        startActivity ( intent );
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
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



    public static class PostsFragment extends Fragment {
        private static final java.lang.String ARG_PAGE = "arg_page";


        public static Fragment newInstance( int pageNumber ) {
            PostsFragment postsFragment = new PostsFragment();
            Bundle arguments = new Bundle();

            arguments.putInt(ARG_PAGE, pageNumber + 1 );
            postsFragment.setArguments( arguments );
            return postsFragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.content, parent, false);
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            Bundle arguments = getArguments();
            assert arguments != null;
            int pageNumber = arguments.getInt(ARG_PAGE);

            ///Justified TextView is for making all lines equal length
            JustifiedTextView myText = view.findViewById(R.id.justText);
            if ( pageNumber == 1 )                 SetYesterday(myText);
            else if ( pageNumber == 2 )            SetToday(myText);
            else if ( pageNumber == 3 )            SetTomorrow(myText);
            else if ( pageNumber == 4 )            SetMonth( myText );
            /// else if ( pageNumber == 5 )            SetMonth( myText );
            /// else if ( pageNumber == 6 )            SetYear( myText );

            myText.setGravity(Gravity.CENTER_HORIZONTAL);
            myText.setTextColor(Color.WHITE);
        }
    }


    private static void SetYear(TextView myText)        { queryYear( stringYear, myText); }
    private static void SetMonth(TextView myText)       { queryMonth( stringMonth, myText); }
    private static void SetWeek(TextView myText)        { queryWeek( stringWeek, myText ); }
    private static void SetTomorrow(TextView myText)    { queryDay( tomorrow, myText ); }
    private static void SetToday(TextView myText)       { queryDay( today, myText ); }
    private static void SetYesterday(TextView myText)   { queryDay ( yesterday, myText ); }


    public static void queryYear(final String date, final TextView textView) {

        Query query = databaseReference.child(titleZodiac).orderByChild("year").equalTo(date);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    YearText text = postSnapshot.getValue(YearText.class);
                    textView.setText(text.text);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public static void queryMonth(final String date, final TextView textView) {

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection(titleZodiac);
        com.google.firebase.firestore.Query query = collectionReference.whereEqualTo("month", date );

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if ( task.isSuccessful() ) {
                    for ( QueryDocumentSnapshot snapshot : task.getResult() ) {
                        MonthText text = snapshot.toObject(MonthText.class);
                        textView.setText( text.text );
                    }
                }
            }
        });


    }
    public static void queryWeek(final String date, final TextView textView) {

        Query query = databaseReference.child(titleZodiac).orderByChild("week").equalTo(date);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    WeekText text = postSnapshot.getValue(WeekText.class);
                    textView.setText(text.text);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public static void queryDay(final String date, final TextView textView) {

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection(titleZodiac);

        com.google.firebase.firestore.Query query = collectionReference.whereEqualTo("date", date );

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if ( task.isSuccessful()) {

                    for (QueryDocumentSnapshot snapshot : Objects.requireNonNull(task.getResult())) {
                        Text text = snapshot.toObject( Text.class );
                        textView.setText( text.text );
                    }

                }
            }
        });
    }
}

