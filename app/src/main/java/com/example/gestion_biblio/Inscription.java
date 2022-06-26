package com.example.gestion_biblio;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class Inscription extends AppCompatActivity {

    EditText edtnom,edtprenom,edtapogee,edtemail,edttel,edtpassword1,edtpassword2;
    String nom,prenom,apogee,email,tele,pass1,pass2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        getSupportActionBar().setTitle("Inscription");
        //setting the back my_biblio_btn_remove
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        setUpViews();

    }
    //////


    //////////////
    public  void setUpViews(){

        edtnom = findViewById(R.id.editnom);
        edtprenom = findViewById(R.id.editprenom);
        edtapogee = findViewById(R.id.editapogee);
        edtemail = findViewById(R.id.editemail);
        edttel = findViewById(R.id.edittel);
        edtpassword1 = findViewById(R.id.editpassword1);
        edtpassword2 = findViewById(R.id.editpassword2);

    }

    /////////////////
    public void deja_btn(View view) {
        Intent intent_retour = new Intent(Inscription.this,Login_Activity.class);
        startActivity(intent_retour);
    }

    ////////////////////////
    public void onClick_enregistrer(View view) {

        nom = edtnom.getText().toString();
        prenom = edtprenom.getText().toString();
        apogee = edtapogee.getText().toString();
        email = edtemail.getText().toString();
        tele = edttel.getText().toString();
        pass1 = edtpassword1.getText().toString();
        pass2 = edtpassword2.getText().toString();

        Log.e("§§§§§§§§§§ TAG", "nom " +nom);
        Log.e("§§§§§§§§§§ TAG", "prenom " +prenom);
        Log.e("§§§§§§§§§§ TAG", "apogee " +apogee);
        Log.e("§§§§§§§§§§ TAG", "email " +email);
        Log.e("§§§§§§§§§§ TAG", "pass1 " +pass1);
        Log.e("§§§§§§§§§§ TAG", "pass2 " +pass2);

        if(TextUtils.isEmpty(nom))
            edtnom.setError("nécessaire");

        else if(TextUtils.isEmpty(prenom))
            edtprenom.setError("nécessaire");

        else if(TextUtils.isEmpty(apogee))
            edtapogee.setError("nécessaire");


        else if(TextUtils.isEmpty(email))
            edtemail.setError("nécessaire");

        else if(TextUtils.isEmpty(pass1))
            edtpassword1.setError("nécessaire");

        else if(TextUtils.isEmpty(pass2))
            edtpassword2.setError("nécessaire");

        else if(!pass1.equals(pass2)){
            edtpassword1.setError("*");
            edtpassword2.setError("*");
        }

        else addData();
    }

    /////////////////
    public  void addData(){

        String url ="http://"+Login_Activity.IP+"/php_Scripts/Gestion_biblio_scripts/Inscription.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("§§§§§§§§§§ TAG", "RESPONSE IS " + response);

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    Toast.makeText(Inscription.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                     Log.e("§§§§§§§§§§ TAG2", "Exception §§§§§§§");
                    e.printStackTrace();
                }

                edtnom.setText("");
                edtprenom.setText("");
                edtapogee.setText("");
                edtemail.setText("");
                edttel.setText("");
                edtpassword1.setText("");
                edtpassword2.setText("");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Inscription.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            public String getBodyContentType() {
                // as we are passing data in the form of url encoded
                // so we are passing the content type below
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();

                // on below line we are passing our
                // key and value pair to our parameters.

                params.put("nom",nom);
                params.put("prenom",prenom);
                params.put("apogee",apogee);
                params.put("email",email);
                params.put("tele",tele);
                params.put("mot_pass",pass1);


                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(Inscription.this);

        requestQueue.add(request);
    }


    //// return bar ///////////
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){

        if(menuItem.getItemId() == android.R.id.home)
            this.finish();
            return true;
        }

}