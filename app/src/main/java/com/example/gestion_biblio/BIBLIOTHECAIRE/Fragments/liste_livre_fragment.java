package com.example.gestion_biblio.BIBLIOTHECAIRE.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gestion_biblio.Adapters.Livres_liste_Adapter;
import com.example.gestion_biblio.Adapters.RecyclerView_Interface;
import com.example.gestion_biblio.BIBLIOTHECAIRE.Biblio_Activity;
import com.example.gestion_biblio.BIBLIOTHECAIRE.Page_Livre_Biblio;
import com.example.gestion_biblio.Login_Activity;
import com.example.gestion_biblio.Modules.Livre_Model;
import com.example.gestion_biblio.Modules.Livres_modelClass;
import com.example.gestion_biblio.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class liste_livre_fragment extends Fragment {

    View view;

    RecyclerView recyclerView;
    ArrayList<Livre_Model> livres_ArrayList = new ArrayList<>();
    private static Livres_liste_Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_liste_livre_fragment, container, false);

        recyclerView = view.findViewById(R.id.frag_rv_listLivres);
        fetch_Books();
        return view;
    }

    //////////// set livre liste //////////
    ///////////////
    public void fetch_Books(){

        String url = "http://"+ Login_Activity.IP +":80/php_Scripts/Gestion_biblio_scripts/fetch_books_2.php";
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("please wait ...!");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try {
                    JSONArray all_books= new JSONArray(response);

                    for(int i=0;i<all_books.length();i++){

                        JSONObject book = all_books.getJSONObject(i);

                        String imageName= book.getString("image");
                        String image_url= "http://"+Login_Activity.IP+"/php_Scripts/Gestion_biblio_scripts/livresCover/"+imageName;
                        livres_ArrayList.add(new Livre_Model(
                                book.getInt("id_livre"),
                                image_url,
                                book.getString("titre"),
                                book.getString("discipline"),
                                book.getString("description"),
                                book.getString("auteur"),
                                book.getString("disponible"),
                                book.getInt("num_exemplaire")
                        ));
                    }
                    adapter = new Livres_liste_Adapter(getActivity(),livres_ArrayList,recyclerViewInterface);
                    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);
    }

    /////////////////////////
    RecyclerView_Interface recyclerViewInterface= new RecyclerView_Interface() {
        @Override
        public void onItemClick(View view, int position) {
            Intent intent =new Intent(getActivity(), Page_Livre_Biblio.class);
            intent.putExtra("ID",livres_ArrayList.get(position).getId_livre());
            intent.putExtra("IMAGE",livres_ArrayList.get(position).getImageCover());
            intent.putExtra("TITRE",livres_ArrayList.get(position).getTitle());
            intent.putExtra("AUTEUR",livres_ArrayList.get(position).getAuteur());
            intent.putExtra("Discipline",livres_ArrayList.get(position).getDiscipline());
            intent.putExtra("Description",livres_ArrayList.get(position).getDescription());
            intent.putExtra("NUM",livres_ArrayList.get(position).getNum_exemplaire());

            startActivity(intent);
        }
    };
/////////////////////////////////////////////////////////////////////
public static void filter(String s){
    adapter.getFilter().filter(s);
}
//////////////////////////////////////////////////////////////////////////////////////////////////////////



}


