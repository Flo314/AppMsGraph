package com.example.appmsgraph.utils;

import java.util.ArrayList;

public class TransformArrayAndParseString {

    // transforme un arraylist en tableau de string
    public static String[] getStringArray(ArrayList<String> array){
        String[] str = new String[array.size()];

        for(int i = 0; i < array.size(); i++){
            str[i] = array.get(i);
        }
        return str;
    }

}
