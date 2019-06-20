package com.example.appmsgraph.screens;

import android.content.Intent;
import android.support.annotation.NonNull;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appmsgraph.R;
import com.example.appmsgraph.VisiteAdapter;
import com.example.appmsgraph.modelSharepoint.Value;
import com.example.appmsgraph.modelcustom.VisiteObject;
import com.example.appmsgraph.network.GetDataService;
import com.example.appmsgraph.network.RetrofitInstance;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Historique extends AppCompatActivity implements VisiteAdapter.ListItemClickListenerVisite {

    /* UI */
    private ActionBar actionBar;
    private TextView historique;
    private CardView cardView;
    private FloatingActionButton createVisite;
    private VisiteAdapter adapter;
    private RecyclerView recyclerView;
    private ImageView deleteItem;

    /*Debug*/
    private final String TAG = Historique.class.getSimpleName();

    /*Data*/
    private String nameTitle;
    private String prenom;
    private String authHeader;
    private String id;
    public static String histo;
    private String visite;

    public static List<VisiteObject> visiteObjectList;
    public static int position;


    VisiteObject visiteObject = new VisiteObject();


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

        Log.d(TAG, "Element ListHisto = " + " " + visiteObject.getVisitList() + "\n");

        visiteObjectList = visiteObject.setListAuBonFormat(histo);

        // mise en page du recyclerview
        recyclerView = findViewById(R.id.recyclerviewHistorique);
        adapter = new VisiteAdapter(visiteObjectList, this, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Historique.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        recyclerView.setVisibility(View.VISIBLE);


        // actionBar
        actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setTitle(nameTitle);
        actionBar.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.toolbar_visibility));

        // Floating action button qui demarre l'activity AddVisite (ajout)
        createVisite = findViewById(R.id.createVisite);
        createVisite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addIntent = new Intent(getApplicationContext(), AddVisite.class);
                addIntent.putExtra("Tok", authHeader);
                addIntent.putExtra("id", id);
                addIntent.putExtra("title", nameTitle);
                addIntent.putExtra("prenom", prenom);
                startActivity(addIntent);
            }
        });
    }

    @Override
    public void onListItemClickVisite(int clickedItemIndex) {
        Intent updateintent = new Intent(getApplicationContext(), UpdateVisite.class);
        VisiteObject clickItem = visiteObjectList.get(clickedItemIndex);
        Log.d(TAG, "Item clicked: " + clickItem);

        updateintent.putExtra("title", nameTitle);
        updateintent.putExtra("prenom", prenom);
        updateintent.putExtra("Tok", authHeader);
        updateintent.putExtra("id", id);
        updateintent.putExtra("date", visiteObjectList.get(clickedItemIndex).getDate());
        updateintent.putExtra("type", visiteObjectList.get(clickedItemIndex).getType());
        updateintent.putExtra("note", visiteObjectList.get(clickedItemIndex).getNote());
        updateintent.putExtra("comment", visiteObjectList.get(clickedItemIndex).getComment());

        // récupérer la position de l'élément cliqué
        Historique.position = clickedItemIndex;

        startActivity(updateintent);
    }

    /* Cycle de vie Activity
     * =======================*/

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "OnResume called ");
        recyclerView.setAdapter(new VisiteAdapter(visiteObject.setListAuBonFormat(histo), this, this));
        Log.d(TAG, "ACTIVITY HISTORIQUE VALUE HISTO: " + visiteObject.getVisitList().toString());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "OnPause called ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "OnStop called ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "OnDestroy called ");
    }


}
