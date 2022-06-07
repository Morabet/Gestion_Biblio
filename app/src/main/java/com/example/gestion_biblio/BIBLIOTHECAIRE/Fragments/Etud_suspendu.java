package com.example.gestion_biblio.BIBLIOTHECAIRE.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
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
import com.example.gestion_biblio.BIBLIOTHECAIRE.Adapters.BlackList_Adapter;
import com.example.gestion_biblio.BIBLIOTHECAIRE.Adapters.OnClick_ConfirmeRetour;
import com.example.gestion_biblio.BIBLIOTHECAIRE.Adapters.OnClick_RemoveSuspendu;
import com.example.gestion_biblio.Login_Activity;
import com.example.gestion_biblio.Modules.User_modelClass;
import com.example.gestion_biblio.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Etud_suspendu extends Fragment {

    View view;
    RecyclerView recyclerView;
    ArrayList<User_modelClass> etud_suspendu_list=new ArrayList<>();
    static BlackList_Adapter adapter;
    public String url ="http://"+ Login_Activity.IP+"/php_Scripts/Gestion_biblio_scripts/fetch_liste_noire.php";
    public String url1 ="http://"+ Login_Activity.IP+"/php_Scripts/Gestion_biblio_scripts/remove_suspendu.php";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       view =  inflater.inflate(R.layout.fragment_etud_suspendu, container, false);
       recyclerView = view.findViewById(R.id.rv_BlackList);
       data();

       return view;
    }

    private void data() {
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait ...");
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(Etud_suspendu.this.getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i<array.length(); i++){

                                JSONObject object = array.getJSONObject(i);
                                etud_suspendu_list.add(new User_modelClass(
                                        object.getString("apogee"),
                                        object.getString("nom"),
                                        object.getString("prenom")
                                ));

                            }
                            Log.e("tttttttttt","BookList "+etud_suspendu_list.size());
                            adapter = new BlackList_Adapter(getContext(),etud_suspendu_list,onClick_removeSuspendu);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                            recyclerView.setAdapter(adapter);


                        }catch (Exception e){

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Fail to get the data..", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }

    OnClick_RemoveSuspendu onClick_removeSuspendu = new OnClick_RemoveSuspendu() {
        @Override
        public void OnClickRemoveSuspendu(int position) {
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Toast.makeText(getActivity(), jsonObject.getString("message").toString(), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity(), "Fail to get the data..", Toast.LENGTH_SHORT).show();
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put("apogee",etud_suspendu_list.get(position).getApogee());
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }
    };

    public static void filter(String s){
        adapter.getFilter().filter(s);
    }
}