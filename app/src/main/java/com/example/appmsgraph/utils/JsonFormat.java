package com.example.appmsgraph.utils;


import org.json.JSONException;
import org.json.JSONObject;

public class JsonFormat {

    // transforme les champ du formulaire en objet JSON
    public static JSONObject jsonToFormatObject(String s1, String s2, String s3, String s4) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("date", s1);
            jsonObject.put("type", s2);
            jsonObject.put("note", s3);
            jsonObject.put("comment", s4);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    // transforme l'objet JSON en un tableau d'objet JSON
//    public static JSONArray jsonToFormatArrayPush(JSONObject jsonObject){
//        JSONArray jsonArray = new JSONArray();
//        jsonArray.put(jsonObject);
//        return jsonArray;
//    }
//
//    public static JSONArray jsonToFormatArray(){
//        return new JSONArray();
//    }

}
