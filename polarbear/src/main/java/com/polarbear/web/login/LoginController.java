package com.polarbear.web.login;

import static com.polarbear.util.Constants.ResultState.LOGIN_NAME_PWD_ERR;
import static com.polarbear.util.Constants.ResultState.SUCCESS;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysql.jdbc.StringUtils;
import com.polarbear.service.login.LoginData;
import com.polarbear.service.login.LoginService;
import com.polarbear.util.JsonResult;
import com.polarbear.util.cookie.UserCookieUtil;

@Controller
public class LoginController {
    @Autowired(required = false)
    private LoginService loginService;

    @RequestMapping(value = {"login.json","login.do"}, method = { RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public Object login(HttpServletResponse response, HttpServletRequest request, @RequestParam("uname") String uname, @RequestParam("password") String password) {
        try {
            validate(uname, password);
            LoginData loginData = loginService.login(uname, password);
            // TODO 登录token写入cookie in facade
            UserCookieUtil.saveUserCookie(loginData.getUser(), request, response, 0);
            return new JsonResult(SUCCESS).put(LoginData.class.getSimpleName(), loginData);
        } catch (LoginException e) {
            return new JsonResult(LOGIN_NAME_PWD_ERR);
        }
    }

    private void validate(String uname, String password) throws LoginException {
        if (StringUtils.isNullOrEmpty(uname) || StringUtils.isNullOrEmpty(password)) {
            throw new LoginException(LOGIN_NAME_PWD_ERR.emsg());
        }
    }
}
