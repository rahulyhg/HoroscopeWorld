package com.example.kar.horoscope.world;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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

        ArrayList <String> items = new ArrayList<>();

        int col = 2;
        int space = 180;

        final ArrayList<Image> imageList = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new SpacesItemDecoration(  space ));
        recyclerView.setLayoutManager(new GridLayoutManager(this, col ));
        final CompAdapter adapter = new CompAdapter(this, imageList, this);
        recyclerView.setAdapter(adapter);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Images");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for( DataSnapshot snapshot : dataSnapshot.getChildren() ) {
                    Image image = snapshot.getValue(Image.class);
                    imageList.add ( image );
                }

                adapter.setNameList(imageList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Compatibility.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });



        Button showButton = findViewById( R.id.goCompatibility );
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView male_check = findViewById(R.id.gender_male);
                TextView female_check = findViewById(R.id.gender_female);

                if ( male_check.getText().toString().equals("Male") || female_check.getText().toString().equals("Female")) {
                    Toast.makeText(Compatibility.this, "Select Zodiac", Toast.LENGTH_LONG).show();
                }

                else {
                    Intent intent = new Intent(Compatibility.this, ShowCompatibility.class );
                    intent.putExtra("MaleName", male_check.getText().toString());
                    intent.putExtra("FemaleName", female_check.getText().toString());
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
    public void ItemClicked(String s, String downloadURL) {

        if ("male".equals(currentTag)) {

            TextView nameOfZodiac = findViewById(R.id.gender_male);
            nameOfZodiac.setText(s);
            nameOfZodiac.setTextColor(Color.GRAY);
            nameOfZodiac.setTextSize(18);

            ImageView imageView = findViewById(R.id.male );
            GlideApp.with(getApplicationContext()).load(downloadURL).into(imageView);
            changeSize(imageView, 77);
        }

        else if ("female".equals(currentTag)) {
            TextView nameOfZodiac = findViewById(R.id.gender_female );
            nameOfZodiac.setText(s);
            nameOfZodiac.setTextColor(Color.GRAY);
            nameOfZodiac.setTextSize(18);

            ImageView imageView = findViewById(R.id.female );
            GlideApp.with(getApplicationContext()).load(downloadURL).into(imageView);
            changeSize(imageView, 77);

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
