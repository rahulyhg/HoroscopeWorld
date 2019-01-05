package com.example.kar.horoscope.world;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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

        for ( int i= 0; i < items.size(); i++ )
            Log.d ( "listItem = ", items.get(i));

        adapter.setNameList( items );


    }
}
