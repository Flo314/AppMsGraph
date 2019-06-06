package com.example.appmsgraph.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    public static Boolean validator(String comment) {
        boolean valid = true;
        if (comment != null && !comment.isEmpty()){
            Pattern pattern = Pattern.compile("^[a-zA-Z0-9áàâäãåçéèêëíìîïñóòôöõúùûüýÿæœÁÀÂÄÃÅÇÉÈÊËÍÌÎÏÑÓÒÔÖÕÚÙÛÜÝŸÆŒ._' -!?,;:\\-]{2,150}$");
            Matcher matcher = pattern.matcher(comment);
            if (!matcher.find()) {
                valid = false;
            }
        }
        return valid;
    }
}
