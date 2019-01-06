package com.example.kar.horoscope.world;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;

public class Compatibility extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compatibility);

        setTitle("Compatibility");

        ArrayList <String> items = new ArrayList<>();

        int col = 2;
        int space = 150;

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new SpacesItemDecoration(  space ));
        recyclerView.setLayoutManager(new GridLayoutManager(this, col ));
        CompAdapter adapter = new CompAdapter(this, items );
        recyclerView.setAdapter(adapter);


        SharedPreferences settings = getSharedPreferences( "Prefs", 0 );
        String zodiacString = settings.getString("zodiac", "" );
        String[] itemsZodiac = zodiacString.split(",");

        items.addAll(Arrays.asList(itemsZodiac));
        adapter.setNameList( items );

        final ImageView male = findViewById(R.id.male);
        final ImageView female = findViewById(R.id.female );
        male.setClickable(true);
        female.setClickable(true);


        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                male.getLayoutParams().height = dpToPx(88);
                male.getLayoutParams().width = dpToPx(88);
                male.requestLayout();

                female.getLayoutParams().height = dpToPx(77);
                female.getLayoutParams().width = dpToPx(77);
                female.requestLayout();
            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                male.getLayoutParams().height = dpToPx(77);
                male.getLayoutParams().width = dpToPx(77);
                male.requestLayout();

                female.getLayoutParams().height = dpToPx( 88 );
                female.getLayoutParams().width = dpToPx(88);
                female.requestLayout();
            }
        });
    }

    public int dpToPx(int dp) {
        float density = getApplicationContext().getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }
}
