package com.example.appmsgraph;

import android.accounts.Account;
import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.microsoft.identity.client.AuthenticationCallback;
import com.microsoft.identity.client.AuthenticationResult;
import com.microsoft.identity.client.IAccount;
import com.microsoft.identity.client.IAuthenticationResult;
import com.microsoft.identity.client.PublicClientApplication;
import com.microsoft.identity.client.exception.MsalClientException;
import com.microsoft.identity.client.exception.MsalException;
import com.microsoft.identity.client.exception.MsalServiceException;
import com.microsoft.identity.client.exception.MsalUiRequiredException;

import java.util.List;


public class MainActivity extends AppCompatActivity {


    final static String CLIENT_ID = Constantes.CLIENT_ID;
    final static String SCOPES [] = Constantes.SCOPES;
    final static String MSGRAPH_URL = "https://graph.microsoft.com/v1.0/me";

//    /* Azure AD Variables */
//    private PublicClientApplication publicClientApplication;
//    private AuthenticationResult authResult;
    AuthenticationCallback callback;

    AuthenticationHelper authenticationHelper = null;

    /*Debug*/
    private final String TAG = MainActivity.class.getSimpleName();

    /*UI*/
    Button btnSign;
    private ProgressBar mProgress = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgress = findViewById(R.id.progressbar);

        btnSign = findViewById(R.id.btnSign);
        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSign();
                Log.d(TAG, "Connexion...");
            }
        });

//        /* Enregistrer l'état pour l'activity */
//        publicClientApplication = null;
//        if (publicClientApplication == null) {
//            publicClientApplication = new PublicClientApplication(this.getApplicationContext(), CLIENT_ID);
//        }


    }

    private void onSign() {
        authenticationHelper = AuthenticationHelper.getInstance(getApplicationContext());
        authenticationHelper.getPublicClient();
        authenticationHelper.doAquireToken(this, callback);
    }

    //    /* Regarde si les jetons sont dans le cache (rafraîchit si nécessaire et si on ne force pas Refresh */
//    private AuthenticationCallback getAuthSilentCallback(){
//        return new AuthenticationCallback() {
//            @Override
//            public void onSuccess(IAuthenticationResult authenticationResult) {
//                /* Successfully got a token, call Graph now */
//                Log.d(TAG, "Successfully authenticated");
//
//                /* Store the authResult */
//                authResult = (AuthenticationResult) authenticationResult;
//            }
//
//            @Override
//            public void onError(MsalException exception) {
//                // vérifie le type d'exception
//                if(exception instanceof MsalUiRequiredException){
//                    // Exception dans Msal
//                }else if(exception instanceof MsalClientException){
//                    // Exception lors de la communication avec le serveur d'authentification
//                }else if(exception instanceof MsalServiceException);
//            }
//
//            @Override
//            public void onCancel() {
//                /* User cancelled the authentication */
//                Log.d(TAG, "User cancelled login.");
//            }
//        };
//    }
//
//    /* Si réussit nous utilisons l'accès
//     * jeton pour appeler le Microsoft Graph. Ne vérifie pas le cache
//     */
//    private AuthenticationCallback getAuthInteractiveCallback() {
//        return new AuthenticationCallback() {
//
//            @Override
//            public void onSuccess(IAuthenticationResult authenticationResult) {
//                /* Successfully got a token, call graph now */
//                Log.d(TAG, "Successfully authenticated");
//                Log.d(TAG, "ID Token: " + authenticationResult.getIdToken());
//
//                /* Store the auth result */
//                authResult = (AuthenticationResult) authenticationResult;
//            }
//
//            @Override
//            public void onError(MsalException exception) {
//                /* Failed to acquireToken */
//                Log.d(TAG, "Authentication failed: " + exception.toString());
//
//                if (exception instanceof MsalClientException) {
//                    /* Exception inside MSAL, more info inside MsalError.java */
//                } else if (exception instanceof MsalServiceException) {
//                    /* Exception when communicating with the STS, likely config issue */
//                }
//            }
//
//            @Override
//            public void onCancel() {
//                /* User cancelled the authentication */
//                Log.d(TAG, "User cancelled login.");
//            }
//        };
//    }
//
    public Activity getActivity() {
        return this;
    }

//    private void onCallAuth(){
//        //publicClientApplication.acquireToken(getActivity(), SCOPES, getAuthInteractiveCallback());
//        /* Essayer d'obtenir un utilisateur et acquérir TokenSilent
//          Si cela échoue, demande interactive */
//        final List<Account> users = null;
//
//        try {
//            publicClientApplication.getAccounts(new PublicClientApplication.AccountsLoadedCallback() {
//                @Override
//                public void onAccountsLoaded(List<IAccount> accounts) {
//
//                    if (users != null && users.size() == 1) {
//                        /* 1 user */
//                        publicClientApplication.acquireTokenSilentAsync(SCOPES, (IAccount) users.get(0), getAuthSilentCallback());
//                    } else {
//                        /* 0 user */
//                        publicClientApplication.acquireToken(getActivity(), SCOPES, getAuthInteractiveCallback());
//                    }
//                }
//            });
//
//        } catch (IndexOutOfBoundsException e) {
//            Log.d(TAG, "User at this position does not exist: " + e.toString());
//        }
//    }


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

    // Cycle de vie Activity ------------------------------------------------------------------------------

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
        hideProgressBar();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "OnDestroy called ");
    }



}
