package com.example.gestion_biblio.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gestion_biblio.Modules.Livre_Model;
import com.example.gestion_biblio.Modules.Livres_modelClass;
import com.example.gestion_biblio.R;

import java.util.ArrayList;
import java.util.List;


public class childAdapter extends RecyclerView.Adapter<childAdapter.ViewHlder> {
    // child adapter for one recycler view
    // child data will contains a   child typeLise and a Context
    // setOnClickListener  needs the Context

    ArrayList<Livre_Model> childList;
    private Nested_RV_interface recyclerViewInterface;
    Context context;


    public childAdapter(Context context,ArrayList<Livre_Model> childList, Nested_RV_interface recyclerViewInterface) {

        this.context= context;
        this.childList = childList;
        this.recyclerViewInterface = recyclerViewInterface;
    }


    @NonNull
    @Override
    public childAdapter.ViewHlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // this is where we give a look to our childLayout
        //it will return a child type item
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.child_rv_item_layout,parent,false);

        //  View view = LayoutInflater.from(context).inflate(R.layout.child_rv_item_layout,null,false);

        return new ViewHlder(view,recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull childAdapter.ViewHlder holder, int position) {
        // assigning the values to the view we created in the child_rv_item_layout based on the position of the recycler view
        // set The DATA
        Glide.with(context).load(childList.get(position).getImageCover()).into(holder.iv_child_item);
    }

    @Override
    public int getItemCount() {
        return childList.size();
    }

    ///////////////////////////
    public class ViewHlder extends RecyclerView.ViewHolder {
        // grabbing all the views from our child_rv_item_layout //  like in the onCreate method

        ImageView iv_child_item;

        public ViewHlder(@NonNull View itemView, Nested_RV_interface recyclerViewInterface) {
            super(itemView);

            iv_child_item=itemView.findViewById(R.id.iv_child_item);
            iv_child_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("§§§§ GAT 0","ITEM CLICKED");

                    //Toast.makeText(iv_child_item.getContext(), "item"+childList.get(getAdapterPosition()).getTitle(), Toast.LENGTH_SHORT).show();
                    recyclerViewInterface.onItemClicked(view,childList.get(getAdapterPosition()));
                }
            });

        }
    }
}
