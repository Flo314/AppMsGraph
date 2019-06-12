package com.example.appmsgraph.screens;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.appmsgraph.R;
import com.example.appmsgraph.VisiteAdapter;
import com.example.appmsgraph.modelcustom.VisiteObject;

import java.util.ArrayList;
import java.util.Objects;

public class Historique extends AppCompatActivity {

    /* UI */
    ActionBar actionBar;
    TextView historique;
    CardView cardView;
    FloatingActionButton addVisit;
    private RecyclerView recyclerView;

    /*Debug*/
    private final String TAG = Historique.class.getSimpleName();

    /*Data*/
    private String nameTitle;
    private String prenom;
    private String authHeader;
    private String id;
    private String histo;
    private String visite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique_visite);

        // Data qui vient de MainActivity
        Intent intents = getIntent();
        nameTitle = intents.getStringExtra("title");
        prenom = intents.getStringExtra("prenom");
        authHeader = intents.getStringExtra("Tok");
        id = intents.getStringExtra("id");
        histo = intents.getStringExtra("histo");
        Log.d(TAG, "Histo de MainActivity = " + histo + "\n");
        visite = intents.getStringExtra("visite");
//        Toast.makeText(getApplicationContext(), "token: " + authHeader , Toast.LENGTH_SHORT).show();

//        list = histo.split(",");
//        for(int j = 0; j < listHisto.length; j++){
//            Log.d(TAG, "Element ListHisto = " + " " + listHisto[j] + "\n");
//        }

        // actionBar
        actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setTitle(nameTitle);
        actionBar.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.toolbar_visibility));

        // Je récupère mon RecyclerView pour pouvoir agir dessus
        recyclerView = findViewById(R.id.recyclerviewHistorique);
        recyclerView.setAdapter(new VisiteAdapter(VisiteObject.getVisitList()));

        // Floating action button qui demarre l'activity CreateVisite (ajout)
        addVisit = findViewById(R.id.addVisit);
        addVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), CreateVisite.class);
//                intent.putExtra("Uniqid",false);
//                intent.putExtra("Tok", authHeader);
//                startActivity(intent);
//                Log.d(TAG, "Lance CreateVisite...");
                // Ce listener nous permet de lancer l'activité d'ajout lorsqu'il est cliqué
                Intent addIntent = new Intent(getApplicationContext(), AddVisite.class);
                startActivity(addIntent);
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
//                intent.putExtra("datalist",datalist);
                intent.putExtra("title", nameTitle);
                intent.putExtra("prenom", prenom);
                intent.putExtra("Tok", authHeader);
                intent.putExtra("id", id);
                intent.putExtra("visite", visite);
                startActivity(intent);
            }
        });
    }


}
