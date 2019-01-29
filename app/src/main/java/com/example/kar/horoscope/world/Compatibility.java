package com.example.kar.horoscope.world;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Compatibility extends AppCompatActivity implements ClickItem {

    private String currentTag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compatibility);

        setTitle("Compatibility");

        final ImageView male = findViewById(R.id.male);
        final ImageView female = findViewById(R.id.female );
        male.setClickable(true);
        female.setClickable(true);

        changeSize( male, 88 );
        setCurrentTag("male");

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                changeSize(male, 88 );
                changeSize(female, 77 );
                setCurrentTag("male");
            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                changeSize(male, 77);
                changeSize(female, 88 );
                setCurrentTag("female");
            }
        });

        int Names[] = {R.string.Aries, R.string.Taurus, R.string.Gemini, R.string.Cancer, R.string.Leo,
                       R.string.Virgo, R.string.Libra, R.string.Scorpio, R.string.Sagittarius, R.string.Capricorn,
                       R.string.Aquarius, R.string.Pisces };
        int Images[] = {
                R.drawable.logo_aries,
                R.drawable.logo_taurus,
                R.drawable.logo_gemini,
                R.drawable.logo_cancer,
                R.drawable.logo_leo,
                R.drawable.logo_virgo,
                R.drawable.logo_libra,
                R.drawable.logo_scorpio,
                R.drawable.logo_sagittarius,
                R.drawable.logo_capricorn,
                R.drawable.logo_aquarius,
                R.drawable.logo_pisces};


        int col = 2;
        int space = 180;

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new SpacesItemDecoration(  space ));
        recyclerView.setLayoutManager(new GridLayoutManager(this, col ));
        final CompAdapter adapter = new CompAdapter(this, Names, Images, this);
        recyclerView.setAdapter(adapter);


        Button showButton = findViewById( R.id.goCompatibility );
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView male_check = findViewById(R.id.gender_male);
                TextView female_check = findViewById(R.id.gender_female);
                ImageView maleImage = findViewById(R.id.male );
                ImageView femaleImage = findViewById(R.id.female);

                if ( male_check.getText().toString().equals("Male") || female_check.getText().toString().equals("Female")) {
                    Toast.makeText(Compatibility.this, "Select Zodiac", Toast.LENGTH_LONG).show();
                }

                else {
                    Intent intent = new Intent(Compatibility.this, ShowCompatibility.class );
                    intent.putExtra("MaleName", male_check.getText().toString());
                    intent.putExtra("FemaleName", female_check.getText().toString());

                    maleImage.setDrawingCacheEnabled(true);
                    femaleImage.setDrawingCacheEnabled(true);

                    Bitmap bitmap1 = maleImage.getDrawingCache();
                    Bitmap bitmap2 = femaleImage.getDrawingCache();

                    intent.putExtra("Bitmap1", bitmap1 );
                    intent.putExtra("Bitmap2", bitmap2);

                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            }
        });

    }


    public void setCurrentTag(String tag) {
        this.currentTag = tag;
    }

    public int dpToPx(int dp) {
        float density = getApplicationContext().getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }

    public void changeSize ( ImageView imageView, int size) {
        imageView.getLayoutParams().width = dpToPx(size);
        imageView.getLayoutParams().height = dpToPx(size );
        imageView.requestLayout();
    }

    @Override
    public void ItemClicked(int s, int downloadURL) {

        if ("male".equals(currentTag)) {

            TextView nameOfZodiac = findViewById(R.id.gender_male);
            nameOfZodiac.setText(s);
            nameOfZodiac.setTextColor(Color.GRAY);
            nameOfZodiac.setTextSize(18);

            ImageView imageView = findViewById(R.id.male );
            GlideApp.with(getApplicationContext()).load(downloadURL).into(imageView);
        }

        else if ("female".equals(currentTag)) {
            TextView nameOfZodiac = findViewById(R.id.gender_female );
            nameOfZodiac.setText(s);
            nameOfZodiac.setTextColor(Color.GRAY);
            nameOfZodiac.setTextSize(18);

            ImageView imageView = findViewById(R.id.female );
            GlideApp.with(getApplicationContext()).load(downloadURL).into(imageView);

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
