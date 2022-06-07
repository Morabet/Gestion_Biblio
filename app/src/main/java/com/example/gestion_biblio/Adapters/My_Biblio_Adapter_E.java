package com.example.gestion_biblio.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestion_biblio.Modules.Livre_Model;
import com.example.gestion_biblio.Modules.Livres_modelClass;
import com.example.gestion_biblio.R;

import java.util.ArrayList;

public class My_Biblio_Adapter_E extends RecyclerView.Adapter<My_Biblio_Adapter_E.MyViewHolder> {

    Context context;
    ArrayList<Livre_Model> livreListe;
    private final RecyclerView_Interface_2 recyclerViewInterface2;

    public My_Biblio_Adapter_E(Context context, ArrayList<Livre_Model> livreListe, RecyclerView_Interface_2 recyclerViewInterface2) {
        this.context = context;
        this.livreListe = livreListe;
        this.recyclerViewInterface2 = recyclerViewInterface2;
    }

    @NonNull
    @Override
    public My_Biblio_Adapter_E.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_biblio_rv_layout_emprunter,parent,false);

        return new My_Biblio_Adapter_E.MyViewHolder(view,recyclerViewInterface2);
    }

    @Override
    public void onBindViewHolder(@NonNull My_Biblio_Adapter_E.MyViewHolder holder, int position) {
        holder.tv_my_biblio_titreE.setText(livreListe.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return livreListe.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_my_biblio_titreE;

        public MyViewHolder(@NonNull View itemView,RecyclerView_Interface_2 recyclerViewInterface2) {
            super(itemView);

            tv_my_biblio_titreE = itemView.findViewById(R.id.tv_my_biblio_titreE);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerViewInterface2.onItemClick2(view,getAdapterPosition());
                }
            });
        }
    }
}
