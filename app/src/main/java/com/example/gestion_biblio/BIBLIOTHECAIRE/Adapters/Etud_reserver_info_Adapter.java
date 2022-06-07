package com.example.gestion_biblio.BIBLIOTHECAIRE.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestion_biblio.Modules.LivreReserver_Model;
import com.example.gestion_biblio.Modules.Livres_modelClass;
import com.example.gestion_biblio.R;

import java.util.ArrayList;

public class Etud_reserver_info_Adapter extends RecyclerView.Adapter<Etud_reserver_info_Adapter.ViewHolder>{

    public Context context;
    ArrayList<LivreReserver_Model> livre_reserver_titre;
    Confirmer_Emprunt_Interface confirmer_emprunt_interface;

    public Etud_reserver_info_Adapter(Context context, ArrayList<LivreReserver_Model> livre_reserver_titre,Confirmer_Emprunt_Interface confirmer_emprunt_interface) {
        this.context = context;
        this.livre_reserver_titre = livre_reserver_titre;
        this.confirmer_emprunt_interface= confirmer_emprunt_interface;
    }

    @NonNull
    @Override
    public Etud_reserver_info_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_reservation_etud_page_layout,parent,false);
        return new ViewHolder(view,confirmer_emprunt_interface);
    }

    @Override
    public void onBindViewHolder(@NonNull Etud_reserver_info_Adapter.ViewHolder holder, int position) {
        holder.tv_livre_titre_reserver.setText(livre_reserver_titre.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return livre_reserver_titre.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_livre_titre_reserver;
        Button btn_confirmerEMprunt_reserver;

        public ViewHolder(@NonNull View itemView,Confirmer_Emprunt_Interface confirmer_emprunt_interface) {
            super(itemView);

            tv_livre_titre_reserver = itemView.findViewById(R.id.tv_livre_titre_reserver);
            btn_confirmerEMprunt_reserver = itemView.findViewById(R.id.btn_confirmerEMprunt_reserver);

            btn_confirmerEMprunt_reserver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    confirmer_emprunt_interface.OnClick_ConfirmerEmprunt(getAdapterPosition());
                }
            });
        }
    }
}
