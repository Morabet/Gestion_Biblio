package com.example.gestion_biblio.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gestion_biblio.Modules.Livre_Model;
import com.example.gestion_biblio.Modules.Livres_modelClass;
import com.example.gestion_biblio.R;

import java.util.ArrayList;
import java.util.List;

public class Livres_liste_Adapter extends RecyclerView.Adapter<Livres_liste_Adapter.ViewHolder> implements Filterable {


    ArrayList<Livre_Model> livreListe;
    ArrayList<Livre_Model> copyList; // to store the items ; we need it in the search view

    Context context;
    private final RecyclerView_Interface recyclerViewInterface;

    public Livres_liste_Adapter(Context context ,ArrayList<Livre_Model> livreListe, RecyclerView_Interface recyclerViewInterface) {
        this.context= context;
        this.livreListe = livreListe;
        this.recyclerViewInterface = recyclerViewInterface;

        copyList= new ArrayList<>(livreListe);// copy the elements of livreListe
    }

    @NonNull
    @Override
    public Livres_liste_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.liste_livres_e,parent,false);

        return new Livres_liste_Adapter.ViewHolder(view,recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull Livres_liste_Adapter.ViewHolder holder, int position) {
        holder.tv_titre.setText(livreListe.get(position).getTitle());
        Glide.with(context).load(livreListe.get(position).getImageCover()).into(holder.iv_livre);
    }

    @Override
    public int getItemCount() {
        return livreListe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_livre;
        TextView tv_titre;

        public ViewHolder(@NonNull View itemView,RecyclerView_Interface recyclerViewInterface) {
            super(itemView);

            iv_livre = itemView.findViewById(R.id.iv_livre);
            tv_titre = itemView.findViewById(R.id.tv_titre);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        recyclerViewInterface.onItemClick(view,getAdapterPosition());
                }
            });
        }
    }

    ///////////////////////////////////////////
    @Override
    public Filter getFilter() {
        return myFilter;
    }
    private Filter myFilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Livre_Model> filteredList = new ArrayList<>();

            if(charSequence == null || charSequence.length()== 0){
                filteredList.addAll(copyList);
            }
            else {
                String filterPatern= charSequence.toString().toLowerCase().trim();

                for(Livre_Model item: copyList){
                    if(item.getTitle().toLowerCase().trim().contains(filterPatern)){
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
            livreListe.clear();
            livreListe.addAll((ArrayList) filterResults.values);
            notifyDataSetChanged();
        }
    };
}
