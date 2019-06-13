package com.example.appmsgraph.modelcustom;

import android.util.Log;

import com.example.appmsgraph.screens.MainActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VisiteObject {
    private String id;
    private String date;
    private String type;
    private String note;
    private String comment;

    public static List<VisiteObject> visitList;

    public List<VisiteObject> getListAuBonFormat(String historique) {
        List<VisiteObject> listAuBonFormat = new ArrayList<>();
        VisiteObject visiteObject = new VisiteObject();
        //data1!data2!data3£data1!data2!data3£data1!data2!data3
        for(String visiteAsString : Arrays.asList(historique.split("£"))){
            //data1!data2!data3
            String[] res = visiteAsString.split("!");
                visiteObject.date = res[0];
                visiteObject.type = res[1];
                visiteObject.note = res[2];
                visiteObject.comment = res[3];
                listAuBonFormat.add(visiteObject);
        }
        return listAuBonFormat;
    }

    public static List<VisiteObject> getVisitList() {
        if (visitList == null) {
            visitList = new ArrayList<>();
        }
        return visitList;
    }

    public static void addVisite(VisiteObject visite) {
        // Je teste d'abord si ma liste n'est pas null
        // SINON JE RISQUE UNE ERREUR DE TYPE NullPointerException (affectation sur un objet inexistant ou dit null)
        if (visitList != null) {
            // Ma liste n'est pas null, je peux ajouter!
            visitList.add(visite);
        } else {
            // Sinon j'instancie ma liste
            visitList = new ArrayList<>();
            // Et j'ajoute ma visite
            visitList.add(visite);
        }
    }

    // Constructeur
    public VisiteObject(String id, String date, String type, String note, String comment) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.note = note;
        this.comment = comment;
    }

    // Constructeur
    public VisiteObject() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "VisiteObject{" +
                "id='" + id + '\'' +
                ", date='" + date + '\'' +
                ", type='" + type + '\'' +
                ", note='" + note + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}

