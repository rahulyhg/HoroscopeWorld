package com.example.kar.horoscope.world;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class SetUserZodiac extends AppCompatActivity{

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_user_zodiac);

        textView = findViewById(R.id.textDate );

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get ( Calendar.YEAR );
        int month = calendar.get( Calendar.MONTH );
        int day = calendar.get ( Calendar.DAY_OF_MONTH );

        DatePickerDialog datePickerDialog = new DatePickerDialog(SetUserZodiac.this,
               android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth,
               dateSetListener,
               year, month, day);
        datePickerDialog.show();

    }

    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            month = month + 1;
            String date = day + "/" + month + "/" + year;
            updateDate( date );
        }
    };

    public void updateDate(String date) {
        textView.setText(date);
    }
}
