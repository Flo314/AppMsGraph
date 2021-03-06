package com.example.appmsgraph.screens;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import com.example.appmsgraph.CollaboratorAdapter;
import com.example.appmsgraph.R;
import com.example.appmsgraph.auth.AuthenticationHelper;
import com.example.appmsgraph.modelSharepoint.Fields;
import com.example.appmsgraph.modelSharepoint.User;
import com.example.appmsgraph.modelSharepoint.UserConnect;
import com.example.appmsgraph.modelSharepoint.Value;
import com.example.appmsgraph.modelSharepoint.Value_;
import com.example.appmsgraph.network.GetDataService;
import com.example.appmsgraph.network.RetrofitInstance;
import com.example.appmsgraph.utils.CompareDate;
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


public class MainActivity extends AppCompatActivity implements CollaboratorAdapter.ListItemClickListener {

    /*Auth*/
    AuthenticationHelper authenticationHelper = null;
    private String authHeader;

    /*Debug*/
    private final String TAG = MainActivity.class.getSimpleName();

    /*UI*/
    private Button btnSign;
    private ActionBar actionBar;
    private ImageView logo;
    private ProgressBar mProgress = null;
    private CollaboratorAdapter adapter;
    private RecyclerView recyclerView;

    /*Data*/
    private static ArrayList<Value_> datalistObj = new ArrayList<>();
    private static List<Value_> filterListBusinessManager = new ArrayList<>();
    private String userConnectEmail;

    private Value_ businessManager;
    private Value_ responsableTechnique;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // actionbar
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();
        actionBar.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.toolbar_visibility));
        actionBar.setTitle("Team");

        authenticationHelper = AuthenticationHelper.getInstance(getApplicationContext());

        // progressbar
        mProgress = findViewById(R.id.progressbar);
        logo = findViewById(R.id.logo);
        btnSign = findViewById(R.id.btnSign);
        // bouton Signin pour l'authentification
        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
                Log.d(TAG, "Connexion...");
            }
        });
        recyclerView = findViewById(R.id.recyclerview);

    }

    /* Helper methods Authentification
     * =================================
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        authenticationHelper.handleRedirect(requestCode, resultCode, data);
    }

    /* Connexion silencieuse - utilisée s'il y a déjà un compte d'utilisateur dans le cache MSAL
    * permet de ne pas retaper son email et mot de passe */
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
                getUser();
                network();
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
                    Log.e(TAG, "Client error authenticating: " + exception.toString());
                } else if (exception instanceof MsalServiceException) {
                    // Exception lors de la communication avec le serveur d'authentification
                    Log.e(TAG, "Service error authenticating: " + exception.toString());
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
                    doSilentSignIn();
                } else {
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


    @SuppressLint("RestrictedApi")
    private void updateSuccessUI() {
        btnSign.setVisibility(View.INVISIBLE);
        logo.setVisibility(View.INVISIBLE);
        actionBar.show();
    }

    private void loadDataList(ArrayList<Value_> datalist) {
        recyclerView = findViewById(R.id.recyclerview);
        adapter = new CollaboratorAdapter(datalist, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(View.VISIBLE);
    }

    /* Helper methods gèrent les appels réseaux / récupère les données de la liste sharepoint
     * ====================================================================================== */

    private void network() {
        //Créer un identifiant pour l'interface RetrofitInstance
        GetDataService service = RetrofitInstance.getRetrofitInstance().create(GetDataService.class);
        // Appel la méthode avec paramètre dans l'interface pour obtenir les données sur l'employé
        // en lui passant le token
        Call<Value> call = service.getCollaboratorsData(authHeader);
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
                    loadDataList(response.body().getValue());
                    // les données sont stockées dans cette liste
                    datalistObj = response.body().getValue();
                    // connexion du BusinessManager
                    adapter.updateListBusinessManager(userConnectEmail);
                    // todo connexion du Responsable technique (méthode ok)
                    // todo connexion autre doit afficher la liste de tous les collaborateurs
                }
            }

            @Override
            public void onFailure(@NonNull Call<Value> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, "\n" +
                        "Une erreur s'est produite. Veuillez réessayer plus tard!", Toast.LENGTH_LONG).show();
                Log.e(TAG, "Failure: " + t.toString());
                t.printStackTrace();
            }
        });
    }

    private void getUser() {
        //Créer un identifiant pour l'interface RetrofitInstance
        GetDataService service = RetrofitInstance.getRetrofitInstance().create(GetDataService.class);
        // Appelez la méthode avec paramètre dans l'interface pour obtenir les données sur l'employé
        // en lui passant le token
        Call<UserConnect> call = service.getUsers(authHeader);
        Log.d(TAG, "starting retrofit request to graph");
        Log.d(TAG, call.request().url() + "");
        // Exécute la requête asynchrone
        call.enqueue(new Callback<UserConnect>() {
            @Override
            public void onResponse(@NonNull Call<UserConnect> call, @NonNull Response<UserConnect> response) {
                Log.d(TAG, "Response: " + response.message());
                Log.d(TAG, "Response: " + response.toString());
                // si reponse ok et que les data ne sont pas null
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "RESPONSE USER CONNECT: " + response.code());
                    userConnectEmail = response.body().getMail();
                    Log.d(TAG, "USER EMAIL CONNECT: " + response.body().getUserPrincipalName());
                    return;
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserConnect> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, "\n" +
                        "Une erreur s'est produite. Veuillez réessayer plus tard!", Toast.LENGTH_LONG).show();
                Log.e(TAG, "Failure: " + t.toString());
                t.printStackTrace();
            }
        });
    }

    // implementation interface adapter pour la gestion des click sur les items du recyclerview
    @Override
    public void onListItemClick(int clickedItemIndex) {
//        Log.d(TAG, "clickItem: " + clickedItemIndex);

        // ouverture de HistoriqueActivity en lui passant le nom du collab pour le title de l'action bar
        // ainsi que ses données
        Intent intent = new Intent(getApplicationContext(), Historique.class);
        Value_ clickItem = datalistObj.get(clickedItemIndex);
        intent.putExtra("title", clickItem.getFields().getTitle());
        intent.putExtra("prenom", clickItem.getFields().getPrenom());
        intent.putExtra("Tok", authHeader);
        intent.putExtra("id", clickItem.getId());
        intent.putExtra("visite", clickItem.getFields().getVisite());
        intent.putExtra("histo", clickItem.getFields().getHistorique());
        startActivity(intent);
        Log.d(TAG, "clickItem: " + clickItem.getFields().getId() + " - " + clickItem.getFields().getTitle());
    }

    /* Menu ActionBar Team
     * =======================*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        // icon search dans le menu
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    // item du menu Filter (filtrer les données)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // icon filter Menu
            case R.id.action_mycollab:
                // affiche les collab du BusinessManager
                adapter.updateListBusinessManager(userConnectEmail);
                return true;
            case R.id.action_myagence:
                // todo filtre sur agence
                Toast.makeText(this, "Afficher la liste des collab de l'agence", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_important:
                // méthode de l'adapter CollaboratorAdapter
                // affiche les collab ou l'indicateur est rouge
                adapter.updateListRed();
                return true;
            case R.id.action_moyen:
                // méthode de l'adapter CollaboratorAdapter
                // affiche les collab ou l'indicateur est orange
                adapter.updateListOrange();
                return true;
            case R.id.action_bon:
                // méthode de l'adapter CollaboratorAdapter
                // affiche les collab ou l'indicateur est vert
                adapter.updateListGreen();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
        network();
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
