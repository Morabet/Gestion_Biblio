package com.example.gestion_biblio.BIBLIOTHECAIRE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gestion_biblio.BIBLIOTHECAIRE.Adapters.Confirmer_Emprunt_Interface;
import com.example.gestion_biblio.BIBLIOTHECAIRE.Adapters.Etud_reserver_info_Adapter;
import com.example.gestion_biblio.BIBLIOTHECAIRE.Adapters.reservation_Adapter;
import com.example.gestion_biblio.Login_Activity;
import com.example.gestion_biblio.Modules.LivreReserver_Model;
import com.example.gestion_biblio.Modules.Livre_Model;
import com.example.gestion_biblio.Modules.Livres_modelClass;
import com.example.gestion_biblio.Modules.User_modelClass;
import com.example.gestion_biblio.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class reservation_etud_page extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<LivreReserver_Model> livre_reserver= new ArrayList<>();
    Etud_reserver_info_Adapter adapter;

    TextView tv_apogee_reserver,tv_nom_reserver,tv_prenom_reserver;
    String apogee,nom,prenom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_etud_page);
        getSupportActionBar().setTitle("Administrateur");
        //setting the back my_biblio_btn_remove
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        setUpViews();
        setUpList();
    }

    ///// setUp views /////////
    public void setUpViews(){

        tv_apogee_reserver =findViewById(R.id.tv_apogee_reserver);
        tv_nom_reserver = findViewById(R.id.tv_nom_reserver);
        tv_prenom_reserver = findViewById(R.id.tv_prenom_reserver);
        recyclerView = findViewById(R.id.rv_livreReserver_reserver);

        apogee = getIntent().getStringExtra("Apogee");
        nom = getIntent().getStringExtra("Nom");
        prenom = getIntent().getStringExtra("Prenom");

        tv_apogee_reserver.setText(apogee);
        tv_nom_reserver.setText(nom);
        tv_prenom_reserver.setText(prenom);

    }

    ////// setUp list///

    public void setUpList(){

        ProgressDialog progressDialog = new ProgressDialog(reservation_etud_page.this);
        progressDialog.setMessage("Please wait ...");
        progressDialog.show();
        String url ="http://"+ Login_Activity.IP +":80/php_Scripts/Gestion_biblio_scripts/fetch_books%20_reserved_Biblio.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);
                        livre_reserver.add(new LivreReserver_Model(
                                object.getInt("idR"),
                                object.getString("titre")
                        ));

                        adapter = new Etud_reserver_info_Adapter(reservation_etud_page.this,livre_reserver,confirmer_emprunt_interface);
                        recyclerView.setLayoutManager(new LinearLayoutManager(reservation_etud_page.this));
                        recyclerView.setAdapter(adapter);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(reservation_etud_page.this, "on error response", Toast.LENGTH_SHORT).show();
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
                params.put("apogee",String.valueOf(apogee));
                return params;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }
    Confirmer_Emprunt_Interface confirmer_emprunt_interface = new Confirmer_Emprunt_Interface() {
        @Override
        public void OnClick_ConfirmerEmprunt(int position) {
            ProgressDialog pr = new ProgressDialog(reservation_etud_page.this);
            pr.setMessage("Please wait ...");
            pr.show();

            String url = "http://"+Login_Activity.IP+":80/php_Scripts/Gestion_biblio_scripts/confirmerEmprunt_biblio.php";

            StringRequest rq= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    pr.dismiss();
                    try {
                        JSONObject object= new JSONObject(response);

                        if(object.getString("error").equals("false")){
                            Toast.makeText(reservation_etud_page.this,object.getString("message"),Toast.LENGTH_LONG).show();
                            livre_reserver.remove(position);
                            adapter = new Etud_reserver_info_Adapter(reservation_etud_page.this,livre_reserver,confirmer_emprunt_interface);
                            recyclerView.setLayoutManager(new LinearLayoutManager(reservation_etud_page.this));
                            recyclerView.setAdapter(adapter);
                        }
                        else Toast.makeText(reservation_etud_page.this,"la confirmation a échoué",Toast.LENGTH_LONG).show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                        Toast.makeText(reservation_etud_page.this,"on error response",Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                public String getBodyContentType() {
                    return "application/x-www-form-urlencoded; charset=UTF-8";
                }

                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> param= new HashMap<>();

                    param.put("idReservation",String.valueOf(livre_reserver.get(position).getId_reservation()));
                    return param;
                }
            };
            Volley.newRequestQueue(reservation_etud_page.this).add(rq);
        }
    };

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // this event will enable the back
    // function to the my_biblio_btn_remove on press
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.exit_btn:
        }
        return super.onOptionsItemSelected(item);
    }
}