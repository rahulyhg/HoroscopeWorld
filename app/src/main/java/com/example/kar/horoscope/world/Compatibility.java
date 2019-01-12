package com.example.kar.horoscope.world;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class Compatibility extends AppCompatActivity implements ClickItem {

    private String currentTag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compatibility);

        setTitle("Compatibility");

        Button showButton = findViewById( R.id.goCompatibility );
        final ImageView male = findViewById(R.id.male);
        final ImageView female = findViewById(R.id.female );
        male.setClickable(true);
        female.setClickable(true);

        changeSize( male, 88 );
        setCurrentTag("male");

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                changeSize(male, 88 );
                changeSize(female, 77 );
                setCurrentTag("male");
            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                changeSize(male, 77);
                changeSize(female, 88 );
                setCurrentTag("female");
            }
        });

        ArrayList <String> items = new ArrayList<>();

        int col = 2;
        int space = 180;

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new SpacesItemDecoration(  space ));
        recyclerView.setLayoutManager(new GridLayoutManager(this, col ));
        CompAdapter adapter = new CompAdapter(this, items, this);
        recyclerView.setAdapter(adapter);


        SharedPreferences settings = getSharedPreferences( "Prefs", 0 );
        String zodiacString = settings.getString("zodiac", "" );
        assert zodiacString != null;
        String[] itemsZodiac = zodiacString.split(",");

        items.addAll(Arrays.asList(itemsZodiac));
        adapter.setNameList( items );

    }

    public void setCurrentTag(String tag) {
        this.currentTag = tag;
    }

    public int dpToPx(int dp) {
        float density = getApplicationContext().getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }

    public void changeSize ( ImageView imageView, int size) {
        imageView.getLayoutParams().width = dpToPx(size);
        imageView.getLayoutParams().height = dpToPx(size );
        imageView.requestLayout();
    }

    @Override
    public void ItemClicked(String s) {

        if ("male".equals(currentTag)) {

            TextView nameOfZodiac = findViewById(R.id.gender_male);
            nameOfZodiac.setText(s);
            nameOfZodiac.setTextColor(Color.GRAY);
            nameOfZodiac.setTextSize(18);
        }

        else if ("female".equals(currentTag)) {
            TextView nameOfZodiac = findViewById(R.id.gender_female );
            nameOfZodiac.setText(s);
            nameOfZodiac.setTextColor(Color.GRAY);
            nameOfZodiac.setTextSize(18);
        }
    }
}
