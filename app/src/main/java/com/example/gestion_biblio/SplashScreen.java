package com.example.gestion_biblio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();

        Intent intent= new Intent(SplashScreen.this, Login_Activity.class);

        Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
            }
        },3000);
    }
}