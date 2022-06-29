package com.example.gestion_biblio.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestion_biblio.Livres_listes;
import com.example.gestion_biblio.Modules.Livre_Model;
import com.example.gestion_biblio.Modules.Livres_modelClass;
import com.example.gestion_biblio.Modules.parent_modelClass;
import com.example.gestion_biblio.R;
import com.example.gestion_biblio.homeEtud_Activity;

import java.util.ArrayList;
import java.util.List;


public class parentAdapter extends RecyclerView.Adapter<parentAdapter.ViewHlder> {
    // will put a textView and a my_biblio_btn_remove and a list of child items

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    ArrayList<parent_modelClass> parentList;
    private final Nested_RV_interface recyclerViewInterface;
    Context context;

    public parentAdapter(Context context,ArrayList<parent_modelClass> parentList, Nested_RV_interface recyclerViewInterface) {
        this.parentList = parentList;
        this.recyclerViewInterface = recyclerViewInterface;
        this.context=context;
    }


    @NonNull
    @Override
    public parentAdapter.ViewHlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //it will return a child type item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.parent_rv_layout,parent,false);
        return new ViewHlder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull parentAdapter.ViewHlder holder, int position) {
        // set the title and the recycler View and the my_biblio_btn_remove

        holder.parent_title.setText(parentList.get(position).getTitle());

        //set The recyclerView Holder

        childAdapter child_adapter  = new childAdapter(context,parentList.get(position).getChildList(),recyclerViewInterface);
        //setting the orientation
        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.rv_child.getContext(),LinearLayoutManager.HORIZONTAL,false);
        layoutManager.setInitialPrefetchItemCount(parentList.get(position).getChildList().size());

        holder.rv_child.setLayoutManager(layoutManager);
        holder.rv_child.setAdapter(child_adapter);
        holder.rv_child.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return parentList.size();
    }

    ///////////////////////////
    public static class ViewHlder extends RecyclerView.ViewHolder {

        RecyclerView rv_child;
        TextView parent_title;
        Button btn_more;


        public ViewHlder(@NonNull View itemView) {
            super(itemView);

            rv_child=itemView.findViewById(R.id.rv_child);
            parent_title = itemView.findViewById(R.id.tv_title);
            btn_more = itemView.findViewById(R.id.bt_more);
            Context context = btn_more.getContext();

            //parent_title.getText().equals(); if we want to specify

            btn_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                   Intent intent = new Intent(context, Livres_listes.class);

                    if(parent_title.getText().equals("Mathématiques")){

                        sendData(intent,homeEtud_Activity.math_List);

                    } else if(parent_title.getText().equals("Physique")){

                        sendData(intent,homeEtud_Activity.physic_List);

                    }else if(parent_title.getText().equals("Informatique")){

                        sendData(intent,homeEtud_Activity.computerScience_List);

                    }else if(parent_title.getText().equals("Biologie")){

                        sendData(intent,homeEtud_Activity.biology_List);

                    }else if(parent_title.getText().equals("Giologie")){

                        sendData(intent,homeEtud_Activity.geology_List);

                    }else if(parent_title.getText().equals("Chimie")){

                        sendData(intent,homeEtud_Activity.chemistry_List);

                    }
                    context.startActivity(intent);
                }
            });
        }
        private void sendData(Intent intent,ArrayList<Livre_Model> list){

            intent.putExtra("livre_list_size",list.size());

            for(int i=0;i<list.size();i++){
                intent.putExtra("livre_list_Image"+i,list.get(i).getImageCover());
                intent.putExtra("livre_list_Id"+i, list.get(i).getId_livre());
                intent.putExtra("livre_list_title"+i,list.get(i).getTitle());
                intent.putExtra("livre_list_discipline"+i,list.get(i).getDiscipline());
                intent.putExtra("livre_list_description"+i,list.get(i).getDescription());
                intent.putExtra("livre_list_auteur"+i, list.get(i).getAuteur());
                intent.putExtra("livre_list_disponible"+i, list.get(i).getDisponible());
                intent.putExtra("livre_list_numEx"+i, list.get(i).getNum_exemplaire());
            }
        }

    }

}
