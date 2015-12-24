package com.polarbear.service.register;

import static com.polarbear.util.Constants.ResultState.PARAM_ERR;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.polarbear.ValidateException;
import com.polarbear.dao.BaseDao;
import com.polarbear.domain.User;
import com.polarbear.util.MD5Util;

@Service
public class AppRegisterStep2Service {
    @Autowired
    private BaseDao<User> userDao;

    public void completeStep2(int verifyCode, String encodeVerifyCode, long cellPhone, String pwd) throws ValidateException {
        validateVerifyCode(verifyCode, encodeVerifyCode);
//        validateCellphone(cellPhone);
        storeUser(cellPhone, pwd);
    }

    private void storeUser(long cellPhone, String pwd) {
        String md5_pwd = MD5Util.encode2hex(pwd);
        userDao.store(new User(String.valueOf(cellPhone), md5_pwd, cellPhone));
    }

    private void validateVerifyCode(int verifyCode, String encodeVerifyCode) throws ValidateException {
        throw new ValidateException(PARAM_ERR);
    }
    
    private void validateCellphone() {
        
    }
}
