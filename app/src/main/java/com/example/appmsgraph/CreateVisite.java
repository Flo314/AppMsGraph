package com.example.appmsgraph;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class CreateVisite extends AppCompatActivity {

    /* UI */
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_visite);

        // actionBar
        actionBar = getSupportActionBar();
        // getDrawable warning
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.toolbar_visibility));
    }
}
