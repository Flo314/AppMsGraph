package com.example.appmsgraph;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appmsgraph.model.Value_;

import java.util.ArrayList;

public class HistoriqueAdapter extends RecyclerView.Adapter<HistoriqueAdapter.HistoriqueAdapterViewHolder> {

    private ArrayList<Value_> dataList;

    final private HistoriqueAdapter.ListItemClickListener onClickListener;

    // interface qui définit l'auditeur click recyclerview
    public interface ListItemClickListener{
        void onListItemClick(int clickedItemIndex);
    }

    public HistoriqueAdapter(ArrayList<Value_> dataList, HistoriqueAdapter.ListItemClickListener listener) {
        this.dataList = dataList;
        this.onClickListener = listener;
    }


    @NonNull
    @Override
    public HistoriqueAdapter.HistoriqueAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_historique_visite, parent, false);
        return new HistoriqueAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HistoriqueAdapterViewHolder holder, int position) {
        holder.historique.setText(dataList.get(position).getFields().getHistoriquevisite());
//        holder.date_visite.setText(dataList.get(position).getFields().getVisite());
//        holder.type_visite.setText(dataList.get(position).getFields().getPrenom());
//        holder.note.setText(dataList.get(position).getFields().getVisite());
//        holder.comment.setText(dataList.get(position).getFields().getVisite());
    }

    @Override
    public int getItemCount() {
        if(dataList != null){
            return dataList.size();
        }
        return 0;
    }

    // récupère tous les item de la view
    public class HistoriqueAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

//        TextView date_visite, type_visite, note, comment;
        TextView historique;

        public HistoriqueAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            historique = itemView.findViewById(R.id.historique);
//            date_visite = itemView.findViewById(R.id.date_visite);
//            type_visite = itemView.findViewById(R.id.type_visite);
//            note = itemView.findViewById(R.id.note);
//            comment = itemView.findViewById(R.id.commentaire);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            onClickListener.onListItemClick(clickedPosition);
        }
    }
}
