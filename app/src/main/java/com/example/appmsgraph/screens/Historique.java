package com.example.appmsgraph.screens;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.appmsgraph.R;

import java.util.Objects;

public class Historique extends AppCompatActivity {

    /* UI */
    ActionBar actionBar;
    TextView historique;
    CardView cardView;
    FloatingActionButton addVisit;

    /*Debug*/
    private final String TAG = Historique.class.getSimpleName();

    /*Data*/
    private String nameTitle;
    private String prenom;
    private String tok;
    private String id;
    private String visite;
    private String histo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique_visite);

        // Data qui vient de MainActivity
        Intent intents = getIntent();
        nameTitle = intents.getStringExtra("title");
        prenom = intents.getStringExtra("prenom");
        tok = intents.getStringExtra("tok");
        id = intents.getStringExtra("id");
        visite = intents.getStringExtra("visite");
        histo = intents.getStringExtra("histo");
//        Toast.makeText(this, "token: " + tok , Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "id + histo: " + " " + id + " " +histo , Toast.LENGTH_SHORT).show();

        // actionBar
        actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setTitle(nameTitle);
        actionBar.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.toolbar_visibility));

        // Floating action button qui demarre l'activity CreateVisite
        addVisit = findViewById(R.id.addVisit);
        addVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateVisite.class);
                intent.putExtra("Uniqid",false);
                startActivity(intent);
                Log.d(TAG, "Lance CreateVisite...");
            }
        });

        // affiche dans l'activity Historique le champ historique visite de la liste sharepoint
        historique = findViewById(R.id.historique);
        historique.setText(histo);

        cardView = findViewById(R.id.clickedCardHistorique);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateVisite.class);
                intent.putExtra("Uniqid",true);
                intent.putExtra("title", nameTitle);
                intent.putExtra("prenom", prenom);
                intent.putExtra("tok", tok);
                intent.putExtra("id", id);
                intent.putExtra("visite", visite);
                intent.putExtra("histo", histo);
                startActivity(intent);
            }
        });

    }


}
