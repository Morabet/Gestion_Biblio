package com.example.gestion_biblio.Dialoge;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.gestion_biblio.R;

public class Dialoge_3 extends AppCompatDialogFragment {

    private EditText dialog3_new_email,dialog3_new_password,dialog3_new_nom;
    Dialoge3_listener dialoge3_listener ;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        LayoutInflater inflater= getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialoge_3_layout,null);
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
                        String newNom = dialog3_new_nom.getText().toString();
                        String newEmail = dialog3_new_email.getText().toString();
                        String newPassword = dialog3_new_password.getText().toString();
                        dialoge3_listener.modifierdata(newNom , newEmail , newPassword);
                    }
                });
        dialog3_new_nom = view.findViewById(R.id.dialog3_new_nom);
        dialog3_new_email = view.findViewById(R.id.dialog3_new_email);
        dialog3_new_password = view.findViewById(R.id.dialog3_new_password);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
             dialoge3_listener = (Dialoge3_listener) context;
        }
        catch (ClassCastException e ){
            throw new ClassCastException(context.toString()+"implementation needed 3 !");
        }
    }

    public  interface Dialoge3_listener{
        void modifierdata(String newNom , String newEmail , String newPassword);
    }
}
