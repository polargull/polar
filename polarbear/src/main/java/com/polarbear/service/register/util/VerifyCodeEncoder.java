package com.polarbear.service.register.util;

import com.polarbear.util.Constants;
import com.polarbear.util.date.IClock;
import com.polarbear.util.date.SystemClock;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.net.URLDecoder;
public class VerifyCodeEncoder {

    IClock clock = new SystemClock();
    
    public VerifyCodeEncoder setClock(IClock clock) {
        this.clock = clock;
        return this;
    }

    public String encodeNeedCompareVerifyCode(int verifyCode, long cellphone) throws UnsupportedEncodingException {
        StringBuilder needCompareVerifyCode = new StringBuilder();
        needCompareVerifyCode.append(verifyCode).append(":").append(cellphone).append(":").append(clock.now().getMillis()).append(":").append(Constants.WEB_KEY);
        return URLEncoder.encode(Base64.encode(needCompareVerifyCode.toString().getBytes()),"utf-8");
    }
    
    private String decode(String encode) {
        return new String(Base64.decode(encode));
    }
    
    public static void main(String[] args) throws UnsupportedEncodingException {
        System.out.println(new VerifyCodeEncoder().encodeNeedCompareVerifyCode(123456,12345678901l));
        String urlDecode = URLDecoder.decode(new VerifyCodeEncoder().encodeNeedCompareVerifyCode(123456,12345678901l),"utf-8");
        
        
        System.out.println(new VerifyCodeEncoder().decode(urlDecode));
    }
}
