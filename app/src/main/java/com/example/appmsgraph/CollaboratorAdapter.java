package com.example.appmsgraph;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appmsgraph.model.Value_;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * adaptateur qui sera utilisé par RecyclerView pour afficher la liste des collaborateurs
 */
public class CollaboratorAdapter extends RecyclerView.Adapter<CollaboratorAdapter.CollaboratorViewHolder> implements Filterable {

    private ArrayList<Value_> dataList;
    // list pour filtrer
    private ArrayList<Value_> dataListFull;
    Context context;

    final private ListItemClickListener onClickListener;

    // interface qui définit l'auditeur click recyclerview
    public interface ListItemClickListener{
        void onListItemClick(int clickedItemIndex);
    }

    public CollaboratorAdapter(ArrayList<Value_> dataList, ListItemClickListener listener) {
        this.dataList = dataList;
        dataListFull = new ArrayList<>(dataList);
        this.onClickListener = listener;
    }

    @NonNull
    @Override
    public CollaboratorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.fragment_liste_collaborators, parent, false);
        return new CollaboratorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CollaboratorViewHolder holder, int position) {
        holder.textnamecollab.setText(dataList.get(position).getFields().getTitle());
        holder.textlastvisite.setText(dataList.get(position).getFields().getPrenom());
        holder.textdate.setText(dataList.get(position).getFields().getVisite());

        // Todo pour la date de visite
//        final Drawable drawablegreen = ContextCompat.getDrawable(context, R.drawable.cerclebackgroundgreen);
//        final Drawable drawablered = ContextCompat.getDrawable(context, R.drawable.cerclebackgroungred);
//        final Drawable drawableorange = ContextCompat.getDrawable(context, R.drawable.cerclebackgroundorange);
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//        Date strDate = null;
//        try {
//            strDate = sdf.parse(dataList.get(position).getFields().getVisite());
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        assert strDate != null;
//        if (System.currentTimeMillis() < strDate.getTime()) {
//            holder.textdate.setBackground(drawablegreen);
//        }else{
//            holder.textdate.setBackground(drawablered);
//        }
    }

    // Récupère le nombre de jours entre deux dates
    public static double getDaysBetweenTwoDate(Date srcDate, Date destDate) {
        long startTime = srcDate.getTime();
        long destTime = destDate.getTime();
        long deltaTime = destTime - startTime;
        long oneDayTime = 24 * 60 * 60 * 1000;
        double deltaDay = Math.abs(deltaTime / oneDayTime);
        return deltaDay;
    }

    @Override
    public int getItemCount() {
        if(dataList != null){
            return dataList.size();
        }
        return 0;
     }

     // Filtre sur le nom
    @Override
    public Filter getFilter() {
        return fiterName;
    }

    private Filter fiterName = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Value_> filteredList = new ArrayList<>();
            if(constraint == null || constraint.length() == 0){
                filteredList.addAll(dataListFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(Value_ item : dataListFull){
                    if(item.getFields().getTitle().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            dataList.clear();
            dataList.addAll((Collection<? extends Value_>) results.values);
            notifyDataSetChanged();
        }
    };


    // récupère tous les item de la view
    public class CollaboratorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textnamecollab, textlastvisite, textdate;

        public CollaboratorViewHolder(@NonNull View itemView) {
            super(itemView);
            textnamecollab = itemView.findViewById(R.id.textnamecollab);
            textlastvisite = itemView.findViewById(R.id.textlastvisite);
            textdate = itemView.findViewById(R.id.textdate);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            onClickListener.onListItemClick(clickedPosition);
        }
    }

}
