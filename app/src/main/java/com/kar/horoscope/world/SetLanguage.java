package com.kar.horoscope.world;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SetLanguage extends AppCompatActivity {

    Spinner spinner;
    Locale locale;
    String currentLanguage = "en", currentLang;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_language);

        currentLanguage = getIntent().getStringExtra(currentLang);
        spinner = findViewById(R.id.spinner);

        List<String> list = new ArrayList<>();
        list.add ( "Select Language");
        list.add ( "English" );
        list.add ( "Russian" );


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_dropdown_item, list);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        setLocale("en");
                        break;
                    case 2:
                        setLocale("ru");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent( this, MainActivity.class );
        startActivity(intent);
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


    private void setLocale(String localeName) {
        if (!localeName.equals(currentLanguage)) {
            locale = new Locale(localeName);
            Resources res = getResources();
            SaveLanguage( localeName );
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = locale;
            res.updateConfiguration(conf, dm);
            Intent refresh = new Intent(this, MainActivity.class);
            refresh.putExtra(currentLang, localeName);
            startActivity(refresh);
            finish();
        } else {
            Toast.makeText(SetLanguage.this, "Language already selected!", Toast.LENGTH_SHORT).show();
        }
    }

    private void SaveLanguage( String lang ) {
        preferences = PreferenceManager.getDefaultSharedPreferences(this );
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("language", lang );
        editor.apply();
    }

}