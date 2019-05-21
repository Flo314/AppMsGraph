package com.example.appmsgraph;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appmsgraph.model.Fields;
import com.example.appmsgraph.model.Value_;
import com.example.appmsgraph.utils.ItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * adaptateur qui sera utilisé par RecyclerView pour afficher la liste des collaborateurs
 */
public class CollaboratorAdapter extends RecyclerView.Adapter<CollaboratorAdapter.CollaboratorViewHolder> {

    private ArrayList<Value_> dataList;
    private Context context;

    public CollaboratorAdapter(ArrayList<Value_> dataList) {
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

        // utiliser lorsqu'on veux mettre chaque donnée d'item dans la même activity
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                String name = dataList.get(position).getFields().getTitle();
                String prenom = dataList.get(position).getFields().getPrenom();
                String histo = dataList.get(position).getFields().getHistoriquevisite();

                Intent intent = new Intent(context, CreateVisite.class);
                intent.putExtra("iTitle", name);
                intent.putExtra("iTitle", prenom);
                intent.putExtra("iTitle", histo);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(dataList != null){
            return dataList.size();
        }
        return 0;
     }

    // récupère tous les item de la view
    public class CollaboratorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textnamecollab, textlastvisite, textdate;
        ItemClickListener itemClickListener;

        public CollaboratorViewHolder(@NonNull View itemView) {
            super(itemView);
            textnamecollab = itemView.findViewById(R.id.textnamecollab);
            textlastvisite = itemView.findViewById(R.id.textlastvisite);
            textdate = itemView.findViewById(R.id.textdate);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClick(v, getLayoutPosition());
        }

        public void setItemClickListener(ItemClickListener ic){
            this.itemClickListener = ic;
        }
    }
}
