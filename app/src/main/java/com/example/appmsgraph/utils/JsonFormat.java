package com.example.appmsgraph.utils;

import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonFormat {

    // transforme les champ du formulaire en objet JSON
    public static JSONObject jsonToFormatObject(String s0, String s1, String s2, String s3, String s4) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", s0);
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
