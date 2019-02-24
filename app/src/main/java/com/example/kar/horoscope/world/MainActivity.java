package com.example.kar.horoscope.world;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ActionBarDrawerToggle toggle;

    RecyclerView recyclerView;
    private RecyclerAdapter adapter;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        DrawerLayout drawerLayout = findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle( this, drawerLayout, R.string.open, R.string.close );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        ///Set Header Parameters
        NavigationView navigationView = findViewById(R.id.nav_view);
        View view = navigationView.getHeaderView(0);


        ImageView profilePic = view.findViewById(R.id.user_pic);
        TextView userName = view.findViewById(R.id.user_name);
        TextView userEmail = view.findViewById(R.id.user_email);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        assert user != null;
        GlideApp.with(profilePic.getContext()).load(user.getPhotoUrl()).into(profilePic);
        userName.setText(user.getDisplayName());
        userEmail.setText(user.getEmail());




        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if ( id == R.id.exit) {
                    AuthUI.getInstance()
                            .signOut(MainActivity.this)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this );
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putInt("Name", -1 );
                                    editor.apply();

                                    Intent intent = new Intent(MainActivity.this, LogIn.class );
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                                    finish();
                                }
                            });
                }

                if ( id == R.id.compatibility ) {
                    Intent intent = new Intent( MainActivity.this, Compatibility.class );
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }

                if ( id == R.id.favourite ) {
                    Intent intent = new Intent( MainActivity.this, SetUserZodiac.class );
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                }

                if ( id == R.id.write ) { SendMail(); }

                if ( id == R.id.feedback ) { SendFeedback(); }

                /*if ( id == R.id.language ) {
                    Intent intent = new Intent(MainActivity.this, SetLanguage.class );
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                }*/

                if ( id == R.id.about ) {
                    Intent intent = new Intent( MainActivity.this, About.class );
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }

                return true;
            }
        });

        String Names[] = getResources().getStringArray(R.array.Zodiacs);

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


        int col = 3;
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, col ));
        adapter = new RecyclerAdapter(this, Names, Images );
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item ) {
        if ( toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("IntentReset")
    private void SendMail() {

        Uri uri = Uri.parse("mailto:karenmirakyan@gmail.com");

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, uri );
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");


        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            Log.i("Finished sending", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private void SendFeedback() {
        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, " unable to find market app", Toast.LENGTH_LONG).show();
        }
    }
}
