package com.example.appmsgraph.network;


import com.example.appmsgraph.modelSharepoint.Value;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface GetDataService {

    // https://graph.microsoft.com/v1.0/

    // Spécifier le type de requête et transmettre l'URL relative
    @GET("items?expand=fields")
    Call<Value> getCollaboratorsData(@Header("Authorization") String authHeader);
    @GET("items?expand=fields(select=id,Title,prenom)")
    Call<Value> getNameCollab(@Header("Authorization") String authHeader);

}

