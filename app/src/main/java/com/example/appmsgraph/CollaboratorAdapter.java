package com.example.appmsgraph;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appmsgraph.model.Value;

import java.util.List;

/**
 * adaptateur qui sera utilisé par RecyclerView pour afficher la liste des collaborateurs
 */
public class CollaboratorAdapter extends RecyclerView.Adapter<CollaboratorAdapter.CollaboratorViewHolder> {

    private List<Value> dataList;

    public CollaboratorAdapter(List<Value> dataList) {
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
        holder.textnamecollab.setText(dataList.get(position).getFields().getTitle());
        holder.textlastvisite.setText(dataList.get(position).getFields().getPrenom());
        holder.textdate.setText(dataList.get(position).getFields().getVisite());

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    // récupère tous les item de la view
    public class CollaboratorViewHolder extends RecyclerView.ViewHolder {

        TextView textnamecollab, textlastvisite, textdate;

        public CollaboratorViewHolder(@NonNull View itemView) {
            super(itemView);
            textnamecollab = (TextView) itemView.findViewById(R.id.textnamecollab);
            textlastvisite = (TextView) itemView.findViewById(R.id.textlastvisite);
            textdate = (TextView) itemView.findViewById(R.id.textdate);
        }
    }
}
