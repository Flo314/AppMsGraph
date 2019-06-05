package com.example.appmsgraph.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonFormat {

    public static JSONArray jsonToFormat(String s0, String s1 , String s2, String s3, String s4){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", s0 );
            jsonObject.put("date", s1 );
            jsonObject.put("type", s2 );
            jsonObject.put("note", s3 );
            jsonObject.put("comment", s4 );
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(jsonObject);

        return jsonArray;
    }
}
