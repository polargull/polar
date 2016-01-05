package com.polarbear.util;

import org.joda.time.DateTime;

/**
 * 时间与integer类型互换
 * 
 * @author
 * 
 */
public class DateUtil {

    public static String secondsToDateString(Integer seconds, String... format) {
        if (format.length == 0) {
            return new DateTime(seconds * 1000L).toString("yyyy-MM-dd HH:mm:ss");
        }
        return new DateTime(seconds * 1000L).toString(format[0]);
    }

    public static int getCurrentSeconds() {
        return (int) ((new DateTime().getMillis()) / 1000);
    }

    public static void main(String[] args) {
        System.out.println(getCurrentSeconds() * 1000l);
    }
}