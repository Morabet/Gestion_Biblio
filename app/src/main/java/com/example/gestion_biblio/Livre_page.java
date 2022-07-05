package com.example.gestion_biblio;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Livre_page extends AppCompatActivity {

    ImageView iv_bookImage;
    Button  btn_reserver;
    TextView tv_livreTitre,tv_auteur,tv_discipline,tv_description,tv_num;

    int id,numExemp;
    String image,title,auteur, discipline, description;

    RequestQueue requestQueue;
    public static final String myTAG = "REQUEST TAG";
    Handler handler = new Handler();
    Handler handler2 = new Handler();
    Runnable runnable,runnable2;
    int numR,numE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livre_page);
        getSupportActionBar().setTitle("Explorer");
        //setting the back my_biblio_btn_remove
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        requestQueue = Volley.newRequestQueue(Livre_page.this);

        numR= Login_Activity.current_user.getNum_reserver();
        numE= Login_Activity.current_user.getNum_emprunter();

        getNum();
        setUpViews();
        refreshPage();

    }

    ////////
    public void setUpViews(){
        btn_reserver= findViewById(R.id.btn_Reserver);
        btn_reserver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReserverLivre();
            }
        });
        iv_bookImage = findViewById(R.id.iv_bookImage);
        tv_livreTitre = findViewById(R.id.tv_livreTitre);
        tv_auteur= findViewById(R.id.tv_auteur);
        tv_description = findViewById(R.id.tv_description);
        tv_discipline = findViewById(R.id.tv_discipline);
        tv_num = findViewById(R.id.tv_num);


        image = getIntent().getStringExtra("IMAGE");
        id = getIntent().getIntExtra("ID",0);
        title = getIntent().getStringExtra("TITRE");
        discipline = getIntent().getStringExtra("Discipline");
        description = getIntent().getStringExtra("Description");
        auteur = getIntent().getStringExtra("Auteur");
        numExemp = getIntent().getIntExtra("NUM",0);

        Glide.with(this).load(image).into(iv_bookImage);
        tv_livreTitre.setText(title);
        tv_description.setText(description);
        tv_discipline.setText(discipline);
        tv_auteur.setText(auteur);
        tv_num.setText(String.valueOf(numExemp));
        getNum();
    }
///////////////////////////////////////

    public void ReserverLivre(){
        Log.e("dddddddd",numE+"    "+numR);
        if( numE+ numR>=3 || numExemp<=0){
            Toast.makeText(Livre_page.this,"la réservation est refusée",Toast.LENGTH_LONG).show();
        }
        else {
            handler.removeCallbacks(runnable);  // to stop the handler
            handler2.removeCallbacks(runnable2);

            ProgressDialog progressDialog = new ProgressDialog(Livre_page.this);
            progressDialog.setMessage("please wait ...!");
            progressDialog.show();

            String url= "http://"+Login_Activity.IP+":80/php_Scripts/Gestion_biblio_scripts/Reserver_Livre.php";

            StringRequest request= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    handler.postDelayed(runnable, 0);   // to reset the handler
                    handler2.postDelayed(runnable2,0);
                    progressDialog.dismiss();

                    try {
                        JSONObject jsonObject= new JSONObject(response);
                        Toast.makeText(Livre_page.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Livre_page.this, "error on response", Toast.LENGTH_SHORT).show();
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
                    params.put("id_livre",String.valueOf(id));
                    params.put("etud_apogee",Login_Activity.current_user.getApogee());
                    return params;
                }
            };

            requestQueue.add(request);
        }
    }

////  !!  RESERVER  !!! ///////////////////////////////////////

    public void getNum(){
        String url3= "http://"+Login_Activity.IP+":80/php_Scripts/Gestion_biblio_scripts/getNumExemplaireReserverEmprunter.php";
        Log.e("MMMMM","getNum 1");
        handler.removeCallbacks(runnable);
        handler2.postDelayed(runnable2=new Runnable() {
            @Override
            public void run() {
                requestQueue.cancelAll(myTAG);
                StringRequest request3= new StringRequest(Request.Method.POST, url3, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("NNNN","getNum");
                        try {
                            JSONObject object= new JSONObject(response);
                            if(object.getString("error").equals("false")){
                                numR=Integer.parseInt(object.getString("numR"));
                                numE=Integer.parseInt(object.getString("numE"));
                                Log.e("AAAAAAA",numR+"    "+numE);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        handler.postDelayed(runnable,0);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("NNEEEEE","getNum ERROR");
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
                        param.put("apogee",Login_Activity.current_user.getApogee());
                        return param;
                    }
                } ;
                request3.setTag(myTAG);
                requestQueue.add(request3);
                handler2.postDelayed(runnable2, 1000);
            }
        },1000);

    }

/////////////////////////////////////////////////////////////////////////////

   public void refreshPage(){
        String url= "http://"+Login_Activity.IP+":80/php_Scripts/Gestion_biblio_scripts/get_Single_Book.php";
            Log.e("RRRRRR","refresh ");
            handler2.removeCallbacks(runnable2);
        handler.postDelayed(runnable= new Runnable() {
            @Override
            public void run() {
                requestQueue.cancelAll(myTAG);

                StringRequest request2= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object= new JSONObject(response);
                            if(object.getString("error").equals("false")){
                                tv_livreTitre.setText(object.getString("titre"));
                                tv_description.setText(object.getString("description"));
                                tv_discipline.setText(object.getString("discipline"));
                                tv_auteur.setText(object.getString("auteur"));
                                tv_num.setText(object.getString("numEx"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        handler2.postDelayed(runnable2,0);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
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
                        param.put("id_livre",String.valueOf(id));
                        return param;
                    }
                } ;
                request2.setTag(myTAG);
                requestQueue.add(request2);
                handler.postDelayed(runnable, 1000);
            }
        },1000);

    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////
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