package com.example.appmsgraph.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Librairie Retrofit permettant l'utilisation d'API
 */
public class RetrofitInstance {

    private static Retrofit retrofit;
    final static String MSGRAPH_URL = "https://graph.microsoft.com/v1.0/";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(MSGRAPH_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
