package com.example.appmsgraph.modelSharepoint;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Value {

    @SerializedName("value")
    private ArrayList<Value_> value;

    public ArrayList<Value_> getValue() {
        return value;
    }

    public void setValue(ArrayList<Value_> value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Value{" +
                "value=" + value +
                '}';
    }
}
