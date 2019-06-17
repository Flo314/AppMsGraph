package com.example.appmsgraph.screens;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appmsgraph.R;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class UpdateVisite extends AppCompatActivity {

    /* UI */
    ActionBar actionBar;
    private TextView dateupdate, nameupdate;
    private EditText commentupdate;
    private Spinner typeupdate;
    private RatingBar noteupdate;
    private FloatingActionButton updatevisite;

    /*DATA*/
    private String date;
    private String name;
    private String prenom;
    private String note;
    private String type;
    private String comment;
    private String authHeader;
    private String id;

    /*DatePicker*/
    private int mYear, mMonth, mDay;

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
//        List<String> list = Arrays.asList(getResources().getStringArray(R.array.type_visite));
//
//        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(UpdateVisite.this, android.R.layout.simple_spinner_item, list);
//        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        typeupdate.setAdapter(spinnerAdapter);
//        spinnerAdapter.add(type);
//        spinnerAdapter.notifyDataSetChanged();
        noteupdate = findViewById(R.id.updatenotevisite);
        noteupdate.setRating(Float.parseFloat(note));
        commentupdate = findViewById(R.id.updatecommentairevisite);
        commentupdate.setText(comment);

        dateupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDate(v);
            }
        });

        updatevisite = findViewById(R.id.updateVisit);
        updatevisite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UpdateVisite.this,"Visite modifié avec succès", Toast.LENGTH_LONG).show();
            }
        });
    }

    // Datepiker
    public void getDate(View v) {
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
                        dateupdate.setText(sdf.format((myCalendar.getTime())));
                    }
                }, mYear, mMonth, mDay);
        dpd.getDatePicker().setMinDate(System.currentTimeMillis());
        dpd.show();
    }

    public void updateVisitlClicked(){
        // update network
    }
}
