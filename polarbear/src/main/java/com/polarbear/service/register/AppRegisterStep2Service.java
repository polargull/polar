package com.polarbear.service.register;

import static com.polarbear.util.Constants.ResultState.PARAM_ERR;
import static com.polarbear.util.Constants.ResultState.VERIFY_CODE_INVIDIT;

import org.apache.commons.lang.math.NumberUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.polarbear.ValidateException;
import com.polarbear.dao.BaseDao;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.User;
import com.polarbear.service.login.LoginData;
import com.polarbear.util.MD5Util;
import com.polarbear.util.date.IClock;
import com.polarbear.util.date.SystemClock;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

@Service
public class AppRegisterStep2Service {
    @Autowired
    private BaseDao<User> userDao;
    IClock systemClock = new SystemClock();
    
    public LoginData completeStep2(int verifyCode, String encodeVerifyCode, String pwd) throws ValidateException, NumberFormatException, DaoException {
        validateVerifyCode(verifyCode, decodeVerifyCode(encodeVerifyCode), decodeVerifyCodeCreateTime(encodeVerifyCode));
        validateCellphone(decodeCellphone(encodeVerifyCode));
        User user = storeUser(Long.valueOf(decodeCellphone(encodeVerifyCode)), pwd);
        return new LoginData(user);
    }

    private void validateVerifyCode(int verifyCode, String encodeVerifyCode, long decodeVerifyCodeCreateTime) throws ValidateException {
        if (verifyCode != Integer.parseInt(encodeVerifyCode)) {
            throw new ValidateException(PARAM_ERR);
        }
        if (systemClock.now().getMillis() - decodeVerifyCodeCreateTime > 1000 * 60) {
            throw new ValidateException(VERIFY_CODE_INVIDIT);
        }
    }

    private void validateCellphone(String cellphone) throws ValidateException {
        if (!NumberUtils.isDigits(cellphone) || cellphone.trim().length() != 11) {
            throw new ValidateException(PARAM_ERR);
        }
    }

    private User storeUser(long cellPhone, String pwd) throws DaoException {
        String md5_pwd = MD5Util.encode2hex(pwd);
        User user = new User(String.valueOf(cellPhone), md5_pwd, cellPhone);
        userDao.store(user);
        return user;
    }

    private String decodeVerifyCode(String encodeVerifyCode) {
        return verifyDecode(encodeVerifyCode)[0];
    }

    private long decodeVerifyCodeCreateTime(String encodeVerifyCode) {
        return Long.parseLong(verifyDecode(encodeVerifyCode)[2]);
    }
    
    private String decodeCellphone(String encodeVerifyCode) {
        return verifyDecode(encodeVerifyCode)[1];
    }

    private String[] verifyDecode(String encodeVerifyCode) {
        String verifyDecodeCode = new String(Base64.decode(encodeVerifyCode));
        return verifyDecodeCode.split(":");
    }
}
