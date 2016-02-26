package com.polarbear.service.login;

import static com.polarbear.util.Constants.ResultState.LOGIN_NAME_PWD_ERR;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.polarbear.dao.BaseDao;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.Admin;
import com.polarbear.service.login.bean.LoginData;
import com.polarbear.service.login.util.LoginEncoder;
import com.polarbear.util.security.MD5Util;

@Service
public class AdminLoginService {
    @Autowired(required = false)
    BaseDao<Admin> adminDao;
    LoginEncoder loginEncoder = LoginEncoder.getInstance();

    public LoginData<Admin> login(String uname, String pwd) throws LoginException, DaoException {
        Admin admin = adminDao.findByNamedQueryObject("queryAdminNameAndPwd", uname, MD5Util.encode2hex(pwd));
        if (admin == null)
            throw new LoginException(LOGIN_NAME_PWD_ERR.emsg());
        return new LoginData<Admin>(admin, loginEncoder.encodeLoginUser(admin));
    }
}