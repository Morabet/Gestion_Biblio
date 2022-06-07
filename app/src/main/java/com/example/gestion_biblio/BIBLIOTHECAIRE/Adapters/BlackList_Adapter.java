package com.example.gestion_biblio.BIBLIOTHECAIRE.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestion_biblio.Modules.User_modelClass;
import com.example.gestion_biblio.R;

import java.util.ArrayList;
import java.util.List;

public class BlackList_Adapter extends RecyclerView.Adapter<BlackList_Adapter.ViewHolder> implements Filterable {
    public Context context;
    ArrayList<User_modelClass> etud_suspendu_list;
    ArrayList<User_modelClass> copyList;
    public final OnClick_RemoveSuspendu onClick_removeSuspendu;

    public BlackList_Adapter(Context context, ArrayList<User_modelClass> etud_suspendu_list, OnClick_RemoveSuspendu onClick_removeSuspendu) {
        this.context = context;
        this.etud_suspendu_list = etud_suspendu_list;
        this.onClick_removeSuspendu = onClick_removeSuspendu;

        copyList= new ArrayList<>(etud_suspendu_list);
    }

    @NonNull
    @Override
    public BlackList_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragement_etud_suspendu_layout,parent,false);
        return new BlackList_Adapter.ViewHolder(view,onClick_removeSuspendu);
    }

    @Override
    public void onBindViewHolder(@NonNull BlackList_Adapter.ViewHolder holder, int position) {
        holder.tv_suspendu_apogee.setText(etud_suspendu_list.get(position).getApogee());
        holder.tv_suspendu_fullName.setText(etud_suspendu_list.get(position).getNom() + " " + etud_suspendu_list.get(position).getPrenom());
    }

    @Override
    public int getItemCount() {
        return etud_suspendu_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_suspendu_apogee,tv_suspendu_fullName;
        Button btn_Suspendu_remove;

        public ViewHolder(@NonNull View itemView,OnClick_RemoveSuspendu onClick_removeSuspendu) {
            super(itemView);

            tv_suspendu_apogee = itemView.findViewById(R.id.tv_suspendu_apogee);
            tv_suspendu_fullName = itemView.findViewById(R.id.tv_suspendu_fullName);

            btn_Suspendu_remove = itemView.findViewById(R.id.btn_Suspendu_remove);
            btn_Suspendu_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClick_removeSuspendu.OnClickRemoveSuspendu(getAdapterPosition());
                }
            });
        }
    }
///////////////


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
            etud_suspendu_list.clear();
            etud_suspendu_list.addAll((ArrayList) filterResults.values);
            notifyDataSetChanged();
        }
    };
}
