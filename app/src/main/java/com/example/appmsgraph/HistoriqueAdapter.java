package com.example.appmsgraph;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class HistoriqueAdapter extends RecyclerView.Adapter<HistoriqueAdapter.HistoriqueViewHolder> {

    ArrayList<String> listHisto;

    public HistoriqueAdapter(ArrayList<String> listHisto) {
        this.listHisto = listHisto;
    }

    @NonNull
    @Override
    //  cette méthode permet de créer la vue à afficher, et de retourner, associé à ce composant, un objet ViewHolder
    public HistoriqueViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_list_historique, viewGroup, false);
        return new HistoriqueViewHolder(view);
    }

    @Override
    // cette méthode , permet d'afficher les données de l'article (l'item) dans la sous-vue courante
    public void onBindViewHolder(@NonNull HistoriqueViewHolder historiqueViewHolder, int position) {

    }

    @Override
    public int getItemCount() {
        if(listHisto != null){
            return listHisto.size();
        }
        return 0;
    }

    // récupère tous les item de la view
    // stocke la référence aux vues de présentation de carte qui doivent être modifiées de façon dynamique
    // lors de l'exécution du programme par une liste de données obtenues par un appel réseau
    class HistoriqueViewHolder extends RecyclerView.ViewHolder {

        TextView dateHisto, typeHisto, noteHisto, commentHisto;

        HistoriqueViewHolder(@NonNull View itemView) {
            super(itemView);
            dateHisto = itemView.findViewById(R.id.dateHisto);
            typeHisto = itemView.findViewById(R.id.typeHisto);
            noteHisto = itemView.findViewById(R.id.noteHisto);
            commentHisto = itemView.findViewById(R.id.commentHisto);

        }
    }

}
