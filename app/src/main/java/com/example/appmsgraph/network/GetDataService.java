package com.example.appmsgraph.network;


import com.example.appmsgraph.modelSharepoint.Fields;
import com.example.appmsgraph.modelSharepoint.User;
import com.example.appmsgraph.modelSharepoint.UserConnect;
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
    // base URL dans retrofit Instance
    // https://graph.microsoft.com/v1.0/
    @GET("me")
    Call<UserConnect> getUsers(@Header("Authorization") String authHeader);

    // Spécifier le type de requête et transmettre l'URL relative
    @GET("sites/florentlosada.sharepoint.com,90de94ff-5d8d-421d-9a66-ee771a9c0f3a,6dc382be-7b69-4e26-a362-596db959c2fa/lists/7ad30a01-7a94-4e6e-97a9-938e81822ff5/items?expand=fields")
    Call<Value> getCollaboratorsData(@Header("Authorization") String authHeader);

    @PATCH("sites/florentlosada.sharepoint.com,90de94ff-5d8d-421d-9a66-ee771a9c0f3a,6dc382be-7b69-4e26-a362-596db959c2fa/lists/7ad30a01-7a94-4e6e-97a9-938e81822ff5/items/{id}/fields")
    Call<Value> updateData(@Header("Authorization") String authHeader, @Path("id") String id, @Body Fields histo);

    @PATCH("sites/florentlosada.sharepoint.com,90de94ff-5d8d-421d-9a66-ee771a9c0f3a,6dc382be-7b69-4e26-a362-596db959c2fa/lists/7ad30a01-7a94-4e6e-97a9-938e81822ff5/items/{id}/fields")
    Call<Value> updateDate(@Header("Authorization") String authHeader, @Path("id") String id, @Body Fields date);

}

