package com.example.appmsgraph.utils;



import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class CompareDate {

    /*Debug*/
    public String TAG = "";

    public CompareDate() {}

    /**
     *
     * @param stringDate représente la chaîne de la liste sharepoint visite au format 'dd/MM/yyyy'
     * @return le nombres de jours qui sépare la date du jour avec celle entré en paramètre
     */
    public String getCompareDate(String stringDate) {


        // date system
        DateFormat current = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        Calendar calendar = Calendar.getInstance();

        try {
            Date formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE).parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calStr2 = Calendar.getInstance();
        Calendar calStr0 = Calendar.getInstance();
        Date date1 = null;
        Date date2 = null;
        int nbMois = 0;
        int nbAnnees = 0;
        long nbJours = 0;
        final int redIndicator = -30;
        final int orangeIndicator = -20;
        String red = "RED";
        String orange = "ORANGE";
        String green = "GREEN";


        try {
            date1 = new SimpleDateFormat("dd/MM/yyyy").parse(current.format(calendar.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            date2 = new SimpleDateFormat("dd/MM/yyyy").parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

//        if(date1.equals(date2))

        calStr2.setTime(date2);
        calStr0 = Calendar.getInstance();
        calStr0.setTime(date1);
        calStr0.add(GregorianCalendar.YEAR, nbAnnees);
        calStr0.add(GregorianCalendar.MONTH, nbMois);
        nbJours = (calStr2.getTimeInMillis() - calStr0.getTimeInMillis()) / 86400000;

        if(redIndicator >= nbJours){
            TAG = red;
            return TAG;
        }else if(orangeIndicator >= nbJours){
            TAG = orange;
            return TAG;
        }else{
            TAG = green;
            return TAG;
        }
    }

    // Récupère le nombre de jours entre deux dates
//    public static double getDaysBetweenTwoDate(Date srcDate, Date destDate) {
//        long startTime = srcDate.getTime();
//        long destTime = destDate.getTime();
//        long deltaTime = destTime - startTime;
//        long oneDayTime = 24 * 60 * 60 * 1000;
//        double deltaDay = Math.abs(deltaTime / oneDayTime);
//        return deltaDay;
//    }

    @Override
    public String toString() {
        return "CompareDate{" +
                TAG + '\'' +
                '}';
    }

}
