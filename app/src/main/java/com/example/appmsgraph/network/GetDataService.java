package com.example.appmsgraph.network;

import com.example.appmsgraph.Collaborators;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetDataService {

    // https://graph.microsoft.com/v1.0/me

    // nom du fichier qui fournira les données JSON
    // Spécifier le type de requête et transmettre l'URL relative
    @GET("me")

    // Call méthode type retour EmployeeList
    // @Query = paramètre d'URL
    Call<List<Collaborators>> getCollaboratorsData();
}

