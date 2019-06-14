package com.example.appmsgraph.screens;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.appmsgraph.R;

public class UpdateVisite extends AppCompatActivity {

    /* UI */
    ActionBar actionBar;
    private TextView dateupdate, nameupdate;
    private EditText commentupdate;
    private Spinner typeupdate;
    private RatingBar noteupdate;

    /*DATA*/
    private String date;
    private String name;
    private String prenom;
    private String note;
    private String type;
    private String comment;
    private String authHeader;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_visite);

        // actionBar
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.toolbar_visibility));
        actionBar.setTitle("Modfier une Visite");

        // Data qui vient de Historique items
        Intent intentsupdate = getIntent();
        name = intentsupdate.getStringExtra("title");
        prenom = intentsupdate.getStringExtra("prenom");
        authHeader = intentsupdate.getStringExtra("Tok");
        id = intentsupdate.getStringExtra("id");
        date = intentsupdate.getStringExtra("date");
        comment = intentsupdate.getStringExtra("comment");
        note = intentsupdate.getStringExtra("note");
        type = intentsupdate.getStringExtra("type");

        // chargement UI
        nameupdate = findViewById(R.id.updatecollaborator);
        nameupdate.setText(name + " " + prenom);
        dateupdate = findViewById(R.id.updatedatevisite);
        dateupdate.setText(date);
        typeupdate = findViewById(R.id.updatetypevisite);
        typeupdate.setEnabled(true);
        noteupdate = findViewById(R.id.updatenotevisite);
        noteupdate.setRating(Float.parseFloat(note));
        commentupdate = findViewById(R.id.updatecommentairevisite);
        commentupdate.setText(comment);
    }
}
