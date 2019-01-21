package com.example.kar.horoscope.world;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme ( R.style.AppTheme );
        super.onCreate(savedInstanceState);

        long startTime = System.nanoTime();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String s = preferences.getString("Name", null);

        Intent intent;

        if ( s != null ) {
           intent = new Intent( SplashScreen.this, Forecast.class);
           intent.putExtra("Title", s );
        }

        else if ( user == null )     intent = new Intent(SplashScreen.this, LogIn.class);
        else                         intent = new Intent(SplashScreen.this, MainActivity.class);


        startActivity(intent);

        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        Log.d ( "time is -------->" , "" + totalTime  );

        finish();

    }
}