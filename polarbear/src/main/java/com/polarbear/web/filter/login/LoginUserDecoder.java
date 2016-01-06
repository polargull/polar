package com.polarbear.web.filter.login;

import org.apache.commons.lang.StringUtils;

import com.polarbear.ValidateException;
import com.polarbear.util.Constants;
import com.polarbear.util.MD5Util;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class LoginUserDecoder {

    public static long decodeUserId(String loginUserDecode) throws ValidateException {
        if (StringUtils.isEmpty(loginUserDecode)) {
            return 0L;
        }
        String cookieValueDecode = new String(Base64.decode(loginUserDecode));
        String cookieValues[] = cookieValueDecode.split(":");        
        String uid = cookieValues[0];
        String validTime = cookieValues[1];
        String verifyCode = cookieValues[2];
        String verifyCodeTemp = getMD5(uid + ":" + validTime + ":" + Constants.WEB_KEY);
        if (!verifyCodeTemp.equals(verifyCode)) {
            throw new ValidateException("Cookie中的数据校验错误");
        }
        return Long.valueOf(uid);
    }

    private static String getMD5(String value) {
        return MD5Util.encode2hex(value);
    }

}