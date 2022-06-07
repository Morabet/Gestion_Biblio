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

import com.example.gestion_biblio.Modules.User_modelClass;
import com.example.gestion_biblio.R;

import java.util.ArrayList;
import java.util.List;


public class EmpruntAdapter extends RecyclerView.Adapter<EmpruntAdapter.ViewHolder> implements Filterable {

    public Context context;
    public List<User_modelClass> EmpList;
    private List<User_modelClass> copyList;
    public final OnClick_etudEmprunter onClick_etudEmprunter;

    public EmpruntAdapter(Context context, List<User_modelClass> empList, OnClick_etudEmprunter onClick_etudEmprunter) {
        this.context = context;
        EmpList = empList;
        this.onClick_etudEmprunter = onClick_etudEmprunter;

        copyList= new ArrayList<>(EmpList);
    }

    @NonNull
    @Override
    public EmpruntAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragement_reservation_layout,parent,false);

        return  new EmpruntAdapter.ViewHolder(view,onClick_etudEmprunter);

    }

    @Override
    public void onBindViewHolder(@NonNull EmpruntAdapter.ViewHolder holder, int position) {
        holder.tv_nom.setText(EmpList.get(position).getNom());
        holder.tv_prenom.setText(EmpList.get(position).getPrenom());
        holder.tv_apogee.setText(EmpList.get(position).getApogee());
    }

    @Override
    public int getItemCount() {
        return EmpList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_apogee,tv_nom,tv_prenom;
        public ViewHolder(@NonNull View itemView, OnClick_etudEmprunter onClick_etudEmprunter) {
            super(itemView);
            tv_apogee = itemView.findViewById(R.id.tv_apogee);
            tv_nom = itemView.findViewById(R.id.tv_nom);
            tv_prenom = itemView.findViewById(R.id.tv_prenom);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClick_etudEmprunter.ItemClick(view,getAdapterPosition());
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
            EmpList.clear();
            EmpList.addAll((ArrayList) filterResults.values);
            notifyDataSetChanged();
        }
    };
}
