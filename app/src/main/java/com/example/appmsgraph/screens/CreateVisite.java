package com.example.appmsgraph.screens;

import android.app.DatePickerDialog;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.appmsgraph.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class CreateVisite extends AppCompatActivity {

    /* UI */
    ActionBar actionBar;
    private EditText editDate;

    // DatePicker
    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_visite);

        // actionBar
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.toolbar_visibility));
        actionBar.setTitle("Visite");

        // picker date formulaire
        editDate = findViewById(R.id.date_visite);
        editDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
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
                },mYear, mMonth, mDay);
                dpd.getDatePicker().setMinDate(System.currentTimeMillis());
                dpd.show();
            }
        });

    }
}
