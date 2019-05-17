package com.example.appmsgraph.network;

import com.example.appmsgraph.model.Fields;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface GetDataService {

    // https://graph.microsoft.com/v1.0/me

    // Spécifier le type de requête et transmettre l'URL relative
    @GET("lists/7ad30a01-7a94-4e6e-97a9-938e81822ff5/items?expand=fields")

    // Call méthode type retour EmployeeList
    // @Query = paramètre d'URL
    Call<List<Fields>> getCollaboratorsData(@Header("authorization") String auth);
}

