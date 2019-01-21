package com.example.kar.horoscope.world;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme ( R.style.AppTheme );
        super.onCreate(savedInstanceState);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        String s = preferences.getString("Name", null);


        Intent intent;

        if ( s != null ) {
           intent = new Intent( SplashScreen.this, Forecast.class);
           intent.putExtra("Title", s );
        }

        else if ( user == null )     intent = new Intent(SplashScreen.this, LogIn.class);
        else                         intent = new Intent(SplashScreen.this, MainActivity.class);


        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        finish();

    }
}