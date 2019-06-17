package com.example.appmsgraph;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appmsgraph.modelcustom.VisiteObject;
import com.example.appmsgraph.screens.Historique;
import com.example.appmsgraph.screens.UpdateVisite;

import java.util.ArrayList;
import java.util.List;

public class VisiteAdapter extends RecyclerView.Adapter<VisiteAdapter.VisiteViewHolder> {

    List<VisiteObject> list;
    Context context;

    final private VisiteAdapter.ListItemClickListenerVisite onClickListener;

    // interface qui définit l'auditeur click recyclerview
    public interface ListItemClickListenerVisite{
        void onListItemClickVisite(int clickedItemIndex);
    }

    public VisiteAdapter(List<VisiteObject> list, VisiteAdapter.ListItemClickListenerVisite listener, Context context) {
        this.list = list;
        this.onClickListener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public VisiteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_list_historique, parent, false);
        return new VisiteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VisiteViewHolder holder, final int position) {
        holder.datevisite.setText(list.get(position).getDate());
        holder.typevisite.setText(list.get(position).getType());
        holder.notevisite.setText(list.get(position).getNote());
        holder.commentvisite.setText(list.get(position).getComment());
        holder.deleteItem.setImageResource(R.drawable.ic_delete_forever_black_24dp);

        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAt(position);
                Toast.makeText(context,"Visite supprimée", Toast.LENGTH_LONG).show();
                Log.d("TEST DELETE", "TEST DELETE " + position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(list != null){
            return list.size();
        }
        return 0;
    }

    // supprime l'item de la liste
    private void removeAt(int position) {
        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, list.size());
    }

    // ViewHolder
    class VisiteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView typevisite, datevisite,notevisite,commentvisite;
        ImageView deleteItem;

        VisiteViewHolder(@NonNull View itemView) {
            super(itemView);

            datevisite = itemView.findViewById(R.id.textdate);
            typevisite = itemView.findViewById(R.id.texttype);
            notevisite = itemView.findViewById(R.id.textnote);
            commentvisite = itemView.findViewById(R.id.textcomment);
            deleteItem = itemView.findViewById(R.id.deleteItem);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            onClickListener.onListItemClickVisite(clickedPosition);
        }
    }
}
