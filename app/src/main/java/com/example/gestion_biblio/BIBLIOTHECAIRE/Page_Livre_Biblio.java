package com.example.gestion_biblio.BIBLIOTHECAIRE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.gestion_biblio.Login_Activity;
import com.example.gestion_biblio.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Page_Livre_Biblio extends AppCompatActivity {

    TextView tvTitre,tvDiscipline,tv_auteur,tvDiscription,tvNumExemp;
    ImageView ivCover;

    int idLivre,numExemp;
    String image,title,auteur, discipline, description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_livre_biblio);
        getSupportActionBar().setTitle("Administrateur");
        //setting the back my_biblio_btn_remove
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        setUpViews();
    }

    ///////// setUP Views ///////
    public void setUpViews(){

        tvTitre = findViewById(R.id.tv_titre_biblio);
        tvDiscipline = findViewById(R.id.tv_discipline_biblio);
        tvDiscription = findViewById(R.id.tv_description_biblio);
        tvNumExemp = findViewById(R.id.tv_numExemp_biblio);
        ivCover = findViewById(R.id.iv_livreImage_biblio);
        tv_auteur= findViewById(R.id.tv_auteur_biblio);

        idLivre= getIntent().getIntExtra("ID",0);
        image = getIntent().getStringExtra("IMAGE");
        title = getIntent().getStringExtra("TITRE");
        discipline = getIntent().getStringExtra("Discipline");
        auteur= getIntent().getStringExtra("AUTEUR");
        description = getIntent().getStringExtra("Description");
        numExemp = getIntent().getIntExtra("NUM",0);

        Glide.with(Page_Livre_Biblio.this).load(image).into(ivCover);
        tvTitre.setText(title);
        tvDiscipline.setText(discipline);
        tv_auteur.setText(auteur);
        tvDiscription.setText(description);
        tvNumExemp.setText(String.valueOf(numExemp));
    }

    public void OnClick_btnSupprimer(View view) {


        ProgressDialog progressDialog= new ProgressDialog(Page_Livre_Biblio.this);
        progressDialog.setMessage("please wait...!");
        progressDialog.show();

        String url ="http://"+ Login_Activity.IP +":80/php_Scripts/Gestion_biblio_scripts/delete_livre_biblio.php";

        StringRequest request= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
              progressDialog.dismiss();
                try {
                    JSONObject jsonObject= new JSONObject(response);
                    if(jsonObject.getString("error").equals("false")){
                        Toast.makeText(Page_Livre_Biblio.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(Page_Livre_Biblio.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Page_Livre_Biblio.this, "on error response", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params =new HashMap<String,String>();

                params.put("id_livre",String.valueOf(idLivre));
                return  params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Page_Livre_Biblio.this);
        requestQueue.add(request);
    }

    public void OnClick_btnModifier(View view) {

        Intent intent = new Intent(Page_Livre_Biblio.this,Page_Modification_Livre.class);

        intent.putExtra("ID",idLivre);
        intent.putExtra("IMAGE",image);
        intent.putExtra("TITRE",title);
        intent.putExtra("AUTEUR",auteur);
        intent.putExtra("Discipline",discipline);
        intent.putExtra("Description",description);
        intent.putExtra("NUM",numExemp);

        startActivity(intent);
    }

    //////////
    // this event will enable the back
    // function to the my_biblio_btn_remove on press
    /*@Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
}