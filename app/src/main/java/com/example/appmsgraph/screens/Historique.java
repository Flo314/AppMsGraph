package com.example.appmsgraph.screens;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.Toast;

import com.example.appmsgraph.CollaboratorAdapter;
import com.example.appmsgraph.HistoriqueAdapter;
import com.example.appmsgraph.R;
import com.example.appmsgraph.model.ListHistorique;
import com.example.appmsgraph.utils.CompareDate;

import java.util.ArrayList;
import java.util.List;
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
    private String authHeader;
    private String id;
    private String histo;
    private String visite;
    private HistoriqueAdapter adapter;
    private RecyclerView recyclerView;
    String[] listHisto = null;

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
        Log.d(TAG, "Histo de MainActivity = " + histo);
        visite = intents.getStringExtra("visite");
//        Toast.makeText(getApplicationContext(), "token: " + authHeader , Toast.LENGTH_SHORT).show();

        listHisto = histo.split(",");
        for(int i = 0; i < listHisto.length; i++){
            Log.d(TAG, "List = " + listHisto[i]);
        }


//        recyclerView = findViewById(R.id.recyclerviewHistorique);
//        adapter = new HistoriqueAdapter(list);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Historique.this);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(adapter);

        // actionBar
        actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setTitle(nameTitle);
        actionBar.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.toolbar_visibility));

        // Floating action button qui demarre l'activity CreateVisite (ajout)
        addVisit = findViewById(R.id.addVisit);
        addVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateVisite.class);
                intent.putExtra("Uniqid",false);
                intent.putExtra("Tok", authHeader);
                startActivity(intent);
                Log.d(TAG, "Lance CreateVisite...");
            }
        });

        // affiche dans l'activity Historique le champ historique visite de la liste sharepoint
//        historique = findViewById(R.id.historique);
//        historique.setText(histo);

//        cardView = findViewById(R.id.clickedCardHistorique);
//        cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), CreateVisite.class);
//                intent.putExtra("Uniqid",true);
////                intent.putExtra("datalist",datalist);
//                intent.putExtra("title", nameTitle);
//                intent.putExtra("prenom", prenom);
//                intent.putExtra("Tok", authHeader);
//                intent.putExtra("id", id);
//                intent.putExtra("visite", visite);
//                startActivity(intent);
//            }
//        });
    }


}
