package com.example.appmsgraph;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appmsgraph.model.Fields;
import com.example.appmsgraph.model.Value_;

import java.util.ArrayList;
import java.util.List;

/**
 * adaptateur qui sera utilisé par RecyclerView pour afficher la liste des collaborateurs
 */
public class CollaboratorAdapter extends RecyclerView.Adapter<CollaboratorAdapter.CollaboratorViewHolder> {

    private ArrayList<Fields> dataList;

    public CollaboratorAdapter(ArrayList<Fields> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public CollaboratorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.fragment_liste_collaborators, parent, false);
        return new CollaboratorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CollaboratorViewHolder holder, int position) {
        holder.textnamecollab.setText(dataList.get(position).getTitle());
        holder.textlastvisite.setText(dataList.get(position).getPrenom());
        holder.textdate.setText(dataList.get(position).getVisite());

    }

    @Override
    public int getItemCount() {
        if(dataList != null){
            return dataList.size();
        }
        return 0;
     }

    // récupère tous les item de la view
    public class CollaboratorViewHolder extends RecyclerView.ViewHolder {

        TextView textnamecollab, textlastvisite, textdate;

        public CollaboratorViewHolder(@NonNull View itemView) {
            super(itemView);
            textnamecollab = itemView.findViewById(R.id.textnamecollab);
            textlastvisite = itemView.findViewById(R.id.textlastvisite);
            textdate = itemView.findViewById(R.id.textdate);
        }
    }
}
