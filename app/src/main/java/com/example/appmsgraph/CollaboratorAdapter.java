package com.example.appmsgraph;


import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appmsgraph.modelSharepoint.Value_;
import com.example.appmsgraph.utils.CompareDate;

import java.util.ArrayList;
import java.util.Collection;


/**
 * permet de contenir l'ensemble des données à afficher dans le RecyclerView en gérant également ses mises à jour
 * adaptateur qui sera utilisé par RecyclerView pour afficher la liste des collaborateurs
 */
public class CollaboratorAdapter extends RecyclerView.Adapter<CollaboratorAdapter.CollaboratorViewHolder> implements Filterable{

    private ArrayList<Value_> dataList;
    // list pour filtrer
    private ArrayList<Value_> dataListFull;
    private CompareDate compareDate;

    private ArrayList<Value_> dataListOrange = new ArrayList<>();
    private ArrayList<Value_> dataListRed = new ArrayList<>();
    private ArrayList<Value_> dataListGreen = new ArrayList<>();
    private ArrayList<Value_> dataListBusinessManager = new ArrayList<>();
    private ArrayList<Value_> datalistResponsableTechnique = new ArrayList<>();

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
    //  cette méthode permet de créer la vue à afficher, et de retourner, associé à ce composant, un objet ViewHolder
    public CollaboratorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_liste_collaborators, parent, false);
        return new CollaboratorViewHolder(view);
    }

    // cette méthode , permet d'afficher les données de l'article (l'item) dans la sous-vue courante
    @Override
    public void onBindViewHolder(@NonNull final CollaboratorViewHolder holder, int position) {

        // position actuelle du premier item du recyclerview
        int i = 0;

        holder.textnamecollab.setText(dataList.get(position).getFields().getTitle());
        holder.textlastvisite.setText(dataList.get(position).getFields().getPrenom());
        holder.textdate.setText(dataList.get(position).getFields().getVisite());

        // compareDate instance permettant de comparer la date du jour avec une string au format dd/MM/yyyy
        compareDate = new CompareDate();
        // Traitement de la couleur de l'icon pour la date
        for (Value_ value : dataList) {
            // champ visite
            String visite = value.getFields().getVisite();
            if (visite != null && i <= position) {
                compareDate.getCompareDate(visite);
                holder.textdate.setTextColor(Color.GRAY);
                if (compareDate.TAG.equals("RED")) {
                    holder.imageindicatorred.setImageResource(R.drawable.ic_access_red);
                    holder.textdate.setTextColor(Color.RED);
                } else if (compareDate.TAG.equals("ORANGE")) {
                    holder.imageindicatorred.setImageResource(R.drawable.ic_access_orange);
                } else {
                    holder.imageindicatorred.setImageResource(R.drawable.ic_access_green);
                }
            }
            i++;
        }
    }

    @Override
    public int getItemCount() {
        if(dataList != null){
            return dataList.size();
        }
        return 0;
    }

    // filtre sur les collab du BM à la connexion
    public void updateListBusinessManager(String userConnect) {
        for (Value_ item : dataList){
            if(item.getFields().getBusinessManager() != null){
                if(item.getFields().getBusinessManager().equals(userConnect)){
                    dataListBusinessManager.add(item);
                }
            }
        }
        dataList.clear();
        dataList.addAll(dataListBusinessManager);
        notifyDataSetChanged();
    }

    // filtre sur les collab du RTU à la connexion
    public void updateListResponsableTechnique(String userConnect) {
        for (Value_ item : dataList){
            if(item.getFields().getRTU() != null){
                if(item.getFields().getRTU().equals(userConnect)){
                    datalistResponsableTechnique.add(item);
                }
            }
        }
        dataList.clear();
        dataList.addAll(datalistResponsableTechnique);
        notifyDataSetChanged();
    }

    // filtre sur les item red (rouge)
    public void updateListRed() {
        dataList.addAll(dataListBusinessManager);
        String visite;
        int position = 0;
        int i = dataList.size();
        compareDate = new CompareDate();
        for (Value_ item : dataList){
            visite = item.getFields().getVisite();
            if(visite != null && position <= i){
                compareDate.getCompareDate(visite);
                if (compareDate.TAG.equals("RED")){
                    dataListRed.add(item);
                }
            }
            position++;
        }
        dataList.clear();
        dataList.addAll(dataListRed);
        notifyDataSetChanged();
    }

    // filtre sur les item medium (orange)
    public void updateListOrange() {
        dataList.addAll(dataListBusinessManager);
        String visite;
        int position = 0;
        int i = dataList.size();
        compareDate = new CompareDate();
        for (Value_ item : dataList){
            visite = item.getFields().getVisite();
            if(visite != null && position <= i){
                compareDate.getCompareDate(visite);
                if (compareDate.TAG.equals("ORANGE")){
                    dataListOrange.add(item);
                }
            }
            position++;
        }
        dataList.clear();
        dataList.addAll(dataListOrange);
        notifyDataSetChanged();
    }

    // filtre sur les item good (vert)
    public void updateListGreen() {
        dataList.addAll(dataListBusinessManager);
        String visite;
        int position = 0;
        int i = dataList.size();
        compareDate = new CompareDate();
        for (Value_ item : dataList){
            visite = item.getFields().getVisite();
            if(visite != null && position <= i){
                compareDate.getCompareDate(visite);
                if (compareDate.TAG.equals("GREEN")){
                    dataListGreen.add(item);
                }
            }
            position++;
        }
        dataList.clear();
        dataList.addAll(dataListGreen);
        notifyDataSetChanged();
    }

     // Filtre sur le nom icon de la loupe dans l'actionBar
    @Override
    public Filter getFilter() {
        return fiterName;
    }

    // créer un nouveau filtre asynchrone
    private Filter fiterName = new Filter() {
        @Override
        // crée un nouvel Arraylist avec des données filtrées
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Value_> filteredList = new ArrayList<>();
            if(constraint == null || constraint.length() == 0){
                // avant il y avait datalistFull à la place de dataListBusinessManager
                // car le filtre de recherche se faisait sur la liste de tous les collaborateurs
                filteredList.addAll(dataListBusinessManager);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(Value_ item : dataListBusinessManager){
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
        // sert à notifier le fichier modifié
        protected void publishResults(CharSequence constraint, FilterResults results) {
            dataList.clear();
            dataList.addAll((Collection<? extends Value_>) results.values);
            notifyDataSetChanged();
        }
    };

    // récupère tous les item de la view
    // stocke la référence aux vues de présentation de carte qui doivent être modifiées de façon dynamique
    // lors de l'exécution du programme par une liste de données obtenues par un appel réseau
    public class CollaboratorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textnamecollab, textlastvisite, textdate;
        ImageView imageindicatorred, imageindicatororange, imageindicatorgreen;

        CollaboratorViewHolder(@NonNull View itemView) {
            super(itemView);
            textnamecollab = itemView.findViewById(R.id.textnamecollab);
            textlastvisite = itemView.findViewById(R.id.textlastvisite);
            textdate = itemView.findViewById(R.id.textdate);
            imageindicatorred = itemView.findViewById(R.id.imageindicator_red);
            imageindicatororange = itemView.findViewById(R.id.imageindicator_orange);
            imageindicatorgreen= itemView.findViewById(R.id.imageindicator_green);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            onClickListener.onListItemClick(clickedPosition);
        }
    }
}
