package com.example.appmsgraph;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class CreateVisite extends AppCompatActivity {

    /* UI */
    ImageView imageUser;
    TextView name, prenom, historic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_visite);

        // actionBar
        ActionBar actionBar = getSupportActionBar();

        imageUser = findViewById(R.id.user);
        name = findViewById(R.id.name);
        prenom = findViewById(R.id.prenom);

        // Intent
        Intent intent = getIntent();
        String nameCo = intent.getStringExtra("iTitle");
        String prenomCo = intent.getStringExtra("iPrenom");

        // title actionBar
        actionBar.setTitle("Historique");

        // set data dans la view
        name.setText(nameCo);
        prenom.setText(prenomCo);
    }
}
