package com.example.appmsgraph;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.appmsgraph.auth.AuthenticationHelper;
import com.example.appmsgraph.model.Fields;
import com.example.appmsgraph.model.FieldsList;
import com.example.appmsgraph.model.Value;
import com.example.appmsgraph.network.GetDataService;
import com.example.appmsgraph.network.RetrofitInstance;
import com.microsoft.identity.client.AuthenticationCallback;
import com.microsoft.identity.client.IAccount;
import com.microsoft.identity.client.IAuthenticationResult;
import com.microsoft.identity.client.PublicClientApplication;
import com.microsoft.identity.client.exception.MsalClientException;
import com.microsoft.identity.client.exception.MsalException;
import com.microsoft.identity.client.exception.MsalServiceException;
import com.microsoft.identity.client.exception.MsalUiRequiredException;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    /*Auth*/
    AuthenticationHelper authenticationHelper = null;
    String authHeader;

    /*Debug*/
    private final String TAG = MainActivity.class.getSimpleName();

    /*UI*/
    Button btnSign;
    ImageView logo;
    private ProgressBar mProgress = null;
    private CollaboratorAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        authenticationHelper = AuthenticationHelper.getInstance(getApplicationContext());

        mProgress = findViewById(R.id.progressbar);
        logo = findViewById(R.id.logo);
        btnSign = findViewById(R.id.btnSign);
        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
                Log.d(TAG, "Connexion...");
            }
        });

    }

    /* Helper methods Authentification
    * =================================
    */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        authenticationHelper.handleRedirect(requestCode, resultCode, data);
    }

    /* Connexion silencieuse - utilisée s'il y a déjà un compte d'utilisateur dans le cache MSAL */
    private void doSilentSignIn() {
        authenticationHelper.acquireTokenSilently(getAuthCallback());
        Log.d(TAG, "Auth silent...");
    }

    // Demander à l'utilisateur de se connecter
    private void doInteractiveSignIn() {
        authenticationHelper.acquireTokenInteractively(this, getAuthCallback());
        Log.d(TAG, "Auth interactive...");
    }


    // retour de l'authentification
    private AuthenticationCallback getAuthCallback() {
        return new AuthenticationCallback() {
            @Override
            // success
            public void onSuccess(IAuthenticationResult authenticationResult) {
                // token
                String accessToken = authenticationResult.getAccessToken();
                Log.d(TAG, "Authentication success!!! ");
                Log.d(TAG, "Access token: " + accessToken);
                authHeader = accessToken;
                hideProgressBar();
                updateSuccessUI();
            }

            @Override
            // error
            public void onError(MsalException exception) {
                // vérifie le type d'exception
                if (exception instanceof MsalUiRequiredException) {
                    Log.d(TAG, "Interactive login required");
                    doInteractiveSignIn();

                } else if (exception instanceof MsalClientException) {
                    // Exception dans MSAL, plus d'informations dans MsalError.java
                    Log.e(TAG, "Client error authenticating: "+ exception.toString());
                } else if (exception instanceof MsalServiceException) {
                    // Exception lors de la communication avec le serveur d'authentification
                    Log.e(TAG, "Service error authenticating: "+ exception.toString());
                }
                hideProgressBar();
            }

            @Override
            public void onCancel() {
                // L'utilisateur a annulé l'authentification
                Log.d(TAG, "Authentication canceled");
                hideProgressBar();
            }
        };
    }

    // Connexion
    private void signIn() {
        showProgressBar();
        authenticationHelper.getPublicClient().getAccounts(new PublicClientApplication.AccountsLoadedCallback() {
            @Override
            public void onAccountsLoaded(List<IAccount> accounts) {
                if (accounts != null && accounts.size() == 1) {
                    /* 1 user */
                    doSilentSignIn();
                } else {
                    /* 0 user */
                    doInteractiveSignIn();
                }
            }
        });
    }

    /* ProgressBar
    * ============= */

    private void showProgressBar() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgress.setVisibility(View.VISIBLE);
            }
        });
    }

    private void hideProgressBar() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgress.setVisibility(View.GONE);
            }
        });
    }

    /* Helper methods gèrent les mises à jour de l'interface utilisateur
     * =================================================================
     * updateSuccessUi() - Met à jour l'interface utilisateur lorsque l'acquisition de jeton réussit
     * loadDataList() - charge les données réseaux dans le recyclerview */

    private void updateSuccessUI(){
        btnSign.setVisibility(View.INVISIBLE);
        logo.setVisibility(View.INVISIBLE);
        network();
    }

    private void loadDataList(ArrayList<Fields> datalist){
        recyclerView = findViewById(R.id.recyclerview);
        adapter = new CollaboratorAdapter(datalist);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(View.VISIBLE);
    }

    /* Helper methods gèrent les appels réseaux
     * ================================================================= */

    private void network(){
        //Créer un identifiant pour l'interface RetrofitInstance
        GetDataService service = RetrofitInstance.getRetrofitInstance().create(GetDataService.class);
        // Appelez la méthode avec paramètre dans l'interface pour obtenir les données sur l'employé
        Call<FieldsList> call = service.getCollaboratorsData(authHeader);
        Log.d(TAG, "starting retrofit request to graph");
        Log.d(TAG, call.request().url() + "");
        // Exécute la requête asynchrone
        call.enqueue(new Callback<FieldsList>() {
            @Override
            public void onResponse(Call<FieldsList> call, Response<FieldsList> response) {
                loadDataList(response.body().getFieldsList());
                Log.d(TAG, "Response: " + response.toString());
            }

            @Override
            public void onFailure(Call<FieldsList> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_LONG).show();
                Log.d(TAG, "Failure: " + t.toString());
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
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "OnResume called ");
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
