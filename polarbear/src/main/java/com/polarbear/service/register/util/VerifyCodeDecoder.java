package com.polarbear.service.register.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
public class VerifyCodeDecoder {

    public static String decodeVerifyCode(String encodeVerifyCode) throws UnsupportedEncodingException {
        return verifyDecode(encodeVerifyCode)[0];
    }

    public static long decodeVerifyCodeCreateTime(String encodeVerifyCode) throws NumberFormatException, UnsupportedEncodingException {
        return Long.parseLong(verifyDecode(encodeVerifyCode)[2]);
    }

    public static String decodeCellphone(String encodeVerifyCode) throws UnsupportedEncodingException {
        return verifyDecode(encodeVerifyCode)[1];
    }

    private static String[] verifyDecode(String encodeVerifyCode) throws UnsupportedEncodingException {
        String urlDecode = URLDecoder.decode(encodeVerifyCode, "utf-8");
        String verifyDecodeCode = new String(Base64.decode(urlDecode));
        return verifyDecodeCode.split(":");
    }
    
    public static void main(String[] args) throws UnsupportedEncodingException {
        
    }
}
