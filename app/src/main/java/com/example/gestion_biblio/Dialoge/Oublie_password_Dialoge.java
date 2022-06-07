package com.example.gestion_biblio.Dialoge;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.gestion_biblio.R;

public class Oublie_password_Dialoge extends AppCompatDialogFragment {

    private EditText et_apogee,et_email;

    String apogee,email;

    private Oublie_password_Dialoge.Dialoge_Listener dialoge_listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater= getActivity().getLayoutInflater();
        View view= inflater.inflate(R.layout.oublie_password_dialoge_layout,null);
        builder.setView(view)
                .setTitle("Confirmermation")
                .setNegativeButton("annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("confirmer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        apogee = et_apogee.getText().toString();
                        email = et_email.getText().toString();

                        dialoge_listener.SendPassword(apogee,email);
                    }
                });

        et_apogee= view.findViewById(R.id.ET_apogee_Oublier);
        et_email= view.findViewById(R.id.ET_email_Oublier);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try{
            dialoge_listener= (Oublie_password_Dialoge.Dialoge_Listener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"implementation needed !");
        }
    }

    public interface Dialoge_Listener{

        void SendPassword(String apogee,String email);
    }
}
