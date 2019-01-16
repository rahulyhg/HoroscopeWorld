package com.example.kar.horoscope.world;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ShowCompatibility extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_compatibility);

        setTitle("Compatibility");
        Intent intent = getIntent();
        String male = intent.getStringExtra("MaleName");
        String female = intent.getStringExtra("FemaleName");

        TextView maleText = findViewById(R.id.male_zodiac);
        TextView femaleText = findViewById(R.id.female_zodiac);

        setText ( maleText, male );
        setText ( femaleText, female );

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void setText ( TextView textView, String s ) {
        textView.setText ( s );
        textView.setTextColor(Color.GRAY);
        textView.setTextSize(18);
    }
}
