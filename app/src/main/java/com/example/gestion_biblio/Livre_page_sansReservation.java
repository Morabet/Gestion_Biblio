package com.example.gestion_biblio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Livre_page_sansReservation extends AppCompatActivity {

    ImageView ivBookImage;
    TextView tvLivreTitre;
    TextView tvDiscipline;
    TextView tvDescription;
    TextView tvNumExemplaires;

    int numExemp;
    String image,title, discipline, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livre_page_sans_reservation);
        getSupportActionBar().setTitle("Explorer");
        //setting the back my_biblio_btn_remove
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        setUpViews();
    }

    //////////
    public void setUpViews(){
        ivBookImage = findViewById(R.id.ivBookImage);
        tvLivreTitre = findViewById(R.id.tvLivreTitre);
        tvDescription = findViewById(R.id.tvDescription);
        tvDiscipline = findViewById(R.id.tvDiscipline);
        tvNumExemplaires = findViewById(R.id.tvNum);


        image = getIntent().getStringExtra("IMAGE");
        title = getIntent().getStringExtra("TITRE");
        discipline = getIntent().getStringExtra("Discipline");
        description = getIntent().getStringExtra("Description");
        numExemp = getIntent().getIntExtra("NUM",0);

        Glide.with(this).load(image).into(ivBookImage);
        tvLivreTitre.setText(title);
        tvDescription.setText(description);
        tvDiscipline.setText(discipline);
        tvNumExemplaires.setText(String.valueOf(numExemp));
    }

    //////////
    // this event will enable the back
    // function to the my_biblio_btn_remove on press
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}