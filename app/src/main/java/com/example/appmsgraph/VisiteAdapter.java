package com.example.appmsgraph;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appmsgraph.modelcustom.VisiteObject;

import java.util.ArrayList;
import java.util.List;

public class VisiteAdapter extends RecyclerView.Adapter<VisiteAdapter.VisiteViewHolder> {

    List<VisiteObject> list;

    final private VisiteAdapter.ListItemClickListenerVisite onClickListener;

    // interface qui définit l'auditeur click recyclerview
    public interface ListItemClickListenerVisite{
        void onListItemClickVisite(int clickedItemIndex);
    }

    public VisiteAdapter(List<VisiteObject> list, VisiteAdapter.ListItemClickListenerVisite listener) {
        this.list = list;
        this.onClickListener = listener;
    }

    @NonNull
    @Override
    public VisiteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_list_historique, parent, false);
        return new VisiteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VisiteViewHolder holder, int position) {
        holder.datevisite.setText(list.get(position).getDate());
        holder.typevisite.setText(list.get(position).getType());
        holder.notevisite.setText(list.get(position).getNote());
        holder.commentvisite.setText(list.get(position).getComment());
    }

    @Override
    public int getItemCount() {
        if(list != null){
            return list.size();
        }
        return 0;
    }

    // ViewHolder
    class VisiteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView typevisite, datevisite,notevisite,commentvisite;

        VisiteViewHolder(@NonNull View itemView) {
            super(itemView);

            datevisite = itemView.findViewById(R.id.textdate);
            typevisite = itemView.findViewById(R.id.texttype);
            notevisite = itemView.findViewById(R.id.textnote);
            commentvisite = itemView.findViewById(R.id.textcomment);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            onClickListener.onListItemClickVisite(clickedPosition);
        }
    }
}
