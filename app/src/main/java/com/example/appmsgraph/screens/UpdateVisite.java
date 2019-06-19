package com.example.appmsgraph.screens;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appmsgraph.R;
import com.example.appmsgraph.VisiteAdapter;
import com.example.appmsgraph.modelSharepoint.Fields;
import com.example.appmsgraph.modelSharepoint.Value;
import com.example.appmsgraph.modelcustom.VisiteObject;
import com.example.appmsgraph.network.GetDataService;
import com.example.appmsgraph.network.RetrofitInstance;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateVisite extends AppCompatActivity {

    /* UI */
    ActionBar actionBar;
    private TextView dateupdate, nameupdate;
    private EditText commentupdate;
    private Spinner typeupdate;
    private RatingBar noteupdate;
    private FloatingActionButton updatevisite;

    /*Debug*/
    private final String TAG = UpdateVisite.class.getSimpleName();

    /*DATA*/
    private String date;
    private String name;
    private String prenom;
    private String note;
    private String type;
    private String comment;
    private String authHeader;
    private String id;
    private String newDate;
    private String oldHisto;

    VisiteObject visiteObject = new VisiteObject();

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
        // todo récup value du spinner
        String compareValue = type;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.type_visite, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeupdate.setAdapter(adapter);
        if (compareValue != null) {
            int spinnerPosition = adapter.getPosition(compareValue);
            typeupdate.setSelection(spinnerPosition);
        }

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
                updateVisitlClicked();
                Toast.makeText(UpdateVisite.this, "Visite modifié avec succès", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Datepiker
    public void getDate(View v) {
//        DatePickerDialog dpd = new DatePickerDialog(v.getContext(),
//                new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        Calendar myCalendar = Calendar.getInstance();
//                        myCalendar.set(Calendar.YEAR, year);
//                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                        myCalendar.set(Calendar.MONTH, month);
//                        String myFormat = "dd/MM/yyyy";
//                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
//                        dateupdate.setText(sdf.format((myCalendar.getTime())));
//                    }
//                }, mYear, mMonth, mDay);
//        dpd.getDatePicker().setMinDate(System.currentTimeMillis());
//        dpd.show();
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateVisite.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        dateupdate.setText(dayOfMonth + "/" + (monthOfYear +1) + "/" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void updateVisitlClicked() {
        // récup des valeurs du formulaire
        String dateFormulaire = dateupdate.getText().toString();
        String typeFormulaire = typeupdate.getSelectedItem().toString();
        String notFormulaire = String.valueOf(noteupdate.getRating()).toString();
        String commentFormulaire = commentupdate.getText().toString();

        /*UPDATE VISITE*/
        String newHisto = dateFormulaire + "!" + typeFormulaire + "!" + notFormulaire + "!" + commentFormulaire + "£";
        Log.d(TAG, "NEWHISTO: " + newHisto);
        Log.d(TAG, "HISTORIQUE: " + Historique.histo );
        if (Historique.histo != null){
            Historique.histo = newHisto;
            Log.d(TAG, "HISTORIQUE: " + Historique.histo );
        }


        Log.d(TAG, "HISTO " + Historique.histo);
        Log.d(TAG, "NEWHISTO " + newHisto);
        updateHisto();

        finish();
        Log.d(TAG, "ListVisite: " + visiteObject.getVisitList());
    }

    public void updateHisto(){
        Fields fields = new Fields();
        fields.setHistorique(Historique.histo);
        GetDataService service = RetrofitInstance.getRetrofitInstance().create(GetDataService.class);
        Call<Value> call = service.updateData(authHeader,id,fields);
        Log.d(TAG, "starting retrofit request to graph");
        Log.d(TAG, call.request().url() + "");
        // Exécute la requête asynchrone
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                Log.d(TAG, "Response: " + response.message());
                Log.d(TAG, "Response: " + response.toString());
                if (!response.isSuccessful()){
                    Log.d(TAG, "RESPONSE ADD: " + response.code());
                    return;
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                Toast.makeText(UpdateVisite.this, "Something went wrong...Please try later!", Toast.LENGTH_LONG).show();
                Log.e(TAG, "Failure: " + t.toString());
                t.printStackTrace();
            }
        });
    }
    public void updateDate(){
        Fields fields = new Fields();
        fields.setVisite(newDate);
        GetDataService service = RetrofitInstance.getRetrofitInstance().create(GetDataService.class);
        Call<Value> call = service.updateDate(authHeader,id,fields);
        Log.d(TAG, "starting retrofit request to graph");
        Log.d(TAG, call.request().url() + "");
        // Exécute la requête asynchrone
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                Log.d(TAG, "Response: " + response.message());
                Log.d(TAG, "Response: " + response.toString());
                if (!response.isSuccessful()){
                    Log.d(TAG, "RESPONSE ADD: " + response.code());
                    return;
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                Toast.makeText(UpdateVisite.this, "Something went wrong...Please try later!", Toast.LENGTH_LONG).show();
                Log.e(TAG, "Failure: " + t.toString());
                t.printStackTrace();
            }
        });
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