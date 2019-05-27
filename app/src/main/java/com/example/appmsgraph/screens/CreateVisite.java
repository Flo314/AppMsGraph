package com.example.appmsgraph.screens;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appmsgraph.R;
import com.example.appmsgraph.model.Value_;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class CreateVisite extends AppCompatActivity {

    /* UI */
    private ActionBar actionBar;
    private Spinner collab;
    private Spinner type_visite;
    private RatingBar note;
    private TextView editDate;
    private EditText commentaire;

    /*intent*/
    private String datalist;
    private String nameTitle;
    private String prenom;
    private String tok;
    private String id;
    private String histo;
    private String visite;
    private Boolean Uniqid;

    // DatePicker
    private int mYear, mMonth, mDay;

    /*Debug*/
    private final String TAG = CreateVisite.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_visite);

        // actionBar
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.toolbar_visibility));
        actionBar.setTitle("Visite");

        // Data qui vient de Historique
        Intent intent = getIntent();
        // true = cardView
        Uniqid = intent.getBooleanExtra("Uniqid", true);
//        datalist = intent.getStringExtra("datalist");
        nameTitle = intent.getStringExtra("title");
        prenom = intent.getStringExtra("prenom");
        tok = intent.getStringExtra("tok");
        id = intent.getStringExtra("id");
        visite = intent.getStringExtra("visite");
        histo = intent.getStringExtra("histo");
//        Log.d(TAG, "Datalist: " + datalist.toString());

        // editText fomulaire
        collab = findViewById(R.id.collab);
        type_visite = findViewById(R.id.type_visite);
//        note = findViewById(R.id.note);
        commentaire = findViewById(R.id.commentaire);

        // picker date formulaire
        editDate = findViewById(R.id.date_visite);
        if (Uniqid) {
            editDate.setText(visite);
            commentaire.setText(histo);
        }
        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDate(v);
            }
        });
    }

    // Datepiker
    private void getDate(View v) {
        DatePickerDialog dpd = new DatePickerDialog(v.getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar myCalendar = Calendar.getInstance();
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        myCalendar.set(Calendar.MONTH, month);
                        String myFormat = "dd/MM/yyyy";
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
                        editDate.setText(sdf.format((myCalendar.getTime())));
                    }
                }, mYear, mMonth, mDay);
        dpd.getDatePicker().setMinDate(System.currentTimeMillis());
        dpd.show();
    }

}
