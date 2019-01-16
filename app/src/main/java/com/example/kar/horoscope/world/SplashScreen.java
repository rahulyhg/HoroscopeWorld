package com.example.kar.horoscope.world;

import android.content.Intent;
import android.os.Bundle;
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
        Intent intent;

        if ( user == null )     intent = new Intent(SplashScreen.this, LogIn.class);
        else                    intent = new Intent(SplashScreen.this, MainActivity.class);


        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);




        finish();

    }
}