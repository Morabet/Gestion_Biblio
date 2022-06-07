package com.example.gestion_biblio.BIBLIOTHECAIRE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class Page_Modification_Livre extends AppCompatActivity {

    TextView tvTitre,tvDiscipline,tvDiscription,tv_auteur_biblio_mod,tvNumExemp;
    ImageView ivCover;

    int id,numExemp;
    String image,auteur,title, discipline, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_modification_livre);
        getSupportActionBar().setTitle("Administrateur");
        //setting the back my_biblio_btn_remove
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        setUpViews();
    }

    ////// setUp views /////////
    public void setUpViews(){
        tvTitre = findViewById(R.id.tv_titre_biblio_mod);
        tvDiscipline = findViewById(R.id.tv_discipline_biblio_mod);
        tvDiscription = findViewById(R.id.tv_description_biblio_mod);
        tvNumExemp = findViewById(R.id.tv_numExemp_biblio_mod);
        ivCover = findViewById(R.id.iv_livreImage_biblio_mod);
        tv_auteur_biblio_mod= findViewById(R.id.tv_auteur_biblio_mod);

        id= getIntent().getIntExtra("ID",0);
        image = getIntent().getStringExtra("IMAGE");
        title = getIntent().getStringExtra("TITRE");
        discipline = getIntent().getStringExtra("Discipline");
        auteur= getIntent().getStringExtra("AUTEUR");
        description = getIntent().getStringExtra("Description");
        numExemp = getIntent().getIntExtra("NUM",0);

        Glide.with(this).load(image).into(ivCover);
        tvTitre.setText(title);
        tv_auteur_biblio_mod.setText(auteur);
        tvDiscipline.setText(discipline);
        tvDiscription.setText(description);
        tvNumExemp.setText(String.valueOf(numExemp));
    }

    public void OnClick_btnConfirmModification(View view) {

        title= tvTitre.getText().toString();
        discipline= tvDiscipline.getText().toString();
        auteur= tv_auteur_biblio_mod.getText().toString();
        description= tvDiscription.getText().toString();
        numExemp= Integer.parseInt(tvNumExemp.getText().toString());

        String url = "http://"+ Login_Activity.IP +":80/php_Scripts/Gestion_biblio_scripts/modifier_livre_biblio.php";
        ProgressDialog progressDialog= new ProgressDialog(Page_Modification_Livre.this);
        progressDialog.setMessage("please wait...!");
        progressDialog.show();

        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject= new JSONObject(response);

                    if(jsonObject.get("error").equals("false")){
                        Log.e("§§§§§§§§§ T","error"+jsonObject.getString("message"));
                        Toast.makeText(Page_Modification_Livre.this,jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Page_Modification_Livre.this,Page_Livre_Biblio.class);
                        intent.putExtra("ID",id);
                        intent.putExtra("IMAGE",image);
                        intent.putExtra("TITRE",title);
                        intent.putExtra("AUTEUR",auteur);
                        intent.putExtra("Discipline",discipline);
                        intent.putExtra("Description",description);
                        intent.putExtra("NUM",numExemp);
                        startActivity(intent);
                    }
                    else {
                        Log.e("§§§§§§§§§ TAg","error");
                        Log.e("§§§§§§§§§ TAg","error"+jsonObject.getString("message"));
                        Toast.makeText(Page_Modification_Livre.this,jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Page_Modification_Livre.this,"error on response",Toast.LENGTH_LONG).show();
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

                params.put("id_livre",String.valueOf(id));
                params.put("titre",title);
                params.put("auteur",auteur);
                params.put("discipline",discipline);
                params.put("description",description);
                params.put("numEx",String.valueOf(numExemp));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Page_Modification_Livre.this);
        requestQueue.add(request);
    }


//////////////////////////////////////////////////////////////////////////////////////////////////////
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