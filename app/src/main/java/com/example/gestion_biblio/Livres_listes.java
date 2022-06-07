package com.example.gestion_biblio;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.appcompat.widget.SearchView;

import com.example.gestion_biblio.Adapters.Livres_liste_Adapter;
import com.example.gestion_biblio.Adapters.RecyclerView_Interface;
import com.example.gestion_biblio.Modules.Livre_Model;

import java.util.ArrayList;

public class Livres_listes extends AppCompatActivity implements RecyclerView_Interface {

    ArrayList<Livre_Model> livres_liste = new ArrayList<>();
    RecyclerView rv_livres_liste;
    Livres_liste_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livres_listes);
        getSupportActionBar().setTitle("Explorer");
        //setting the back my_biblio_btn_remove
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        rv_livres_liste = findViewById(R.id.rv_livres_liste);

        adapter =new Livres_liste_Adapter(Livres_listes.this,setBookList(livres_liste),this);

        rv_livres_liste.setAdapter(adapter);
        rv_livres_liste.setLayoutManager(new GridLayoutManager(this,3));
    }


    ////////////////
    @Override
    public void onItemClick(View view, int position) {

        Intent intent = new Intent(Livres_listes.this,Livre_page.class);

        intent.putExtra("IMAGE",livres_liste.get(position).getImageCover());
        intent.putExtra("ID", livres_liste.get(position).getId_livre());
        intent.putExtra("TITRE",livres_liste.get(position).getTitle());
        intent.putExtra("Discipline",livres_liste.get(position).getDiscipline());
        intent.putExtra("Description",livres_liste.get(position).getDescription());
        intent.putExtra("Auteur",livres_liste.get(position).getAuteur());
        intent.putExtra("Disponible",livres_liste.get(position).getDisponible());
        intent.putExtra("NUM",livres_liste.get(position).getNum_exemplaire());

        startActivity(intent);
    }

    ///////////////
    public ArrayList<Livre_Model> setBookList(ArrayList<Livre_Model> list){
        int size,numEx,id;
        String image,title,auteur,discipline,description,disponible;

        size = getIntent().getIntExtra("livre_list_size",0);

        for(int i=0;i<size;i++){

            image = getIntent().getStringExtra("livre_list_Image"+i);
            id = getIntent().getIntExtra("livre_list_Id"+i,0);
            numEx = getIntent().getIntExtra("livre_list_numEx"+i,0);
            title = getIntent().getStringExtra("livre_list_title"+i);
            auteur = getIntent().getStringExtra("livre_list_auteur"+i);
            discipline = getIntent().getStringExtra("livre_list_discipline"+i);
            description = getIntent().getStringExtra("livre_list_description"+i);
            disponible = getIntent().getStringExtra("livre_list_disponible"+i);

            list.add(new Livre_Model(id,image,title,discipline,description,auteur,disponible,numEx));
        }
        return list;
    }

    ///////////////


    //////////// setup  return __ search btns /////////////////////////////////////////////

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.search_item,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){

        if(menuItem.getItemId() == android.R.id.home){
            this.finish();
            return true;
        }

        if(menuItem.getItemId() == R.id.search_btn){

            SearchView searchView = (SearchView) menuItem.getActionView();
            searchView.setQueryHint("rechercher:");

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {

                    adapter.getFilter().filter(s);
                    return false;
                }
            });
        }
        return super.onOptionsItemSelected(menuItem);
    }
}