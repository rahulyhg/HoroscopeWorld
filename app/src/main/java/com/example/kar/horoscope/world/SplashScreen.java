package com.example.kar.horoscope.world;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLanguage(getLangCode());

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int s = preferences.getInt("Name", -1 );

        Intent intent;


        if ( s != -1 ) {
           intent = new Intent( SplashScreen.this, Forecast.class);
           intent.putExtra("Title", s );
        }

        else if ( user == null )     intent = new Intent(SplashScreen.this, LogIn.class);
        else                         intent = new Intent(SplashScreen.this, MainActivity.class);


        startActivity(intent);
        finish();
    }

    private void setLanguage( String lang) {
        Locale locale = new Locale(lang);//Set Selected Locale
        Locale.setDefault(locale);//set new locale as default
        Configuration config = new Configuration();//get Configuration
        config.locale = locale;//set config locale as selected locale
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());//Update the config
    }

    private String getLangCode() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String langCode = preferences.getString("language", "en");
        // save english 'en' as the default language
        return langCode;
    }
}