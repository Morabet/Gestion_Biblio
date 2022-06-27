package com.example.gestion_biblio.BIBLIOTHECAIRE.Fragments;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gestion_biblio.Login_Activity;
import com.example.gestion_biblio.R;
import com.example.gestion_biblio.homeEtud_Activity;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


public class Ajouter_livre extends Fragment {
    public Ajouter_livre() {
        // Required empty public constructor
    }

    View view;
    ImageView iv_selectedImage;
    EditText tv_setTitre,tv_description_biblio,tv_numExemp_biblio,tv_setAuteur;
    Button btn_ajouterLivre,btn_selectImage;
    Spinner spinner;

    String titre,discipline,description,auteur,numExemp;

    Bitmap bitmap;
    String encodedImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_ajouter_livre, container, false);

        setUpViews();

        return view;
    }

    //////// setUp Viexs /////////////
    public void setUpViews(){
        iv_selectedImage= view.findViewById(R.id.iv_selectedImage);

        tv_setTitre= view.findViewById(R.id.tv_setTitre);
        tv_description_biblio= view.findViewById(R.id.tv_description_biblio);
        tv_numExemp_biblio= view.findViewById(R.id.tv_numExemp_biblio);
        tv_setAuteur= view.findViewById(R.id.tv_setAuteur);
        spinner = view.findViewById(R.id.spinner_discipline);

        setSpinner();
        btn_ajouterLivre = view.findViewById(R.id.btn_ajouterLivre);
        btn_ajouterLivre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick_btnAjouterLivre();
            }
        });
        btn_selectImage = view.findViewById(R.id.btn_selectImage);
        btn_selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
    }

    private void setSpinner() {
        String disciplines[]= {"","physique","biologie","chimie","giologie","mathematique","informatique"};
        ArrayAdapter<String> adapter= new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item,disciplines);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                discipline= spinner.getSelectedItem().toString();

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                discipline="";
            }
        });
    }

///////////////////////// !!!!  SELECT IMAGE  !!!///////////////////////////////////////////////////////
public void selectImage(){

    Dexter.withContext(getActivity()).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(new PermissionListener() {
                @Override
                public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent,"sélectionnez une image"),1);
                }

                @Override
                public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                }

                @Override
                public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                    permissionToken.continuePermissionRequest();
                }
            }).check();
}

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == 1 && resultCode == RESULT_OK && data != null){

            Uri filePath= data.getData();

            try {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(filePath);
                bitmap= BitmapFactory.decodeStream(inputStream);
                iv_selectedImage.setImageBitmap(bitmap);

                imageStore(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void imageStore(Bitmap bitmap) {

        Thread thread= new Thread(){
            @Override
            public void run() {
                ByteArrayOutputStream stream= new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
                byte[] imageBytes= stream.toByteArray();

                encodedImage= android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////
    public void onClick_btnAjouterLivre(){
                /// we can add them to the model in the ajouter btn

                titre = String.valueOf(tv_setTitre.getText());
                auteur= String.valueOf(tv_setAuteur.getText());
                description = String.valueOf(tv_description_biblio.getText());
                numExemp = tv_numExemp_biblio.getText().toString();

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("please wait ...!");
        progressDialog.show();

        String url ="http://"+ Login_Activity.IP+":80/php_Scripts/Gestion_biblio_scripts/Ajouter_Livre.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try {
                    JSONObject object = new JSONObject(response);
                    Toast.makeText(getContext(), object.toString(), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                iv_selectedImage.setImageResource(0);
                tv_setTitre.setText("");
                tv_description_biblio.setText("");
                tv_numExemp_biblio.setText("");
                tv_setAuteur.setText("");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
                params.put("image",encodedImage);
                params.put("title",titre);
                params.put("auteur",auteur);
                params.put("discipline",discipline);
                params.put("description",description);
                params.put("numEx",numExemp);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);
    }
//////////////////////////////////////////////////////////////////////////////////////

}