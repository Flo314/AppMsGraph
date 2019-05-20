package com.example.appmsgraph.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Value {

    @SerializedName("value")
    private ArrayList<Fields> value;

    public ArrayList<Fields> getValue() {
        return value;
    }

    public void setValue(ArrayList<Fields> value) {
        this.value = value;
    }
}
