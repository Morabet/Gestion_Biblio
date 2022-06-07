package com.example.gestion_biblio.BIBLIOTHECAIRE.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestion_biblio.Modules.Livre_EmprunterModel;
import com.example.gestion_biblio.R;

import java.util.List;


public class BookEmprunterAdapter extends RecyclerView.Adapter<BookEmprunterAdapter.ViewHolder>{

    public Context context;
    public List<Livre_EmprunterModel> livre_emprunterList;
    public  final OnClick_ConfirmeRetour onClick_confirmeRetour;

    public BookEmprunterAdapter(Context context, List<Livre_EmprunterModel> livre_emprunterList, OnClick_ConfirmeRetour onClick_confirmeRetour) {
        this.context = context;
        this.livre_emprunterList = livre_emprunterList;
        this.onClick_confirmeRetour = onClick_confirmeRetour;
    }

    @NonNull
    @Override
    public BookEmprunterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.book_emprunter_adapter_layout,parent,false);
        return new BookEmprunterAdapter.ViewHolder(view,onClick_confirmeRetour);
    }

    @Override
    public void onBindViewHolder(@NonNull BookEmprunterAdapter.ViewHolder holder, int position) {
        holder.tv_titre.setText(livre_emprunterList.get(holder.getAdapterPosition()).getTitle());
    }

    @Override
    public int getItemCount() {
        return livre_emprunterList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_titre;
        Button btn_confirmerR;

        public ViewHolder(@NonNull View itemView, OnClick_ConfirmeRetour onClick_confirmeRetour) {
            super(itemView);

            tv_titre= itemView.findViewById(R.id.tv_livre_titre_emprunter);
            btn_confirmerR= itemView.findViewById(R.id.btn_confirmer_retour_EMprunt);
            btn_confirmerR.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClick_confirmeRetour.OnClickConfirmeRetour(getAdapterPosition());
                }
            });
        }
    }
}
