package com.githubsearch.utils;


import java.util.Date;


/**
 * Created by den on 04.09.17.
 */

public class DateUtils {

    public static String calculateTimeDifference(Date date1, Date date2, String hours, String minutes, String days) {

        long diff = date1.getTime() - date2.getTime();

        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);

        if (diffDays <= 0 && diffHours > 0) {
            return diffHours + " " + hours;
        } else if (diffHours <= 0 && diffDays <= 0 && diffMinutes > 0) {
            return diffMinutes + " " + minutes;
        } else if (diffMinutes <= 0 && diffDays <= 0 && diffHours <= 0 && diffSeconds > 0) {
            return diffSeconds + " sec";
        } else {
            return diffDays + " " + days;
        }
    }
}

