package com.example.kar.horoscope.world;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.Calendar;

public class SetUserZodiac extends AppCompatActivity{

    private TextView textView;
    private NumberPicker.OnValueChangeListener valueChangeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_user_zodiac);
        Button dateButton = findViewById(R.id.dateButton);
        textView = findViewById(R.id.textDate );

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        });


        final String[] names = {"Aries", "Taurus", "Gemini", "Cancer", "Leo", "Virgo", "Libra", "Scorpio", "Sagittarius", "Capricorn", "Aquarius", "Pisces"};

        final NumberPicker namepicker = findViewById(R.id.numberPicker );
        namepicker.setMinValue(0);
        namepicker.setMaxValue(names.length - 1 );
        namepicker.setDisplayedValues(names);
        namepicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        namepicker.setWrapSelectorWheel(false);
        changeDividerColor(namepicker, Color.TRANSPARENT);

        namepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SetUserZodiac.this, "skldjflkasjfk;lasjdf", Toast.LENGTH_LONG).show();
                int pickedValue = namepicker.getValue();
                Toast.makeText(SetUserZodiac.this, "It is " + names[pickedValue], Toast.LENGTH_LONG).show();

            }
        });
    }

    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            month = month + 1;
            String date = day + "/" + month + "/" + year;
            updateDate( date );
        }
    };

    private void changeDividerColor(NumberPicker picker, int color) {
        try {
            Field mField = NumberPicker.class.getDeclaredField("mSelectionDivider");
            mField.setAccessible(true);
            ColorDrawable colorDrawable = new ColorDrawable(color);
            mField.set(picker, colorDrawable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateDate(String date) {
        textView.setText(date);
    }
}
