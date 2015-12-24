package com.polarbear.service.product.util;

public class StylePropertyTransferUtil {
    public static String propertyStrToJson(String propertyStr) {
        StringBuilder sb = new StringBuilder(propertyStr.replaceAll(",", "\",\"").replaceAll(":", "\":\""));
        return sb.insert(0, "{\"").append("\"}").toString();
    }
}
