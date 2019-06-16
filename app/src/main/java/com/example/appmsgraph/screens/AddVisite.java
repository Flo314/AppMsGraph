package com.example.appmsgraph.screens;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appmsgraph.R;
import com.example.appmsgraph.modelSharepoint.Value;
import com.example.appmsgraph.modelSharepoint.Value_;
import com.example.appmsgraph.modelcustom.VisiteObject;
import com.example.appmsgraph.network.GetDataService;
import com.example.appmsgraph.network.RetrofitInstance;
import com.example.appmsgraph.utils.Validator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddVisite extends AppCompatActivity {

    /*DatePicker*/
    private int mYear, mMonth, mDay;
    private TextView editDate;

    /*Debug*/
    private final String TAG = AddVisite.class.getSimpleName();

    /*UI*/
    ActionBar actionBar;
    private TextView collab;
    private FloatingActionButton ajoutVisite;
    private Spinner type_visite;
    private RatingBar note;
    private EditText commentaire;

    /*Validator*/
    private TextView validatorcomment;
    private TextView validatordate;

    /*Data*/
    private static ArrayList<Value_> datalistObj = new ArrayList<>();
    private List<String> spinnerDataName = new ArrayList<>();
    private String authHeader;
    private String id;
    private String nametitle;
    private String prenom;

    VisiteObject visiteObject = new VisiteObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_visite);

        // actionBar
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.toolbar_visibility));
        actionBar.setTitle("Ajouter une Visite");

        // Data qui vient de Historique
        Intent intents = getIntent();
        authHeader = intents.getStringExtra("Tok");
        id = intents.getStringExtra("id");
        nametitle = intents.getStringExtra("title");
        prenom = intents.getStringExtra("prenom");


        // Je récupère mes vues
        validatordate = findViewById(R.id.validatorDateVisite);
        collab = findViewById(R.id.collaborator);
        collab.setText(nametitle + " " + prenom);
        type_visite = findViewById(R.id.typevisite);
        note = findViewById(R.id.notevisite);
        commentaire = findViewById(R.id.commentairevisite);
        validatorcomment = findViewById(R.id.validatorVisite);

        // FloatingActionButton pour faire un Update des data
        ajoutVisite = findViewById(R.id.addVisit);
        ajoutVisite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addVisitlClicked();
            }
        });

        // Date du formulaire
        editDate = findViewById(R.id.datevisite);
        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDate(v);
            }
        });
    }

    public void addVisitlClicked() {

        // Je récupère du coup la saisie utilisateur dans mes vues
        String date = editDate.getText().toString();
        String type = type_visite.getSelectedItem().toString();
        String not = String.valueOf(note.getRating()).toString();
        String comment = commentaire.getText().toString();

        Log.d(TAG, "Data du formulaire: " + id + "\n"
        + date + "\n"
        + type + "\n"
        +not + "\n"
        + comment);

        // Control formulaire
        Validator.validatorDate(date);
        if (!Validator.validatorDate(date)) {
            validatordate.setText("Erreur: veuillez entrer une date");
            validatordate.setVisibility(View.VISIBLE);
            Validator.validator(comment);
        } else if (!Validator.validator(comment)) {
            validatorcomment.setText("Erreur: le commentaire ne doit pas dépasser 150 caractères maximum");
            validatorcomment.setVisibility(View.VISIBLE);
        }else {
            /*ADD VISITE*/
            //TODO MAJ HIST SHAREPOINT
            String newHisto = date+"!"+type+"!"+not+"!"+comment+"£";
            if (Historique.histo == null) {
                Historique.histo = newHisto;
                Log.d(TAG, "NEWHISTO " + newHisto);
            } else {
                Historique.histo = Historique.histo + newHisto;
                Log.d(TAG, "Histo + NEWHISTO " + Historique.histo);
            }
            /**/
            finish();
            Log.d(TAG, "ListVisite: " + visiteObject.getVisitList());
            Toast.makeText(this,"Visite ajouté avec succès", Toast.LENGTH_LONG).show();
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

    /* Cycle de vie Activity
     * =======================*/

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "OnStart called ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "OnPause called ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "OnStop called ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "OnDestroy called ");
    }


}
