package com.polarbear.service.login;

import static com.polarbear.util.Constants.ResultState.LOGIN_NAME_PWD_ERR;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.polarbear.dao.BaseDao;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.User;
import com.polarbear.service.login.bean.LoginData;
import com.polarbear.service.login.util.LoginEncoder;
import com.polarbear.util.MD5Util;

@Service
public class UserLoginService {
    @Autowired(required = false)
    BaseDao<User> userDao;
    LoginEncoder loginEncoder = LoginEncoder.getInstance();

    public LoginData<User> login(String uname, String pwd) throws LoginException, DaoException {
        User user = userDao.findByNamedQueryObject("queryUnameAndPwd", uname, MD5Util.encode2hex(pwd));
        if (user == null)
            throw new LoginException(LOGIN_NAME_PWD_ERR.emsg());
        return new LoginData<User>(user, loginEncoder.encodeLoginUser(user));
    }
    
}