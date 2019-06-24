package com.example.appmsgraph.auth;

/**
 * Configuration de l'application (plateforme Azure cloud microsoft)
 */
public class Constantes {

    // Sites.Read.All, Sites.ReadWrite.All "SecurityActions.ReadWrite.All"
    public static final String[] SCOPES = {"openid", "Sites.ReadWrite.All", "SecurityActions.ReadWrite.All"};
    public static final String CLIENT_ID = "622816c7-17d9-47fa-8572-b742f2e55691";
}
