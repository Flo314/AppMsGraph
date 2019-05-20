package com.example.appmsgraph.network;

import com.example.appmsgraph.model.Fields;
import com.example.appmsgraph.model.Value;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface GetDataService {

    // https://graph.microsoft.com/v1.0/

    // Spécifier le type de requête et transmettre l'URL relative
    @GET("items?expand=fields")

//    Call<List<Fields>> getCollaboratorsData();
    Call<Value> getCollaboratorsData(@Header("Authorization") String authHeader);
}

