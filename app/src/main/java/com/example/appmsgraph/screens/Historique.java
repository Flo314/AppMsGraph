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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appmsgraph.CollaboratorAdapter;
import com.example.appmsgraph.R;
import com.example.appmsgraph.VisiteAdapter;
import com.example.appmsgraph.modelSharepoint.Value_;
import com.example.appmsgraph.modelcustom.VisiteObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Historique extends AppCompatActivity implements VisiteAdapter.ListItemClickListenerVisite{

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
    private List<VisiteObject> visiteObjectList;

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

        visiteObjectList = VisiteObject.getListAuBonFormat(histo);

        Log.d(TAG, "Element ListHisto = " + " " + VisiteObject.getListAuBonFormat(histo).toString() + "\n");

        // affiche dans l'activity Historique le champ historique visite de la liste sharepoint
//        historique = findViewById(R.id.historique);
//        historique.setText(histo);
        recyclerView = findViewById(R.id.recyclerviewHistorique);
        adapter = new VisiteAdapter(visiteObjectList,this, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Historique.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(View.VISIBLE);
//            Log.d(TAG, "VisiteList: " + list);

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

        startActivity(updateintent);
    }

    /* Cycle de vie Activity
     * =======================*/

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "OnResume called ");
        recyclerView.setAdapter(new VisiteAdapter(VisiteObject.getListAuBonFormat(histo),this, this));
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
