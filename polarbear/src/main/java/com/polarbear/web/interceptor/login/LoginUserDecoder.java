package com.polarbear.web.interceptor.login;

import org.apache.commons.lang.StringUtils;

import com.polarbear.ValidateException;
import com.polarbear.util.Constants;
import com.polarbear.util.MD5Util;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import static com.polarbear.util.Constants.ResultState.*;

public class LoginUserDecoder {

    public static long decodeUserId(String loginUserDecode) throws ValidateException {
        if (StringUtils.isEmpty(loginUserDecode)) {
            throw new ValidateException(COOKIE_ERR);
        }
        String cookieValueDecode = new String(Base64.decode(loginUserDecode));
        String cookieValues[] = cookieValueDecode.split(":");
        String uid = cookieValues[0];
        String validTime = cookieValues[1];
        String verifyCode = cookieValues[2];
        String verifyCodeTemp = getMD5(uid + ":" + validTime + ":" + Constants.WEB_KEY);
        if (!verifyCodeTemp.equals(verifyCode)) {
            throw new ValidateException(COOKIE_ERR);
        }
        return Long.valueOf(uid);
    }

    private static String getMD5(String value) {
        return MD5Util.encode2hex(value);
    }

}