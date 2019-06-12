package com.example.appmsgraph.screens;

import android.app.DatePickerDialog;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.appmsgraph.R;
import com.example.appmsgraph.modelcustom.VisiteObject;
import com.example.appmsgraph.utils.CustomDialogSuccess;
import com.example.appmsgraph.utils.Validator;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddVisite extends AppCompatActivity {

    /*DatePicker*/
    private int mYear, mMonth, mDay;
    private TextView editDate;

    /*UI*/
    ActionBar actionBar;

    /*Validator*/
    private TextView validator;
    private TextView validatorDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_visite);

        // actionBar
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.toolbar_visibility));
        actionBar.setTitle("Créer une Visite");
    }
     public void addVisitlClicked(View view){
         // Quand le bouton est cliquée :
         // Je récupère mes vues
         getDate(editDate);
         TextView validatordate = findViewById(R.id.validatorDate);
         Spinner spinnercollab = findViewById(R.id.spinnercollab);
         Spinner type_visite = findViewById(R.id.type_visite);
         RatingBar note = findViewById(R.id.note);
         EditText commentaire = findViewById(R.id.commentaire);
         TextView validatorcomment = findViewById(R.id.validator);

         // Je récupère du coup la saisie utilisateur dans mes vues
         String id = spinnercollab.getSelectedItem().toString();
         String date = editDate.getText().toString();
         String type = type_visite.getSelectedItem().toString();
         String not = String.valueOf(note.getRating()).toString();
         String comment = commentaire.getText().toString();

         // Control formulaire
         Validator.validatorDate(date);
         if (!Validator.validatorDate(date)) {
             validatorDate.setText("Erreur: veuillez entrer une date");
             validatorDate.setVisibility(View.VISIBLE);
             Validator.validator(comment);
         } else if (!Validator.validator(comment)) {
             validator.setText("Erreur: le commentaire ne doit pas dépasser 150 caractères maximum");
             validator.setVisibility(View.VISIBLE);
         }else {
             VisiteObject.addVisite(new VisiteObject(id, date, type, not, comment));
             // Et on ferme l'activité en cours
             finish();
         }
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
                        editDate.setText(sdf.format((myCalendar.getTime())));
                    }
                }, mYear, mMonth, mDay);
        dpd.getDatePicker().setMinDate(System.currentTimeMillis());
        dpd.show();
    }

}
