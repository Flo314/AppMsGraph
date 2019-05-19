package com.example.appmsgraph.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static Retrofit retrofit;
    final static String MSGRAPH_URL = "https://graph.microsoft.com/v1.0/sites/florentlosada.sharepoint.com,90de94ff-5d8d-421d-9a66-ee771a9c0f3a,6dc382be-7b69-4e26-a362-596db959c2fa/";

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
