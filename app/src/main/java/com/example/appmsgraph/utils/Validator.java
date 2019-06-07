package com.example.appmsgraph.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {


    public static Boolean validator(String comment) {
        boolean valid = true;
        if (comment.isEmpty()) {
            Pattern pattern = Pattern.compile("^[a-zA-Z0-9áàâäãåçéèêëíìîïñóòôöõúùûüýÿæœÁÀÂÄÃÅÇÉÈÊËÍÌÎÏÑÓÒÔÖÕÚÙÛÜÝŸÆŒ._' -!?,;:\\-]{0,150}$");
            Matcher matcher = pattern.matcher(comment);
            if (!matcher.find()) {
                valid = false;
            }
        }
        return valid;
    }

    public static Boolean validatorDate(String date) {
        boolean valid = true;
        if (date.isEmpty()) {
            Pattern pattern = Pattern.compile("^(0[1-9]|[12][0-9]|[3][01])/(0[1-9]|1[012])/\\\\d{4}$");
            Matcher matcher = pattern.matcher(date);
            if (!matcher.find()) {
                valid = false;
            }
        }
        return valid;
    }
}
