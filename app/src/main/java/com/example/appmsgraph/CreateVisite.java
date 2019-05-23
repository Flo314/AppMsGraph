package com.example.appmsgraph;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

public class CreateVisite extends AppCompatActivity {

    /* UI */
    ActionBar actionBar;
    FloatingActionButton addVisite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_visite);

        addVisite = findViewById(R.id.add_visite);

        Intent intent = getIntent();
        String nameTitle = intent.getStringExtra("someObject");

        // actionBar
        actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setTitle(nameTitle);
        // getDrawable warning
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.toolbar_visibility));

    }
}
