package com.example.appmsgraph;


import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.util.Log;

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

public class AuthenticationHelper {

    private static AuthenticationHelper INSTANCE = null;
    private static PublicClientApplication publicClientApplication = null;
    public static Context context;
    private Activity activity;

    final static String CLIENT_ID = Constantes.CLIENT_ID;
    final static String SCOPES [] = Constantes.SCOPES;

    /*Debug*/
    private final String TAG = AuthenticationHelper.class.getSimpleName();

    private AuthenticationResult authResult;

    private AuthenticationHelper(){
    }

    public static synchronized AuthenticationHelper getInstance(Context ctx){
        // En passant le context on sauve le context qui est l'activity elle-même
        context = ctx;

        if(INSTANCE == null){
            INSTANCE = new AuthenticationHelper();
            if(publicClientApplication == null){
                publicClientApplication = new PublicClientApplication(ctx, CLIENT_ID);
            }
        }
        return INSTANCE;
    }

    public PublicClientApplication getPublicClient(){
        return publicClientApplication;
    }

    public void doAquireToken(final Activity activity, AuthenticationCallback callback ){
        //publicClientApplication.acquireToken(getActivity(), SCOPES, getAuthInteractiveCallback());

        /* Essayer d'obtenir un utilisateur et acquérir TokenSilent
          Si cela échoue, demande interactive */
        final List<Account> users = null;

        try {
            publicClientApplication.getAccounts(new PublicClientApplication.AccountsLoadedCallback() {
                @Override
                public void onAccountsLoaded(List<IAccount> accounts) {

                    if (users != null && users.size() == 1) {
                        /* 1 user */
                        publicClientApplication.acquireTokenSilentAsync(SCOPES, (IAccount) users.get(0), getAuthSilentCallback());
                    } else {
                        /* 0 user */
                        publicClientApplication.acquireToken(activity, SCOPES, getAuthInteractiveCallback());
                    }
                }
            });

        } catch (IndexOutOfBoundsException e) {
            Log.d(TAG, "User at this position does not exist: " + e.toString());
        }
    }

    public AuthenticationCallback getAuthInteractiveCallback() {
        return new AuthenticationCallback() {

            @Override
            public void onSuccess(IAuthenticationResult authenticationResult) {
                /* Successfully got a token, call graph now */
                Log.d(TAG, "Successfully authenticated");
                Log.d(TAG, "ID Token: " + authenticationResult.getIdToken());

                /* Store the auth result */
                authResult = (AuthenticationResult) authenticationResult;
            }

            @Override
            public void onError(MsalException exception) {
                /* Failed to acquireToken */
                Log.d(TAG, "Authentication failed: " + exception.toString());

                if (exception instanceof MsalClientException) {
                    /* Exception inside MSAL, more info inside MsalError.java */
                } else if (exception instanceof MsalServiceException) {
                    /* Exception when communicating with the STS, likely config issue */
                }
            }

            @Override
            public void onCancel() {
                /* User cancelled the authentication */
                Log.d(TAG, "User cancelled login.");
            }
        };
    }

    /* Regarde si les jetons sont dans le cache (rafraîchit si nécessaire et si on ne force pas Refresh */
    public AuthenticationCallback getAuthSilentCallback(){
        return new AuthenticationCallback() {
            @Override
            public void onSuccess(IAuthenticationResult authenticationResult) {
                /* Successfully got a token, call Graph now */
                Log.d(TAG, "Successfully authenticated");

                /* Store the authResult */
                authResult = (AuthenticationResult) authenticationResult;
            }

            @Override
            public void onError(MsalException exception) {
                // vérifie le type d'exception
                if(exception instanceof MsalUiRequiredException){
                    // Exception dans Msal
                }else if(exception instanceof MsalClientException){
                    // Exception lors de la communication avec le serveur d'authentification
                }else if(exception instanceof MsalServiceException);
            }

            @Override
            public void onCancel() {
                /* User cancelled the authentication */
                Log.d(TAG, "User cancelled login.");
            }
        };
    }
}
