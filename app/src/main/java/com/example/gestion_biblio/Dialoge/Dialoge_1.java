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

public class Dialoge_1 extends AppCompatDialogFragment {

    private EditText dialog_1_pass;
    private Dialoge_1_Listener dialoge_1_listener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater= getActivity().getLayoutInflater();
        View view= inflater.inflate(R.layout.dialog_1_layout,null);
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
                        String current_password= dialog_1_pass.getText().toString();
                        dialoge_1_listener.getPassword(current_password);
                    }
                });

        dialog_1_pass= view.findViewById(R.id.dialog_1_pass);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try{
            dialoge_1_listener= (Dialoge_1_Listener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"implementation needed !");
        }
    }

    public interface Dialoge_1_Listener{

        void getPassword(String currentPass);
    }

}
