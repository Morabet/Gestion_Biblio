package com.example.gestion_biblio.BIBLIOTHECAIRE.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gestion_biblio.Adapters.RecyclerView_Interface;
import com.example.gestion_biblio.BIBLIOTHECAIRE.Adapters.reservation_Adapter;
import com.example.gestion_biblio.BIBLIOTHECAIRE.reservation_etud_page;
import com.example.gestion_biblio.Login_Activity;
import com.example.gestion_biblio.Modules.User_modelClass;
import com.example.gestion_biblio.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;


public class reservation extends Fragment {
    View view;
    RecyclerView recyclerView;
    ArrayList<User_modelClass> userListe= new ArrayList<>();
    private static reservation_Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_reservation, container, false);
        recyclerView = view.findViewById(R.id.rv_reservedBooks_biblio);

        setUpUserListe();

        return view;
    }

    //////// set user liste /////
    public  void setUpUserListe(){

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait ...");
        progressDialog.show();

        String url ="http://"+ Login_Activity.IP +":80/php_Scripts/Gestion_biblio_scripts/fetch_etud_reserver.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);
                        userListe.add(new User_modelClass(
                                object.getString("apogee"),
                                object.getString("nom"),
                                object.getString("prenom")
                        ));

                        adapter = new reservation_Adapter(getActivity(),userListe,recyclerViewInterface);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView.setAdapter(adapter);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "on error response", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);
    }

    ///////////////
    RecyclerView_Interface recyclerViewInterface = new RecyclerView_Interface() {
        @Override
        public void onItemClick(View view, int position) {
            Intent intent = new Intent(getContext(), reservation_etud_page.class);

            intent.putExtra("Apogee",userListe.get(position).getApogee());
            intent.putExtra("Nom",userListe.get(position).getNom());
            intent.putExtra("Prenom",userListe.get(position).getPrenom());

            startActivity(intent);
        }
    };
    ///////////////////////////////////////////////
    public static void filter(String s){
        adapter.getFilter().filter(s);
    }
}