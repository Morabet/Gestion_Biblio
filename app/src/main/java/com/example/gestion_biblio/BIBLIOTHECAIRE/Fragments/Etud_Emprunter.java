package com.example.gestion_biblio.BIBLIOTHECAIRE.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gestion_biblio.BIBLIOTHECAIRE.Adapters.EmpruntAdapter;
import com.example.gestion_biblio.BIBLIOTHECAIRE.Adapters.OnClick_etudEmprunter;
import com.example.gestion_biblio.BIBLIOTHECAIRE.ConfirmerRetour_LivresEmprunter;
import com.example.gestion_biblio.Login_Activity;
import com.example.gestion_biblio.Modules.User_modelClass;
import com.example.gestion_biblio.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Etud_Emprunter extends Fragment {

    View view;
    RecyclerView recyclerView;
    private static EmpruntAdapter emprunter_Adapter;
    ArrayList<User_modelClass> userListe;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_etud__emprunter, container, false);

        recyclerView= view.findViewById(R.id.rv_retourn_emprunt);
        setUpEtudList();
        return view;
    }

    private void setUpEtudList() {

        String url= "http://"+ Login_Activity.IP +":80/php_Scripts/Gestion_biblio_scripts/fetch_Etud_Emprunter.php";

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait ...");
        progressDialog.show();
        userListe= new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
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

                            }
                            Log.e("tttttttttt", "BookList " + userListe.size());
                            emprunter_Adapter = new EmpruntAdapter(getContext(), userListe, onClick_etudEmprunter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                            recyclerView.setAdapter(emprunter_Adapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }}, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "on error response", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(stringRequest);
    }

    OnClick_etudEmprunter onClick_etudEmprunter= new OnClick_etudEmprunter() {
        @Override
        public void ItemClick(View view, int position) {
            Intent intent= new Intent(getContext(), ConfirmerRetour_LivresEmprunter.class);

            intent.putExtra("Apogee",userListe.get(position).getApogee());
            intent.putExtra("Nom",userListe.get(position).getNom());
            intent.putExtra("Prenom",userListe.get(position).getPrenom());

            startActivity(intent);
        }
    };

    ////////////////////////////////
    public static void filter(String s){
        emprunter_Adapter.getFilter().filter(s);
    }
}