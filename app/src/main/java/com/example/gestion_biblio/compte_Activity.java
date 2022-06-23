package com.example.gestion_biblio;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gestion_biblio.Dialoge.Dialoge_1;
import com.example.gestion_biblio.Dialoge.Dialoge_2;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class compte_Activity extends AppCompatActivity implements Dialoge_1.Dialoge_1_Listener, Dialoge_2.Dialoge2_listener {
    BottomNavigationView bottomNavigationView;

    TextView apogee_compte,nom,prenom,email_compte,tele_compte;
    Button btn_modifierCompte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compte);
        getSupportActionBar().setTitle("Mon compte");
        btn_modifierCompte=findViewById(R.id.btn_Modifier_compte_etud);
        btn_modifierCompte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modiferCompte();
            }
        });

        setUpViews();

        setBottomNavigationView();
    }

    /////////////
    public void setUpViews(){
        apogee_compte = findViewById(R.id.tv_apogee_compte);
        nom = findViewById(R.id.tv_nom);
        prenom = findViewById(R.id.tv_prenom);
        email_compte = findViewById(R.id.tv_email);
        tele_compte = findViewById(R.id.tv_tele_compte);

        apogee_compte.setText(Login_Activity.current_user.getApogee());
        nom.setText(Login_Activity.current_user.getNom());
        prenom.setText(Login_Activity.current_user.getPrenom());
        email_compte.setText(Login_Activity.current_user.getEmail());
        tele_compte.setText(Login_Activity.current_user.getTele());
    }

    ///////////////////////////////
    public void modiferCompte(){
        Dialoge_1 dialoge_1= new Dialoge_1();
        dialoge_1.show(getSupportFragmentManager(),"Dialoge_1");

    }

    @Override
    public void getPassword(String currentPass) {
        if(Login_Activity.current_user.getPassword().equals(currentPass)){
            Dialoge_2 dialoge_2=new Dialoge_2();
            dialoge_2.show(getSupportFragmentManager(),"Dialoge_2");
        }
        else{
            Toast.makeText(compte_Activity.this,"mot de passe incorrect",Toast.LENGTH_SHORT).show();
        }
    }

    ////////////////////
   @Override
    public void ModifierData(String newEmail, String newPass, String newTele, String apogee) {
        String email,pass,tele;

        if(newEmail.isEmpty()) email=Login_Activity.current_user.getEmail();
        else email=newEmail;
        if(newPass.isEmpty()) pass=Login_Activity.current_user.getPassword();
        else pass=newPass;
        if(newTele.isEmpty()) tele=Login_Activity.current_user.getTele();
        else tele=newTele;

        ModifyData_DataBase(email,pass,tele,apogee);
       
       email_compte.setText(email);
       tele_compte.setText(tele);
       apogee_compte.setText(apogee);
    }

    private void ModifyData_DataBase(String email,String pass,String tele,String apogee){
        String url ="http://"+Login_Activity.IP+":80/php_Scripts/Gestion_biblio_scripts/Modifier_compteEtud.php";

        ProgressDialog progressDialog = new ProgressDialog(compte_Activity.this);
        progressDialog.setMessage("please wait ...!");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try{

                    JSONObject jsonObject= new JSONObject(response);
                    Toast.makeText(compte_Activity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(compte_Activity.this, "onResponse error", Toast.LENGTH_SHORT).show();
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

                params.put("apogee",apogee);
                params.put("mot_pass",pass);
                params.put("email",email);
                params.put("tele",tele);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(compte_Activity.this);

        requestQueue.add(request);
    }


///////////////////////////////////////////////////////////////////////////////////////////:
    //////////// setting the Bottom navigation bar ///////////
    public void setBottomNavigationView(){
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.compte_item);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.biblio_item:
                        startActivity(new Intent(getApplicationContext(),My_Biblio_Activity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.home_item:
                        startActivity(new Intent(getApplicationContext(),homeEtud_Activity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.compte_item:
                        return true;
                }
                return false;
            }
        });
    }

    //////////// setup exit my_biblio_btn_remove ///////
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_exit_item,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        if(menuItem.getItemId() == R.id.exit_btn){
            AlertDialog.Builder builder = new AlertDialog.Builder(compte_Activity.this);
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


}