package com.example.gestion_biblio.BIBLIOTHECAIRE.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestion_biblio.Adapters.RecyclerView_Interface;
import com.example.gestion_biblio.Modules.Livre_Model;
import com.example.gestion_biblio.Modules.User_modelClass;
import com.example.gestion_biblio.R;

import java.util.ArrayList;
import java.util.List;

public class reservation_Adapter extends RecyclerView.Adapter<reservation_Adapter.ViewHolder> implements Filterable {

    Context context;
    ArrayList<User_modelClass> userListe;
    ArrayList<User_modelClass> copyList;
    RecyclerView_Interface recyclerViewInterface;

    public reservation_Adapter(Context context, ArrayList<User_modelClass> userListe, RecyclerView_Interface recyclerViewInterface) {
        this.context = context;
        this.userListe = userListe;
        this.recyclerViewInterface = recyclerViewInterface;

        copyList= new ArrayList<>(userListe);
    }

    @NonNull
    @Override
    public reservation_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragement_reservation_layout,parent,false);

        return  new reservation_Adapter.ViewHolder(view,recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull reservation_Adapter.ViewHolder holder, int position) {
        holder.tv_nom.setText(userListe.get(position).getNom());
        holder.tv_prenom.setText(userListe.get(position).getPrenom());
        holder.tv_apogee.setText(userListe.get(position).getApogee());
    }

    @Override
    public int getItemCount() {
        return userListe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_apogee,tv_nom,tv_prenom;

        public ViewHolder(@NonNull View itemView,RecyclerView_Interface recyclerViewInterface) {
            super(itemView);

            tv_apogee = itemView.findViewById(R.id.tv_apogee);
            tv_nom = itemView.findViewById(R.id.tv_nom);
            tv_prenom = itemView.findViewById(R.id.tv_prenom);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerViewInterface.onItemClick(view,getAdapterPosition());
                }
            });
        }
    }

    ///////////////////////////////////////////////////////////////////////
    @Override
    public Filter getFilter() {

        return myFilter;
    }
    private Filter myFilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<User_modelClass> filteredList = new ArrayList<>();

            if(charSequence == null || charSequence.length()== 0){
                filteredList.addAll(copyList);
            }
            else {
                String filterPatern= charSequence.toString().toLowerCase().trim();

                for(User_modelClass item: copyList){
                    if(item.getApogee().toLowerCase().trim().contains(filterPatern)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results= new FilterResults();
            results.values= filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            userListe.clear();
            userListe.addAll((ArrayList) filterResults.values);
            notifyDataSetChanged();
        }
    };
}
