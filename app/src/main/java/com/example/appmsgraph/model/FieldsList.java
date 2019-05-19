package com.example.appmsgraph.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FieldsList {

    @SerializedName("fields")
    private ArrayList<Fields> fieldsList;

    public ArrayList<Fields> getFieldsList() {
        return fieldsList;
    }

    public void setFieldsList(ArrayList<Fields> fieldsList) {
        this.fieldsList = fieldsList;
    }
}
