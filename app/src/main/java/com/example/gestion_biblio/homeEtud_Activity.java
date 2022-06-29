package com.example.gestion_biblio;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gestion_biblio.Adapters.Nested_RV_interface;
import com.example.gestion_biblio.Adapters.RecyclerView_Interface;
import com.example.gestion_biblio.Adapters.parentAdapter;
import com.example.gestion_biblio.Modules.Livre_Model;
import com.example.gestion_biblio.Modules.Livres_modelClass;
import com.example.gestion_biblio.Modules.parent_modelClass;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class homeEtud_Activity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    RecyclerView recyclerView;
    parentAdapter parent_adapter;
    ArrayList<parent_modelClass> parentList= new ArrayList<>();
    ArrayList<Livre_Model> livre_list = new ArrayList<>();     // to store fetched data from database

    public static ArrayList<Livre_Model> math_List = new ArrayList<>();
    public static ArrayList<Livre_Model> computerScience_List = new ArrayList<>();
    public static ArrayList<Livre_Model> physic_List = new ArrayList<>();
    public static ArrayList<Livre_Model> chemistry_List = new ArrayList<>();
    public static ArrayList<Livre_Model> biology_List = new ArrayList<>();
    public static ArrayList<Livre_Model> geology_List = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_etud);
        getSupportActionBar().setTitle("Etudiant");
        setBottomNavigationView();

        recyclerView = findViewById(R.id.recyclerView_parent);

        fetchData();
    }



///////////////
    public ArrayList<parent_modelClass> setLists(ArrayList<Livre_Model> list){

        for(int i=0;i<list.size();i++){

            if(list.get(i).getDiscipline().equals("mathematiques")){
                math_List.add(list.get(i));
            }

            if(list.get(i).getDiscipline().equals("physique")){
                physic_List.add(list.get(i));
            }

            if(list.get(i).getDiscipline().equals("biologie")){
                biology_List.add(list.get(i));
            }

            if(list.get(i).getDiscipline().equals("informatique")){
                computerScience_List.add(list.get(i));
            }

            if(list.get(i).getDiscipline().equals("giologie")){
                geology_List.add(list.get(i));
            }

            if(list.get(i).getDiscipline().equals("chimie")){
                chemistry_List.add(list.get(i));
            }

        }
        parentList.add(new parent_modelClass("Mathématiques",math_List));
        parentList.add(new parent_modelClass("Physique",physic_List));
        parentList.add(new parent_modelClass("Giologie",geology_List));
        parentList.add(new parent_modelClass("Biologie",biology_List));
        parentList.add(new parent_modelClass("Informatique",computerScience_List));
        parentList.add(new parent_modelClass("Chimie",chemistry_List));

        return parentList;
    }

    ///////////////
    public void fetchData(){
        ProgressDialog progressDialog = new ProgressDialog(homeEtud_Activity.this);
        progressDialog.setMessage("please wait ...!");
        progressDialog.show();

        String url ="http://"+Login_Activity.IP+":80/php_Scripts/Gestion_biblio_scripts/fetch_books_2.php";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try{

                    JSONArray all_books =new JSONArray(response);

                    for(int i=0;i<all_books.length();i++){

                        JSONObject book = all_books.getJSONObject(i);
                        String imageName= book.getString("image");
                        String image_url= "http://"+Login_Activity.IP+"/php_Scripts/Gestion_biblio_scripts/livresCover/"+imageName;

                        livre_list.add(new Livre_Model(
                                book.getInt("id_livre"),
                                image_url,
                                book.getString("titre"),
                                book.getString("discipline"),
                                book.getString("description"),
                                book.getString("auteur"),
                                book.getString("disponible"),
                                book.getInt("num_exemplaire")
                        ));
                    }

                    parent_adapter= new parentAdapter(homeEtud_Activity.this,setLists(livre_list), recyclerViewInterface);
                    recyclerView.setLayoutManager(new LinearLayoutManager(homeEtud_Activity.this));
                    recyclerView.setAdapter(parent_adapter);
                    parent_adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(homeEtud_Activity.this,"on Response Eror",Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(request);
    }


    ////////////// item clicked ////////////////
    Nested_RV_interface recyclerViewInterface = new Nested_RV_interface() {
        @Override
        public void onItemClicked(View view, Livre_Model livre) {

            sendData(livre);
        }
    };


    ///////////////////////////////////////////
    public void sendData(Livre_Model livre){
        Intent intent = new Intent(homeEtud_Activity.this, Livre_page.class);

        intent.putExtra("IMAGE", livre.getImageCover());
        intent.putExtra("ID", livre.getId_livre());
        intent.putExtra("TITRE",livre.getTitle());
        intent.putExtra("Discipline", livre.getDiscipline());
        intent.putExtra("Description",livre.getDescription());
        intent.putExtra("Auteur",livre.getAuteur());
        intent.putExtra("Disponible",livre.getDisponible());
        intent.putExtra("NUM", livre.getNum_exemplaire());

        startActivity(intent);
    }

    //////////// setup exit my_biblio_btn_remove /////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_exit_item,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        if(menuItem.getItemId() == R.id.exit_btn){
            AlertDialog.Builder builder = new AlertDialog.Builder(homeEtud_Activity.this);
            builder.setMessage("voulez-vous quitter ?");
            builder.setCancelable(true);

            builder.setNegativeButton("OUI", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    moveTaskToBack(true);
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(0);
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
        return true;
    }

    //////////// setting the Bottom navigation bar ///////////
    public void setBottomNavigationView(){
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.home_item);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.biblio_item:
                        startActivity(new Intent(getApplicationContext(),My_Biblio_Activity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.compte_item:
                        startActivity(new Intent(getApplicationContext(),compte_Activity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.home_item:
                        return true;
                }
                return false;
            }
        });
    }
}