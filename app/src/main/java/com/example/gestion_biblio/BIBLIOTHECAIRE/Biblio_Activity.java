package com.example.gestion_biblio.BIBLIOTHECAIRE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.gestion_biblio.BIBLIOTHECAIRE.Adapters.ViewPager_Adapter;
import com.example.gestion_biblio.BIBLIOTHECAIRE.Fragments.Ajouter_livre;
import com.example.gestion_biblio.BIBLIOTHECAIRE.Fragments.Etud_Emprunter;
import com.example.gestion_biblio.BIBLIOTHECAIRE.Fragments.Etud_suspendu;
import com.example.gestion_biblio.BIBLIOTHECAIRE.Fragments.liste_livre_fragment;
import com.example.gestion_biblio.BIBLIOTHECAIRE.Fragments.reservation;
import com.example.gestion_biblio.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class Biblio_Activity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biblio);
        getSupportActionBar().setTitle("Administrateur");

        setViewPager2();
    }

    public void setViewPager2() {

        tabLayout = findViewById(R.id.id_tab);

        viewPager = findViewById(R.id.id_viewPager);

        ViewPager_Adapter viewPager_adapter = new ViewPager_Adapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        viewPager.setAdapter(viewPager_adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(4).setIcon(R.drawable.ic_add_24);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pos= tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    ////////////////////////////////////////


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.search_btn){
            SearchView searchView = (SearchView) item.getActionView();
            if(pos==1){
                searchView.setQueryHint("rechercher par apogée ...");
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        return false;
                    }
                    @Override
                    public boolean onQueryTextChange(String s) {
                        reservation.filter(s);
                        return false;
                    }
                });
            }

            else if(pos==2){
                searchView.setQueryHint("rechercher par apogée...");
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        return false;
                    }
                    @Override
                    public boolean onQueryTextChange(String s) {
                        Etud_Emprunter.filter(s);
                        return false;
                    }
                });
            }

            else if(pos==3){
                searchView.setQueryHint("rechercher par apogée...");
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        return false;
                    }
                    @Override
                    public boolean onQueryTextChange(String s) {
                        Etud_suspendu.filter(s);
                        return false;
                    }
                });
            }

            else {
                searchView.setQueryHint("rechercher par titre...");
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        return false;
                    }
                    @Override
                    public boolean onQueryTextChange(String s) {
                        liste_livre_fragment.filter(s);
                        return false;
                    }
                });
            }
        }
        if(item.getItemId() == R.id.profil){
            Intent intent = new Intent(Biblio_Activity.this,ActivityCompteBiblio.class);
            startActivity(intent);
        }

        if(item.getItemId() == R.id.logout){
            AlertDialog.Builder builder = new AlertDialog.Builder(Biblio_Activity.this);
            builder.setMessage("voulez-vous quitter ?");
            builder.setCancelable(true);
            builder.setNegativeButton("OUI", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    moveTaskToBack(true);
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(1);
                }
            });
            builder.setPositiveButton("NON", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

}