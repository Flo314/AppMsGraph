package com.example.appmsgraph.modelSharepoint;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FieldsList {

    @SerializedName("fieldslist")
    private List<Fields> fieldsList;

    public List<Fields> getFieldsList() {
        return fieldsList;
    }

    public void setFieldsList(List<Fields> fieldsList) {
        this.fieldsList = fieldsList;
    }
}
