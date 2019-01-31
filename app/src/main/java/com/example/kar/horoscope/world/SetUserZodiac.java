package com.example.kar.horoscope.world;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.NumberPicker;

import java.lang.reflect.Field;

public class SetUserZodiac extends AppCompatActivity{

    private NumberPicker.OnValueChangeListener valueChangeListener;
    private String selectedName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_user_zodiac);


        final String[] names = getResources().getStringArray(R.array.Zodiacs);

        final NumberPicker namepicker = findViewById(R.id.numberPicker );
        namepicker.setMinValue(0);
        namepicker.setMaxValue(names.length - 1 );
        namepicker.setDisplayedValues(names);
        namepicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        namepicker.setWrapSelectorWheel(false);
        changeDividerColor(namepicker, Color.TRANSPARENT);

        namepicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldValue, int newValue) {
                selectedName = names[namepicker.getValue()];
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.set, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem menuItem ) {
        if ( menuItem.getItemId() == R.id.set ) {
            if ( selectedName == null )     selectedName = getResources().getString(R.string.Aries);

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this );
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("Name", selectedName );
            editor.apply();

            Intent intent = new Intent( SetUserZodiac.this, Forecast.class );
            intent.putExtra("Title", selectedName );
            startActivity(intent);

            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();

            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

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

}
