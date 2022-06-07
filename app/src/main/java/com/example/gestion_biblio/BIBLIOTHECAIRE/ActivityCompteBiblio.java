package com.example.gestion_biblio.BIBLIOTHECAIRE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.gestion_biblio.R;

public class ActivityCompteBiblio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compte_biblio);
        getSupportActionBar().setTitle("Compte");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }







   @Override
   public boolean onSupportNavigateUp() {
       onBackPressed();
       return true;
   }
}