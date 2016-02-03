package com.polarbear.service.login;

import com.polarbear.domain.Admin;
import com.polarbear.domain.User;
import com.polarbear.util.Constants;
import com.polarbear.util.MD5Util;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class LoginEncoder {
    private static LoginEncoder loginEncoder = new LoginEncoder();

    private LoginEncoder() {
    }

    public static LoginEncoder getInstance() {
        return loginEncoder;
    }

    public String encodeLoginUser(Object user) {
        if (user instanceof User) {
            User u = (User) user;
            return encode(u.getId());
        }
        if (user instanceof Admin) {
            Admin admin = (Admin) user;
            return encode(admin.getId());
        }
        return null;
    }

    private String encode(long uid) {
        // 计算cookie的有效期
        Long validTime = System.currentTimeMillis();
        // MD5加密用户信息，作为校验串
        String verifyCode = MD5Util.encode2hex(new StringBuilder().append(uid).append(":").append(validTime).append(":").append(Constants.WEB_KEY).toString());
        // 计算完整的Cookie值
        String encodeValue = new StringBuilder().append(uid).append(":").append(validTime).append(":").append(verifyCode).toString();
        // 对Cookie的值进行BASE64编码
        return Base64.encode(encodeValue.getBytes());

    }
}