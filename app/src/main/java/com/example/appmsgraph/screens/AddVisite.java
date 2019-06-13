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
import com.example.appmsgraph.VisiteAdapter;
import com.example.appmsgraph.model.Value;
import com.example.appmsgraph.model.Value_;
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
    private Spinner spinnercollab;
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

        // Je récupère mes vues
        validatordate = findViewById(R.id.validatorDateVisite);
        spinnercollab = findViewById(R.id.spinnercollaborator);
        type_visite = findViewById(R.id.typevisite);
        note = findViewById(R.id.notevisite);
        commentaire = findViewById(R.id.commentairevisite);
        validatorcomment = findViewById(R.id.validatorVisite);

        // appel reseau pour obtenir les données afin de remplir les spinner
        networkGet();

        spinnercollab.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Log.d(TAG, "Selected: " + item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // FloatingActionButton pour faire un Update des data
        ajoutVisite = findViewById(R.id.ajoutvisite);
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
        String id = spinnercollab.getSelectedItem().toString();
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
        }
        VisiteObject.addVisite(new VisiteObject(id, date, type, not, comment));
        // Et on ferme l'activité en cours
        finish();
        Log.d(TAG, "ListVisite: " + VisiteObject.getVisitList());
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

    private void networkGet() {
        //Créer un identifiant pour l'interface RetrofitInstance
        GetDataService service = RetrofitInstance.getRetrofitInstance().create(GetDataService.class);
        // Appelez la méthode avec paramètre dans l'interface pour obtenir les données sur l'employé
        // en lui passant le token
        Call<Value> call = service.getNameCollab(authHeader);
        Log.d(TAG, "starting retrofit request to graph");
        Log.d(TAG, call.request().url() + "");
        // Exécute la requête asynchrone
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(@NonNull Call<Value> call, @NonNull Response<Value> response) {
                Log.d(TAG, "Response: " + response.message());
                Log.d(TAG, "Response: " + response.toString());
                // si reponse ok et que les data ne sont pas null
                if (response.isSuccessful() && response.body() != null) {
                    datalistObj = response.body().getValue();

                    // remplir le champ collab du formulaire avec le nom et prenom
                    for (Value_ value : datalistObj) {
                        if (value.getFields().getTitle() != null && value.getFields().getPrenom() != null) {
                            spinnerDataName.add(value.getFields().getId().toString() + " - " + value.getFields().getTitle().toString()
                                    + " " + value.getFields().getPrenom().toString());
                        }
                    }
                    Log.d(TAG, "Data: " + spinnerDataName.toString() + "\n"
                            + "Size: " + spinnerDataName.size());
                    // remplir le spinner avec les data reçu
                    ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(
                            AddVisite.this, android.R.layout.simple_spinner_item, spinnerDataName);
                    adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnercollab.setAdapter(adapterSpinner);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Value> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_LONG).show();
                Log.e(TAG, "Failure: " + t.toString());
                t.printStackTrace();
            }
        });
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
