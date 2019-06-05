package com.example.appmsgraph.network;


import com.example.appmsgraph.model.Value;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface GetDataService {

    // https://graph.microsoft.com/v1.0/

    // Spécifier le type de requête et transmettre l'URL relative
    @GET("items?expand=fields")
    Call<Value> getCollaboratorsData(@Header("Authorization") String authHeader);
    @GET("items?expand=fields(select=ID,Title,prenom)")
    Call<Value> getNameCollab(@Header("Authorization") String authHeader);

}

