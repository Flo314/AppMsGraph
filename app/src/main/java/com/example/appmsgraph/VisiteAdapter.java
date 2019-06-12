package com.example.appmsgraph;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appmsgraph.modelcustom.VisiteObject;

import java.util.List;

public class VisiteAdapter extends RecyclerView.Adapter<VisiteViewHolder> {

    List<VisiteObject> list;

    //ajouter un constructeur prenant en entrée une liste
    public VisiteAdapter(List<VisiteObject> list) {
        this.list = list;
    }

    @NonNull
    @Override
    //cette fonction permet de créer les viewHolder
    //et par la même indiquer la vue à inflater (à partir des layout xml)
    public VisiteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_list_historique,viewGroup,false);
        return new VisiteViewHolder(view);
    }

    @Override
    //c'est ici que nous allons remplir notre cellule avec le texte de chaque VisiteObjects
    public void onBindViewHolder(@NonNull VisiteViewHolder visiteViewHolder, int position) {
        VisiteObject visiteObject = list.get(position);
        visiteViewHolder.bind(visiteObject);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
