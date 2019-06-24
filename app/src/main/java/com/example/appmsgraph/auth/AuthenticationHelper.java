package com.example.appmsgraph.auth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.microsoft.identity.client.AuthenticationCallback;
import com.microsoft.identity.client.IAccount;
import com.microsoft.identity.client.PublicClientApplication;

import java.util.List;

/**
 * Authentification
 */
public class AuthenticationHelper {

    private static AuthenticationHelper INSTANCE = null;
    private static PublicClientApplication publicClientApplication = null;
    public static Context context;

    private String clientid;
    private String scopes[];

    /*Debug*/
    private final String TAG = AuthenticationHelper.class.getSimpleName();

    private AuthenticationHelper(Context ctx) {
        this.clientid = Constantes.CLIENT_ID;
        this.scopes = Constantes.SCOPES;
        publicClientApplication = new PublicClientApplication(ctx, clientid);
    }

    public static synchronized AuthenticationHelper getInstance(Context ctx) {
        if (INSTANCE == null) {
            INSTANCE = new AuthenticationHelper(ctx);
        }

        return INSTANCE;
    }

    // Version appelée à partir de fragments. Ne crée pas de instance s'il n'en existe pas
    public static synchronized AuthenticationHelper getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException(
                    "AuthenticationHelper has not been initialized from MainActivity");
        }

        return INSTANCE;
    }

    public PublicClientApplication getPublicClient() {
        return publicClientApplication;
    }


    public void handleRedirect(int requestCode, int resultCode, Intent data) {
        publicClientApplication.handleInteractiveRequestRedirect(requestCode, resultCode, data);
    }

    // Demander à l'utilisateur de se connecter
    public void acquireTokenInteractively(Activity activity, AuthenticationCallback callback) {
        publicClientApplication.acquireToken(activity, scopes, callback);
    }

    // Connexion silencieuse - utilisée s'il y a déjà un compte d'utilisateur dans le cache MSAL
    public void acquireTokenSilently(final AuthenticationCallback callback) {
        publicClientApplication.getAccounts(new PublicClientApplication.AccountsLoadedCallback() {
            @Override
            public void onAccountsLoaded(List<IAccount> accounts) {
                if(!accounts.isEmpty()){
                    publicClientApplication.acquireTokenSilentAsync(scopes, accounts.get(0), callback);
                }else{
                    /* no account */
                    Log.d(TAG, "No account");
                }
            }
        });

    }

}
