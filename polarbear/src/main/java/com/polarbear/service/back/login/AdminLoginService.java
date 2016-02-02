package com.polarbear.service.back.login;

import static com.polarbear.util.Constants.ResultState.LOGIN_NAME_PWD_ERR;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.polarbear.dao.BaseDao;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.Admin;
import com.polarbear.util.MD5Util;

@Service
public class AdminLoginService {
    @Autowired(required = false)
    BaseDao<Admin> adminDao;

    public Admin login(String uname, String pwd) throws LoginException, DaoException {
        Admin admin = adminDao.findByNamedQueryObject("queryAdminNameAndPwd", uname, MD5Util.encode2hex(pwd));
        if (admin == null)
            throw new LoginException(LOGIN_NAME_PWD_ERR.emsg());
        return admin;
    }
}