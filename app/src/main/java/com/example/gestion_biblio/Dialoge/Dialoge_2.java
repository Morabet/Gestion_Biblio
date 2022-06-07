package com.example.gestion_biblio.Dialoge;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gestion_biblio.Login_Activity;
import com.example.gestion_biblio.R;
import com.example.gestion_biblio.compte_Activity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Dialoge_2 extends AppCompatDialogFragment {

    private EditText dialog2_new_Email,dialog2_new_password,dialog2_new_Tele;
    private Dialoge2_listener dialoge2_listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        LayoutInflater inflater= getActivity().getLayoutInflater();
        View view= inflater.inflate(R.layout.dialog_2_layout,null);
        builder.setView(view)
                .setTitle("Modification")
                .setNegativeButton("annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("confirmer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String newPass= dialog2_new_password.getText().toString();
                        String newEmail= dialog2_new_Email.getText().toString();
                        String newtele= dialog2_new_Tele.getText().toString();

                        dialoge2_listener.ModifierData(newEmail,newPass,newtele,Login_Activity.current_user.getApogee());
                    }
                });
        dialog2_new_Email= view.findViewById(R.id.dialog2_new_Email);
        dialog2_new_password=view.findViewById(R.id.dialog2_new_password);
        dialog2_new_Tele=view.findViewById(R.id.dialog2_new_Tele);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            dialoge2_listener= (Dialoge2_listener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"implementation needed 2 !");
        }
    }

    public interface Dialoge2_listener{

        void ModifierData(String newEmail,String newPass,String newTele,String apogee);
    }
}
