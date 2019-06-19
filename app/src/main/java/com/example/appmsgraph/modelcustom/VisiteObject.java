package com.example.appmsgraph.modelcustom;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VisiteObject implements Comparable<VisiteObject> {
    private String id;
    private String date;
    private String type;
    private String note;
    private String comment;
    private String newHisto;


    public List<VisiteObject> visitList;

    public static List<VisiteObject> getListAuBonFormat(String historique) {
        List<VisiteObject> listAuBonFormat = new ArrayList<>();

        //data1!data2!data3£data1!data2!data3£data1!data2!data3
        if(historique != null && !historique.isEmpty()) {
            for (String visiteAsString : Arrays.asList(historique.split("£"))) {
                //data1!data2!data3
                String[] res = visiteAsString.split("!");
                VisiteObject visiteObject = new VisiteObject();
                visiteObject.date = res[0];
                visiteObject.type = res[1];
                visiteObject.note = res[2];
                if(visiteObject.comment != null && !visiteObject.comment.isEmpty()){
                    visiteObject.comment = res[3];
                }

                listAuBonFormat.add(visiteObject);
            }
        }
        Collections.sort(listAuBonFormat);
        Collections.reverse(listAuBonFormat);
        return listAuBonFormat;
    }

    public List<VisiteObject> getVisitList() {
        if (visitList == null) {
            visitList = new ArrayList<>();
        }
        return visitList;
    }

//    public void addVisite(VisiteObject visite) {
//        // Je teste d'abord si ma liste n'est pas null
//        // SINON JE RISQUE UNE ERREUR DE TYPE NullPointerException (affectation sur un objet inexistant ou dit null)
//        if (visitList != null) {
//            // Ma liste n'est pas null, je peux ajouter!
//            visitList.add(visite);
//        } else {
//            // Sinon j'instancie ma liste
//            visitList = new ArrayList<>();
//            // Et j'ajoute ma visite
//            visitList.add(visite);
//        }
//    }

    // Constructeur
    public VisiteObject(String date, String type, String note, String comment) {
        this.date = date;
        this.type = type;
        this.note = note;
        this.comment = comment;
    }

    // Constructeur
    public VisiteObject(String newHisto) {
        this.newHisto = newHisto;
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

    @Override
    public int compareTo(VisiteObject o) {
        if(this == o) {
            return 0;
        }else{
            String myFormat = "dd/MM/yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.FRANCE);
            Date date1 =null;
            Date date2 =null;

            try {
                date1 = simpleDateFormat.parse(this.getDate());
                date2 = simpleDateFormat.parse(o.getDate());
                return date1.compareTo(date2);
            } catch (ParseException e) {
                e.printStackTrace();
                return 0;
            }
        }
    }
}

