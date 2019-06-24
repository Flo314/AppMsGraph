package com.example.appmsgraph.screens;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
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
import com.example.appmsgraph.modelSharepoint.Fields;
import com.example.appmsgraph.modelSharepoint.Value;
import com.example.appmsgraph.modelcustom.VisiteObject;
import com.example.appmsgraph.network.GetDataService;
import com.example.appmsgraph.network.RetrofitInstance;
import com.example.appmsgraph.utils.Validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
    private String authHeader;
    private String id;
    private String nametitle;
    private String prenom;
    private String newHisto;
    private String newDate;

    VisiteObject visiteObject;

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
            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                getDate(v);
            }
        });
    }

    // ajout d'une visite dans la liste sharepoint (clolonne Historique)
    private void addVisitlClicked() {
        // Je récupère la saisie utilisateur dans mes vues
        String date = editDate.getText().toString();
        String type = type_visite.getSelectedItem().toString();
        String not = String.valueOf(note.getRating()).toString();
        String comment = commentaire.getText().toString();
        if (comment.isEmpty()) comment = "pas de commentaire";

        // Control formulaire
        Validator.validatorDate(date);
        if (!Validator.validatorDate(date)) {
            validatordate.setText("Erreur: veuillez entrer une date");
            validatordate.setVisibility(View.VISIBLE);
            Validator.validator(comment);
        } else if (!Validator.validator(comment)) {
            validatorcomment.setText("Erreur:Vous avez dépassé la limite de 150 caractères maximum");
            validatorcomment.setVisibility(View.VISIBLE);
        } else {
            /*ADD VISITE*/
            newHisto = date + "!" + type + "!" + not + "!" + comment + "£";
            newDate = date;
            Log.d(TAG, "DATE: " + date);
            // si colonne Historique est vide
            if(Historique.histo == null){
                Historique.histo = newHisto;
                visiteObject = new VisiteObject(date,type,not,comment);
                visiteObject.setListAuBonFormat(Historique.histo);
                Historique.visiteObjectList.add(0,visiteObject);
                updateDate();
                updateHisto();
                Toast.makeText(this, "Visite ajouté avec succès", Toast.LENGTH_SHORT).show();
                // colonne Historique non vide
            }else {
                visiteObject = new VisiteObject(date,type,not,comment);
                // comparer la date pour ne pas l'ajouter si elle est inférieure à la date de la dernière visite
                compareStringDate(visiteObject);
                Log.d(TAG, "COMPARE STRING DATE:" + compareStringDate(visiteObject));
                if(compareStringDate(visiteObject)){
                    Historique.histo = newHisto + Historique.histo;
                    Historique.visiteObjectList.add(0,visiteObject);
                    updateDate();
                    updateHisto();
                    Toast.makeText(this, "Visite ajouté avec succès", Toast.LENGTH_SHORT).show();
                }else Toast.makeText(this, "Erreur la date est inférieure à la dernière visite", Toast.LENGTH_LONG).show();

            }
            finish();
        }



    }

    // Datepiker
    private void getDate(View v) {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(AddVisite.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        editDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private static String formatDate(int year, int month, int day) {

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(year, month, day);
        Date date = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);

        return sdf.format(date);
    }

    // mise à jour de la liste shrarepoint (colonne Historique)
    public void updateHisto() {
        Fields fields = new Fields();
        fields.setHistorique(Historique.histo);
        GetDataService service = RetrofitInstance.getRetrofitInstance().create(GetDataService.class);
        Call<Value> call = service.updateData(authHeader, id, fields);
        Log.d(TAG, "starting retrofit request to graph");
        Log.d(TAG, call.request().url() + "");
        // Exécute la requête asynchrone
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                Log.d(TAG, "Response: " + response.message());
                Log.d(TAG, "Response: " + response.toString());
                if (!response.isSuccessful()) {
                    Log.d(TAG, "RESPONSE ADD: " + response.code());
                    return;
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                Toast.makeText(AddVisite.this, "Something went wrong...Please try later!", Toast.LENGTH_LONG).show();
                Log.e(TAG, "Failure: " + t.toString());
                t.printStackTrace();
            }
        });
    }
    // mise à jour de la liste sharepoint (colonne Visite)
    public void updateDate() {
        Fields fields = new Fields();
        fields.setVisite(newDate);
        GetDataService service = RetrofitInstance.getRetrofitInstance().create(GetDataService.class);
        Call<Value> call = service.updateDate(authHeader, id, fields);
        Log.d(TAG, "starting retrofit request to graph");
        Log.d(TAG, call.request().url() + "");
        // Exécute la requête asynchrone
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                Log.d(TAG, "Response: " + response.message());
                Log.d(TAG, "Response: " + response.toString());
                if (!response.isSuccessful()) {
                    Log.d(TAG, "RESPONSE ADD: " + response.code());
                    return;
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                Toast.makeText(AddVisite.this, "Something went wrong...Please try later!", Toast.LENGTH_LONG).show();
                Log.e(TAG, "Failure: " + t.toString());
                t.printStackTrace();
            }
        });
    }

    // compare la différence entre 2 string converti en date pour l'ajout en tête de liste
    // sinon insertion à l'indice 0
    public boolean compareStringDate(VisiteObject visiteObject) {
        for (VisiteObject item : Historique.visiteObjectList) {
            String myFormat = "dd/MM/yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.FRANCE);
            Date datelist = null;
            Date newdate = null;

            try {
                datelist = simpleDateFormat.parse(item.getDate());
                newdate = simpleDateFormat.parse(visiteObject.getDate());
                if (newdate.getTime() > datelist.getTime())
                    return true;
            } catch (ParseException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    /* Cycle de vie Activity
     * =======================*/

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "OnResume called ");
    }

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
