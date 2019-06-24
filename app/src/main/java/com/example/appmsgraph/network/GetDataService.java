package com.example.appmsgraph.network;


import com.example.appmsgraph.modelSharepoint.Fields;
import com.example.appmsgraph.modelSharepoint.Value;
import com.example.appmsgraph.modelcustom.VisiteObject;
import com.google.gson.JsonObject;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Mise en place des différents point de terminaison apellé dans l'app
 */
public interface GetDataService {

    // https://graph.microsoft.com/v1.0/

    // Spécifier le type de requête et transmettre l'URL relative
    @GET("items?expand=fields")
    Call<Value> getCollaboratorsData(@Header("Authorization") String authHeader);

    @PATCH("items/{id}/fields")
    Call<Value> updateData(@Header("Authorization") String authHeader, @Path("id") String id, @Body Fields histo);

    @PATCH("items/{id}/fields")
    Call<Value> updateDate(@Header("Authorization") String authHeader, @Path("id") String id, @Body Fields date);

}

