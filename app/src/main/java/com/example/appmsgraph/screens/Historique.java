package com.example.appmsgraph.screens;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.appmsgraph.R;

import java.util.Objects;

public class Historique extends AppCompatActivity {

    /* UI */
    ActionBar actionBar;
    FloatingActionButton addVisite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique_visite);

        Intent intent = getIntent();
        String nameTitle = intent.getStringExtra("someObject");

        // actionBar
        actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setTitle(nameTitle);
        // getDrawable warning
//        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.toolbar_visibility));
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
}
