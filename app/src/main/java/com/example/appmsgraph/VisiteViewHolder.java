package com.example.appmsgraph;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.appmsgraph.modelcustom.VisiteObject;

public class VisiteViewHolder extends RecyclerView.ViewHolder {

    private Spinner spinnercollab, typevisite;
    private TextView date;
    private EditText comment;
    private RatingBar note;

    //itemView est la vue correspondante à 1 cellule
    public VisiteViewHolder(@NonNull View itemView) {
        super(itemView);

        spinnercollab = itemView.findViewById(R.id.spinnercollab);
        date = itemView.findViewById(R.id.datevisite);
        typevisite = itemView.findViewById(R.id.typevisite);
        note = itemView.findViewById(R.id.notevisite);
        comment = itemView.findViewById(R.id.commentairevisite);
    }

    //puis ajouter une fonction pour remplir la cellule en fonction d'un CapitalObject
    public void bind(VisiteObject visiteObject){
        date.setText(visiteObject.getDate());
        note.setRating(Float.parseFloat(visiteObject.getNote()));
        comment.setText(visiteObject.getComment());
    }
}
