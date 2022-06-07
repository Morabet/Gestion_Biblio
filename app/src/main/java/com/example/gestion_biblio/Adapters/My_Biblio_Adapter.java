package com.example.gestion_biblio.Adapters;

import android.content.Context;
import android.icu.text.Transliterator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestion_biblio.Modules.Livre_Model;
import com.example.gestion_biblio.Modules.Livres_modelClass;
import com.example.gestion_biblio.R;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class My_Biblio_Adapter extends RecyclerView.Adapter<My_Biblio_Adapter.MyViewHolder> {

    Context context;
    ArrayList<Livre_Model> livreListe;
    private final RecyclerView_Interface recyclerViewInterface;
    private final RemoveReservation_interface removeReservation_interface;

    public My_Biblio_Adapter(RemoveReservation_interface removeReservation_interface,Context context, ArrayList<Livre_Model> livreListe, RecyclerView_Interface recyclerViewInterface) {
        this.context = context;
        this.livreListe = livreListe;
        this.recyclerViewInterface = recyclerViewInterface;
        this.removeReservation_interface= removeReservation_interface;
    }

    @NonNull
    @Override
    public My_Biblio_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_biblio_rv_layout,parent,false);

        return new My_Biblio_Adapter.MyViewHolder(view,recyclerViewInterface,removeReservation_interface);
    }

    @Override
    public void onBindViewHolder(@NonNull My_Biblio_Adapter.MyViewHolder holder, int position) {
        holder.tv_my_biblio_titre.setText(livreListe.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return livreListe.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_my_biblio_titre;
        Button btn_remove;

        public MyViewHolder(@NonNull View itemView, RecyclerView_Interface recyclerViewInterface,RemoveReservation_interface removeReservation_interface) {
            super(itemView);

            tv_my_biblio_titre = itemView.findViewById(R.id.tv_my_biblio_titre);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerViewInterface.onItemClick(view,getAdapterPosition());
                }
            });

            btn_remove= itemView.findViewById(R.id.my_biblio_btn_remove);

            btn_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeReservation_interface.OnClick_removeReservation(getAdapterPosition());
                }
            });
        }
    }
}
