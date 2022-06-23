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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gestion_biblio.BIBLIOTHECAIRE.Adapters.BookEmprunterAdapter;
import com.example.gestion_biblio.BIBLIOTHECAIRE.Adapters.OnClick_ConfirmeRetour;
import com.example.gestion_biblio.Login_Activity;
import com.example.gestion_biblio.Modules.LivreReserver_Model;
import com.example.gestion_biblio.Modules.Livre_EmprunterModel;
import com.example.gestion_biblio.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConfirmerRetour_LivresEmprunter extends AppCompatActivity {

    RecyclerView recyclerView;
    BookEmprunterAdapter adapter;
    ArrayList<Livre_EmprunterModel> livre_emprunter= new ArrayList<>();

    TextView tv_apogee_emprunter,tv_nom_emprunter,tv_prenom_emprunter;
    String apogee,nom,prenom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmer_retour_livres_emprunter);
        getSupportActionBar().setTitle("Administrateur");
        //setting the back my_biblio_btn_remove
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        setUpViews();
        setUpList();
    }

    private void setUpViews() {
        tv_apogee_emprunter =findViewById(R.id.tv_apogee_emprunter);
        tv_nom_emprunter = findViewById(R.id.tv_nom_emprunter);
        tv_prenom_emprunter = findViewById(R.id.tv_prenom_emprunter);
        recyclerView = findViewById(R.id.rv_livreEmprunter_emprunter);

        apogee = getIntent().getStringExtra("Apogee");
        nom = getIntent().getStringExtra("Nom");
        prenom = getIntent().getStringExtra("Prenom");

        tv_apogee_emprunter.setText(apogee);
        tv_nom_emprunter.setText(nom);
        tv_prenom_emprunter.setText(prenom);
    }

    private void setUpList() {
        String url = "http://"+ Login_Activity.IP +":80/php_Scripts/Gestion_biblio_scripts/fetch_book_emprunter_parEtud.php";

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait ...");
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(ConfirmerRetour_LivresEmprunter.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i<array.length(); i++){

                                JSONObject object = array.getJSONObject(i);
                                livre_emprunter.add(new Livre_EmprunterModel(
                                        object.getInt("id_emprunt"),
                                        object.getString("titre")
                                ));

                            }

                            adapter = new BookEmprunterAdapter(ConfirmerRetour_LivresEmprunter.this,livre_emprunter,onClick_confirmeRetour);
                            recyclerView.setLayoutManager(new LinearLayoutManager(ConfirmerRetour_LivresEmprunter.this));
                            recyclerView.setAdapter(adapter);


                        }catch (Exception e){

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ConfirmerRetour_LivresEmprunter.this, "on error response", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params= new HashMap<>();
                params.put("etud_apogee",apogee);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    OnClick_ConfirmeRetour onClick_confirmeRetour= new OnClick_ConfirmeRetour() {
        @Override
        public void OnClickConfirmeRetour(int position) {

            String url1= "http://"+Login_Activity.IP+"/php_Scripts/Gestion_biblio_scripts/ConfirmerRetour_livre.php";

            RequestQueue requestQueue = Volley.newRequestQueue(ConfirmerRetour_LivresEmprunter.this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Toast.makeText(ConfirmerRetour_LivresEmprunter.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        livre_emprunter.remove(position);
                        adapter = new BookEmprunterAdapter(ConfirmerRetour_LivresEmprunter.this,livre_emprunter,onClick_confirmeRetour);
                        recyclerView.setLayoutManager(new LinearLayoutManager(ConfirmerRetour_LivresEmprunter.this));
                        recyclerView.setAdapter(adapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ConfirmerRetour_LivresEmprunter.this, "Fail to get the data..", Toast.LENGTH_SHORT).show();
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put("etud_apogee",apogee);
                    params.put("id_emprunt",String.valueOf(livre_emprunter.get(position).getId_emprunt()));

                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }
    };
    ////////////////////////////////////////////////////////////////////



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