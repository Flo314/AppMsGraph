package com.example.appmsgraph.screens;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.appmsgraph.HistoriqueAdapter;
import com.example.appmsgraph.R;
import com.example.appmsgraph.model.Value;
import com.example.appmsgraph.model.Value_;
import com.example.appmsgraph.network.GetDataService;
import com.example.appmsgraph.network.RetrofitInstance;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Historique extends AppCompatActivity implements HistoriqueAdapter.ListItemClickListener {

    /* UI */
    ActionBar actionBar;
    FloatingActionButton addVisite;
    private HistoriqueAdapter adapter;
    private RecyclerView recyclerView;

    String tok;

    /*Data*/
    private ArrayList<Value_> datalistObj = new ArrayList<>();

    /*Debug*/
    private final String TAG = Historique.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique_visite);

        Intent intent = getIntent();
        String nameTitle = intent.getStringExtra("someObject");
        String tok = intent.getStringExtra("tok");
        //Toast.makeText(this, "token: " + tok , Toast.LENGTH_SHORT).show();

        // actionBar
        actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setTitle(nameTitle);
        actionBar.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.toolbar_visibility));

        // Floating action button
        addVisite = findViewById(R.id.add_visite);
        addVisite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateVisite.class);
                startActivity(intent);
            }
        });

    }

    private void loadDataList(ArrayList<Value_> datalist){
        recyclerView = findViewById(R.id.recyclerview_historique);
        adapter = new HistoriqueAdapter(datalist, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Historique.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        network();
    }

    /* Helper methods gèrent les appels réseaux
     * ================================================================= */

    private void network(){
        //Créer un identifiant pour l'interface RetrofitInstance
        GetDataService service = RetrofitInstance.getRetrofitInstance().create(GetDataService.class);
        // Appelez la méthode avec paramètre dans l'interface pour obtenir les données sur l'employé
        Call<Value> call = service.getCollaboratorsData(tok);
        Log.d(TAG, "starting retrofit request to graph");
        Log.d(TAG, call.request().url() + "");
        // Exécute la requête asynchrone
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                Log.d(TAG, "Response: " + response.message());
                Log.d(TAG, "Response: " + response.toString());
                loadDataList(response.body().getValue());
                datalistObj = response.body().getValue();
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                Toast.makeText(Historique.this, "Something went wrong...Please try later!", Toast.LENGTH_LONG).show();
                Log.e(TAG, "Failure: " + t.toString());
            }
        });
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Log.d(TAG, "clickItem: " + clickedItemIndex);
        // ouverture de HistoriqueActivity en lui passant le nom du collab pour le title de l'action bar
        Intent intent = new Intent(this, Historique.class);
        Value_ clickItem = datalistObj.get(clickedItemIndex);
        intent.putExtra("histo", clickItem.getFields().getHistoriquevisite());
        intent.putExtra("tok", tok);
        startActivity(intent);
    }
}
