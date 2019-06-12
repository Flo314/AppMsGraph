package com.example.appmsgraph.model;

import java.util.ArrayList;
import java.util.List;

public class ListHistorique {

    private String value;
    private String date;
    private String type;
    private String note;
    private String comment;
    private static List<String> historiqueList;

    public ListHistorique(String date, String type, String note, String comment) {
        this.date = date;
        this.type = type;
        this.note = note;
        this.comment = comment;
    }

    public ListHistorique(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public static List<String> getHistoriqueList() {
        return historiqueList;
    }

    public static void setHistoriqueList(List<String> historiqueList) {
        ListHistorique.historiqueList = historiqueList;
    }

    // Ajout d'une visite dans la liste (conteneur)
    public static void addVisiteHistorique(String visite) {
        if (historiqueList != null) {
            historiqueList.add( visite );
        } else {
            historiqueList = new ArrayList<String>();
            historiqueList.add( visite );
        }
    }

    @Override
    public String toString() {
        return "ListHistorique{" +
                "value='" + value + '\'' +
                ", date='" + date + '\'' +
                ", type='" + type + '\'' +
                ", note='" + note + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
