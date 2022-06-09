package com.example.gestion_biblio.BIBLIOTHECAIRE;


import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

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
import com.example.gestion_biblio.Dialoge.Dialoge_3;
import com.example.gestion_biblio.Login_Activity;
import com.example.gestion_biblio.R;
import com.example.gestion_biblio.compte_Activity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ActivityCompteBiblio extends AppCompatActivity implements Dialoge_1.Dialoge_1_Listener, Dialoge_3.Dialoge3_listener {
    TextView nom_admin,password_admin,email_admin;
    Button btn_modifier_compte_admin;
    public String url = "http://"+Login_Activity.IP+"/php_Scripts/Gestion_biblio_scripts/fetch_admin.php";
    public String url1 ="http://"+Login_Activity.IP+":80/php_Scripts/Gestion_biblio_scripts/Modifier_compte_admin.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compte_biblio);

        getSupportActionBar().setTitle("Compte");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        nom_admin=findViewById(R.id.tv_nom_admin);
        email_admin=findViewById(R.id.tv_email_admin);
        password_admin=findViewById(R.id.tv_pass_admin);
        btn_modifier_compte_admin=findViewById(R.id.btn_Modifier_compte_admin);
        btn_modifier_compte_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modiferCompte();
            }
        });


        fetchdata();
    }
    public void modiferCompte(){
        Dialoge_1 dialoge_1 = new Dialoge_1();
        dialoge_1.show(getSupportFragmentManager(),"Dialoge 1");
    }
    @Override
    public void getPassword(String currentPass) {
        if(password_admin.getText().equals(currentPass)){
            Dialoge_3 dialoge_3=new Dialoge_3();
            dialoge_3.show(getSupportFragmentManager(),"Dialoge_3");
        }
        else{
            Toast.makeText(ActivityCompteBiblio.this,"mot de passe incorrect",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void modifierdata(String newNom, String newEmail, String newPassword) {
        String nom,email,password;
        if(newNom.isEmpty())
            nom = nom_admin.getText().toString();
        else
            nom = newNom;
        if(newEmail.isEmpty())
            email = email_admin.getText().toString();
        else
            email = newEmail;

        if(newPassword.isEmpty())
            password = password_admin.getText().toString();
        else
            password = newPassword;

        modifierdata_DataBase(nom , email , password);
    }

    private void modifierdata_DataBase(String nom, String email, String password) {
        ProgressDialog progressDialog = new ProgressDialog(ActivityCompteBiblio.this);
        progressDialog.setMessage("please wait ...!");
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(ActivityCompteBiblio.this);
        StringRequest request = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try{
                    JSONObject jsonObject= new JSONObject(response);
                    Toast.makeText(ActivityCompteBiblio.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ActivityCompteBiblio.this, "onResponse error", Toast.LENGTH_SHORT).show();
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

                params.put("nom",nom);
                params.put("email",email);
                params.put("password",password);

                return params;
            }
        };
        requestQueue.add(request);
    }

    private void fetchdata() {
        ProgressDialog progressDialog = new ProgressDialog(ActivityCompteBiblio.this);
        progressDialog.setMessage("Please wait ...");
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(ActivityCompteBiblio.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            nom_admin.setText(jsonObject.getString("nom"));
                            password_admin.setText(jsonObject.getString("password"));
                            email_admin.setText(jsonObject.getString("email"));

                            if(email_admin.getText().equals("null")){
                                email_admin.setText("Merci de modifier votre compte et saiser votre email");
                                email_admin.setTextColor(Color.parseColor("#FF0000"));
                                email_admin.setTextSize(12);
                            }

                        }catch (Exception e){
                            Log.e("tttttttttt","BookLislllllllt "+e.getMessage());

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ActivityCompteBiblio.this, "Fail to get the data..", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }

   @Override
   public boolean onSupportNavigateUp() {
       onBackPressed();
       return true;
   }




}