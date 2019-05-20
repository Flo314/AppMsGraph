package com.example.appmsgraph.network;

import com.example.appmsgraph.model.Value;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface GetDataService {

    // https://graph.microsoft.com/v1.0/

    // Spécifier le type de requête et transmettre l'URL relative
    @GET("sites/florentlosada.sharepoint.com,90de94ff-5d8d-421d-9a66-ee771a9c0f3a,6dc382be-7b69-4e26-a362-596db959c2fa/lists/7ad30a01-7a94-4e6e-97a9-938e81822ff5/items?expand=fields")

//    Call<List<Fields>> getCollaboratorsData();
    Call<List<Value>> getCollaboratorsData(@Header("Authorization") String authHeader);
}

