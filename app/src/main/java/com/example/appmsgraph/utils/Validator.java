package com.example.appmsgraph.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Cette classe s'ocuppe unique de contrôler le formulaire
 * qui représente L'activity AddVisite et UpdateVisite
 */
public class Validator {


    // Imput commentaire
    public static Boolean validator(String comment) {
        boolean valid = true;
        if (comment != null && !comment.isEmpty()){
            Pattern pattern = Pattern.compile("^[a-zA-Z0-9áàâäãåçéèêëíìîïñóòôöõúùûüýÿæœÁÀÂÄÃÅÇÉÈÊËÍÌÎÏÑÓÒÔÖÕÚÙÛÜÝŸÆŒ._' -!?,;:\\-]{0,150}$");
            Matcher matcher = pattern.matcher(comment);
            if (!matcher.find()) {
                valid = false;
            }
        }
        return valid;
    }

    // Imput datepicker
    public static Boolean validatorDate(String date) {
        boolean valid = true;
        if (date.equals("")|| date.equals("Date")) {
            Pattern pattern = Pattern.compile("^(0[1-9]|[12][0-9]|[3][01])/(0[1-9]|1[012])/\\\\d{4}$");
            Matcher matcher = pattern.matcher(date);
            if (!matcher.find()) {
                valid = false;
            }
        }
        return valid;
    }
}
