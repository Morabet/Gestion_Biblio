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
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gestion_biblio.Adapters.My_Biblio_Adapter;
import com.example.gestion_biblio.Adapters.My_Biblio_Adapter_E;
import com.example.gestion_biblio.Adapters.RecyclerView_Interface;
import com.example.gestion_biblio.Adapters.RecyclerView_Interface_2;
import com.example.gestion_biblio.Adapters.RemoveReservation_interface;
import com.example.gestion_biblio.Modules.Livre_Model;
import com.example.gestion_biblio.Modules.Livres_modelClass;
import com.example.gestion_biblio.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class My_Biblio_Activity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    ArrayList<Livre_Model> livres_reserver = new ArrayList<>();
    ArrayList<Livre_Model> livres_emprunter = new ArrayList<>();

    RecyclerView rv_livres_reserver,rv_livres_emprunter;
    My_Biblio_Adapter adapterR;
    My_Biblio_Adapter_E adapterE;
    ProgressDialog progressDialog;
    TextView tv_livre_reserver,tv_num_livreR,tv_livre_emprunter,tv_num_livreE;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_biblio);
        getSupportActionBar().setTitle("Ma bibliothéque");
        setBottomNavigationView();

        tv_num_livreR = findViewById(R.id.tv_num_livreR);
        tv_num_livreE= findViewById(R.id.tv_num_livreE);
        rv_livres_reserver = findViewById(R.id.rv_livres_reserver);
        rv_livres_emprunter = findViewById(R.id.rv_livres_emprunter);

        requestQueue = Volley.newRequestQueue(this);

        progressDialog = new ProgressDialog(My_Biblio_Activity.this);
        progressDialog.setMessage("please wait ...!");
        progressDialog.show();

        setLivresReserver();
        
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void setLivresReserver(){

        String url= "http://"+Login_Activity.IP+":80/php_Scripts/Gestion_biblio_scripts/get_livresReserver_par_etud.php";

                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {

                            JSONArray all_books = new JSONArray(response);
                            for (int i = 0; i < all_books.length(); i++) {

                                JSONObject book = all_books.getJSONObject(i);
                                String imageName = book.getString("image");
                                String image_url = "http://" + Login_Activity.IP + "/php_Scripts/Gestion_biblio_scripts/livresCover/" + imageName;

                                livres_reserver.add(new Livre_Model(
                                        book.getInt("id_reservation") ,
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
                            tv_num_livreR.setText(String.valueOf(livres_reserver.size()));
                            adapterR = new My_Biblio_Adapter(removeReservation_interface,My_Biblio_Activity.this,livres_reserver,recyclerViewInterface);
                            rv_livres_reserver.setLayoutManager(new LinearLayoutManager(My_Biblio_Activity.this));
                            rv_livres_reserver.setAdapter(adapterR);

                            setLivresEmprunter();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }}, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(My_Biblio_Activity.this, "on error response", Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    public String getBodyContentType() {
                        return "application/x-www-form-urlencoded; charset=UTF-8";
                    }

                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params= new HashMap<>();
                        params.put("etud_apogee",Login_Activity.current_user.getApogee());
                        return params;
                    }
                };
                requestQueue.add(request);
    }

/////////////////////////////////////////////////

    RemoveReservation_interface removeReservation_interface = new RemoveReservation_interface() {
        @Override
        public void OnClick_removeReservation(int position) {

            ProgressDialog pr= new ProgressDialog(My_Biblio_Activity.this);
            pr.setTitle("please wait ...");
            pr.show();
            String url2= "http://"+Login_Activity.IP+":80/php_Scripts/Gestion_biblio_scripts/remove_reservation.php";

            StringRequest stringRequest= new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    pr.dismiss();
                    try {
                        JSONObject jsonObject= new JSONObject(response);
                        if(jsonObject.getString("error").equals("false")){
                            Toast.makeText(My_Biblio_Activity.this,"reservation est suprimé",Toast.LENGTH_LONG).show();
                            //setLivresReserver();
                            livres_reserver.remove(position);
                            tv_num_livreR.setText(String.valueOf(livres_reserver.size()));
                            adapterR = new My_Biblio_Adapter(removeReservation_interface,My_Biblio_Activity.this,livres_reserver,recyclerViewInterface);
                            rv_livres_reserver.setLayoutManager(new LinearLayoutManager(My_Biblio_Activity.this));
                            rv_livres_reserver.setAdapter(adapterR);

                        }
                        else
                            Toast.makeText(My_Biblio_Activity.this, "error", Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("§§§§§§ RESPONSE","on Error Response 2");
                }
            }){

                @Override
                public String getBodyContentType() {
                    return "application/x-www-form-urlencoded; charset=UTF-8";
                }

                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();

                    params.put("id_reservation",String.valueOf(livres_reserver.get(position).getId_reservation()));
                    params.put("apogee",Login_Activity.current_user.getApogee());
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }
    };

//////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void setLivresEmprunter(){
    ProgressDialog prr= new ProgressDialog(My_Biblio_Activity.this);
    prr.setTitle("please wait ...");
    prr.show();

    String url3="http://"+Login_Activity.IP+":80/php_Scripts/Gestion_biblio_scripts/get_livreEmprunter_par_etud.php";
    StringRequest stringRequest2= new StringRequest(Request.Method.POST, url3, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            prr.dismiss();

            JSONArray all_books2 ;
            try {
                all_books2 = new JSONArray(response);

            for (int i = 0; i < all_books2.length(); i++) {

                JSONObject book2 = all_books2.getJSONObject(i);
                String imageName = book2.getString("image");
                String image_url = "http://" + Login_Activity.IP + "/php_Scripts/Gestion_biblio_scripts/livresCover/" + imageName;

                livres_emprunter.add(new Livre_Model(
                        book2.getInt("id_livre"),
                        image_url,
                        book2.getString("titre"),
                        book2.getString("discipline"),
                        book2.getString("description"),
                        book2.getString("auteur"),
                        book2.getString("disponible"),
                        book2.getInt("num_exemplaire")
                ));
            }

            tv_num_livreE.setText(String.valueOf(livres_emprunter.size()));
                 adapterE = new My_Biblio_Adapter_E(My_Biblio_Activity.this,livres_emprunter,recyclerViewInterface2);
                rv_livres_emprunter.setAdapter(adapterE);
                rv_livres_emprunter.setLayoutManager(new LinearLayoutManager(My_Biblio_Activity.this));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("§§§§§§ RESPONSE","on Error Response 3");
        }
    }){
        @Override
        public String getBodyContentType() {
            return "application/x-www-form-urlencoded; charset=UTF-8";
        }

        @Nullable
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String,String> params= new HashMap<>();
            params.put("apogee",Login_Activity.current_user.getApogee());
            return params;
        }
    };
    requestQueue.add(stringRequest2);
}

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    RecyclerView_Interface recyclerViewInterface= new RecyclerView_Interface() {
        @Override
        public void onItemClick(View view, int position) {
            Intent intent = new Intent(My_Biblio_Activity.this, Livre_page_sansReservation.class);

            intent.putExtra("ID",livres_reserver.get(position).getId_livre());
            intent.putExtra("IMAGE", livres_reserver.get(position).getImageCover());
            intent.putExtra("TITRE", livres_reserver.get(position).getTitle());
            intent.putExtra("AUTEUR",livres_reserver.get(position).getAuteur());
            intent.putExtra("Discipline", livres_reserver.get(position).getDiscipline());
            intent.putExtra("Description", livres_reserver.get(position).getDescription());
            intent.putExtra("NUM", livres_reserver.get(position).getNum_exemplaire());

            startActivity(intent);
        }
    };

    /////////////////////////////////////////////
    RecyclerView_Interface_2 recyclerViewInterface2= new RecyclerView_Interface_2() {
        @Override
        public void onItemClick2(View view, int position) {
            Intent intent = new Intent(My_Biblio_Activity.this, Livre_page_sansReservation.class);

            intent.putExtra("IMAGE", livres_emprunter.get(position).getImageCover());
            intent.putExtra("TITRE", livres_emprunter.get(position).getTitle());
            intent.putExtra("AUTEUR",livres_emprunter.get(position).getAuteur());
            intent.putExtra("Discipline", livres_emprunter.get(position).getDiscipline());
            intent.putExtra("Description", livres_emprunter.get(position).getDescription());
            intent.putExtra("NUM", livres_emprunter.get(position).getNum_exemplaire());

            startActivity(intent);
        }
    };

//////////////////// setup exit my_biblio_btn_remove /////////////////////////////////////////////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_exit_item,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        if(menuItem.getItemId() == R.id.exit_btn){
            AlertDialog.Builder builder = new AlertDialog.Builder(My_Biblio_Activity.this);
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
        return true;
    }
    /////////////////////

    //////////
    public void setBottomNavigationView(){
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.biblio_item);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.home_item:
                        startActivity(new Intent(getApplicationContext(),homeEtud_Activity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.compte_item:
                        startActivity(new Intent(getApplicationContext(),compte_Activity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.biblio_item:
                        return true;
                }
                return false;
            }
        });
    }

    ////////
}