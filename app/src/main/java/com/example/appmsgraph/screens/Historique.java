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
import android.widget.Toast;

import com.example.appmsgraph.CollaboratorAdapter;
import com.example.appmsgraph.R;
import com.example.appmsgraph.VisiteAdapter;
import com.example.appmsgraph.modelcustom.VisiteObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Historique extends AppCompatActivity {

    /* UI */
    ActionBar actionBar;
    TextView historique;
    CardView cardView;
    FloatingActionButton createVisite;
    private VisiteAdapter adapter;
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
    List<VisiteObject> list;
    String[] splitList;


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

//        splitList = histo.split("!");
//        for (int i = 0; i < splitList.length; i++) {
//              Log.d(TAG, "Element ListHisto = " + " " + splitList[i] + "\n");
//        }
//        Log.d(TAG, "Element ListHisto = " + " " + Arrays.toString(splitList) + "\n");

        VisiteObject visiteObject = new VisiteObject();
        visiteObject.getListAuBonFormat(histo);
        Log.d(TAG, "Element ListHisto = " + " " + visiteObject.getListAuBonFormat(histo).toString() + "\n");

           // affiche dans l'activity Historique le champ historique visite de la liste sharepoint
            historique = findViewById(R.id.historique);
            historique.setText(histo);
            recyclerView = findViewById(R.id.recyclerviewHistorique);
            adapter = new VisiteAdapter(VisiteObject.getVisitList());
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Historique.this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
            recyclerView.setVisibility(View.VISIBLE);
            Log.d(TAG, "VisiteList: " + list);

        // actionBar
        actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setTitle(nameTitle);
        actionBar.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.toolbar_visibility));

        // Floating action button qui demarre l'activity CreateVisite (ajout)
        createVisite = findViewById(R.id.createVisite);
        createVisite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), CreateVisite.class);
//                intent.putExtra("Uniqid",false);
//                intent.putExtra("Tok", authHeader);
//                startActivity(intent);
//                Log.d(TAG, "Lance CreateVisite...");
                // Ce listener nous permet de lancer l'activité d'ajout lorsqu'il est cliqué
                Intent addIntent = new Intent(getApplicationContext(), AddVisite.class);
                addIntent.putExtra("Tok", authHeader);
                startActivity(addIntent);
            }
        });

        cardView = findViewById(R.id.clickedCardHisto);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateVisite.class);
                intent.putExtra("Uniqid", true);
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

    @Override
    protected void onResume(){
        super.onResume();
        recyclerView.setAdapter(new VisiteAdapter(VisiteObject.getVisitList()));
    }

    // test ajout object
//    private void ajouterVilles() {
//        VisiteObject.addVisite(new VisiteObject("2","13/06/2019","DOP","2","cool"));
//        VisiteObject.addVisite(new VisiteObject("2","13/06/2019","DOP","2","cool"));
//        VisiteObject.addVisite(new VisiteObject("2","13/06/2019","DOP","2","cool"));
//        VisiteObject.addVisite(new VisiteObject("2","13/06/2019","DOP","2","cool"));
//        VisiteObject.addVisite(new VisiteObject("2","13/06/2019","DOP","2","cool"));
//    }

}
