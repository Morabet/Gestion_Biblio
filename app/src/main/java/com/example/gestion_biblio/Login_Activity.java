package com.example.gestion_biblio;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gestion_biblio.BIBLIOTHECAIRE.Biblio_Activity;
import com.example.gestion_biblio.Dialoge.Oublie_password_Dialoge;
import com.example.gestion_biblio.Modules.Current_User_Model;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login_Activity extends AppCompatActivity implements Oublie_password_Dialoge.Dialoge_Listener{

    public static String IP=  "192.168.43.76";  //"10.0.2.2";

    public static Current_User_Model current_user;     // setting the current user info
    Intent intent;
    EditText userName,password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Gestion de Bibliothèque");


        userName = findViewById(R.id.edT_UserName);
        password = findViewById(R.id.edT_password);

    }


///////////////////////////////////////////////

    public void onClick_connexion(View view) {
            login_Fetch();
    }

    public void onClick_oublie(View view) {

        Oublie_password_Dialoge dialoge= new Oublie_password_Dialoge();
        dialoge.show(getSupportFragmentManager(),"oublier dialog");
    }

    public void onClick_nouveau(View view) {
        Intent intent_cree = new Intent(Login_Activity.this,Inscription.class);
        startActivity(intent_cree);
    }


    //////////////////////////////////////////////////
    /////  login & getting the etudiant  infos ///////

    public void login_Fetch(){
        ProgressDialog progressDialog1 = new ProgressDialog(Login_Activity.this);
        progressDialog1.setMessage("please wait ...!");
        progressDialog1.show();
        String url ="http://"+IP+":80/php_Scripts/Gestion_biblio_scripts/login.php";

                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog1.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getString("error").equals("false")){
                                if(jsonObject.getString("user").equals("etudiant")){

                                    current_user = new Current_User_Model(jsonObject.getString("apogee"),
                                            jsonObject.getString("nom"),jsonObject.getString("prenom"),
                                            jsonObject.getString("email"),jsonObject.getString("mot_pass"),
                                            jsonObject.getString("tele"),jsonObject.getString("statut"),
                                            jsonObject.getInt("numReserver"),jsonObject.getInt("numEmprunter"));

                                    intent = new Intent(Login_Activity.this,homeEtud_Activity.class);
                                    startActivity(intent);
                                }
                                if(jsonObject.getString("user").equals("admin")){
                                    intent = new Intent(Login_Activity.this,Biblio_Activity.class);
                                    startActivity(intent);
                                }
                            }
                            else{
                                Toast.makeText(Login_Activity.this, "échec de la connexion", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Log.e("§§§§§§§§§§ TAG", "EXCEPTION :  "+e.getMessage());
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Login_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                        params.put("apogee",userName.getText().toString());
                        params.put("mot_pass",password.getText().toString());

                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(Login_Activity.this);
                requestQueue.add(request);

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void SendPassword(String apogee, String email)  {
        String url="http://"+IP+":80/php_Scripts/Gestion_biblio_scripts/send_oublie_password.php";

        ProgressDialog progressDialog = new ProgressDialog(Login_Activity.this);
        progressDialog.setMessage("please wait ...!");
        progressDialog.show();


        StringRequest request= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                    Toast.makeText(Login_Activity.this,response,Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(Login_Activity.this,"on error response",Toast.LENGTH_SHORT).show();
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
                param.put("apogee",apogee);
                param.put("email",email);
                return param;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Login_Activity.this);
        requestQueue.add(request);
    }
}