package com.example.appmsgraph.screens;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appmsgraph.R;
import com.example.appmsgraph.model.Fields;
import com.example.appmsgraph.model.Value;
import com.example.appmsgraph.model.Value_;
import com.example.appmsgraph.network.GetDataService;
import com.example.appmsgraph.network.RetrofitInstance;
import com.example.appmsgraph.utils.CustomDialogSuccess;
import com.example.appmsgraph.utils.JsonFormat;
import com.example.appmsgraph.utils.Validator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.appmsgraph.R.color.red;


public class CreateVisite extends AppCompatActivity {

    /* UI */
    private ActionBar actionBar;
    private Spinner spinnercollab;
    private Spinner type_visite;
    private RatingBar note;
    private TextView editDate;
    private EditText commentaire;
    private FloatingActionButton save;
    private FloatingActionButton update;

    /*Validator*/
    private TextView validator;
    private TextView validatorDate;

    /*Data*/
    private static ArrayList<Value_> datalistObj = new ArrayList<>();
    private List<String> spinnerDataName = new ArrayList<>();
    private String idField;

    /*intent*/
    private String nameTitle;
    private String prenom;
    private String authHeader;
    private String id;
    private String histo;
    private String visite;
    private Boolean Uniqid;

    // DatePicker
    private int mYear, mMonth, mDay;

    /*Debug*/
    private final String TAG = CreateVisite.class.getSimpleName();

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_visite);

        // Data qui vient de Historique
        Intent intent = getIntent();
        // true = cardView
        Uniqid = intent.getBooleanExtra("Uniqid", true);
        nameTitle = intent.getStringExtra("title");
        prenom = intent.getStringExtra("prenom");
        authHeader = intent.getStringExtra("Tok");
        id = intent.getStringExtra("id");
        visite = intent.getStringExtra("visite");
        histo = intent.getStringExtra("histo");
//        Toast.makeText(getApplicationContext(), "token: " + authHeader, Toast.LENGTH_SHORT).show();

        // actionBar
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.toolbar_visibility));
        actionBar.setTitle("Créer une Visite");

        spinnercollab = (Spinner) findViewById(R.id.spinnercollab);
        // appel reseau pour obtenir les données
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
        update = findViewById(R.id.update_visite);

        // FloatingActionButton pour faire un Post des data
        save = findViewById(R.id.save_visite);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrieveForm();
            }
        });

        // formulaire
        validatorDate = findViewById(R.id.validatorDate);
        validator = findViewById(R.id.validator);
        type_visite = findViewById(R.id.type_visite);
        note = findViewById(R.id.note);
        commentaire = findViewById(R.id.commentaire);

        // picker date formulaire
        editDate = findViewById(R.id.date_visite);
        if (Uniqid) {
            actionBar.setTitle("Visite: " + nameTitle + " " + prenom);
            save.setVisibility(View.GONE);
            update.setVisibility(View.VISIBLE);
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

    // récupération des entré clavier
    private void retrieveForm() {

        String iD = spinnercollab.getSelectedItem().toString();
        String date = editDate.getText().toString();
        String type = type_visite.getSelectedItem().toString();
        String not = String.valueOf(note.getRating());
        String comment = commentaire.getText().toString();

        Validator.validatorDate(date);
        if (!Validator.validatorDate(date)) {
            validatorDate.setText("Erreur:veuillez entrer une date");
            validatorDate.setVisibility(View.VISIBLE);
            Validator.validator(comment);
        } else if (!Validator.validator(comment)) {
            validator.setText("Erreur:le commentaire ne doit pas dépasser 150 caractères maximum");
            validator.setVisibility(View.VISIBLE);
        }else {
        JSONObject jsonObject = JsonFormat.jsonToFormatObject(iD, date, type, not, comment);
        Log.d(TAG, "JSONOBJECT: " + jsonObject);
        // CustomDialog en cas de succes de POST
        CustomDialogSuccess customdialogsuccess = new CustomDialogSuccess();
        customdialogsuccess.show(getSupportFragmentManager(), "example simple dialog");
        validator.setVisibility(View.GONE);
        validatorDate.setVisibility(View.GONE);
        editDate.setText("Date");
        note.setRating(0F);
        commentaire.getText().clear();
    }

}

    /* Helper methods gèrent les appels réseaux
     * ================================================================= */

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
                    // remplir le champ collab avec le nom et prenom
                    for (Value_ value : datalistObj) {
                        idField = value.getFields().getId().toString();
                        if (value.getFields().getTitle() != null && value.getFields().getPrenom() != null) {
                            spinnerDataName.add(value.getFields().getTitle().toString()
                                    + " " + value.getFields().getPrenom().toString());
                        }
                    }
                    Log.d(TAG, "Data: " + spinnerDataName.toString() + "\n"
                            + "Size: " + spinnerDataName.size());
                    // remplir le spinner avec les data reçu
                    ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(
                            CreateVisite.this, android.R.layout.simple_spinner_item, spinnerDataName);
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

}
