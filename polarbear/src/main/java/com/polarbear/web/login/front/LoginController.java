package com.polarbear.web.login.front;

import static com.polarbear.util.Constants.ResultState.LOGIN_NAME_PWD_ERR;
import static com.polarbear.util.Constants.ResultState.SUCCESS;

import java.net.MalformedURLException;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysql.jdbc.StringUtils;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.User;
import com.polarbear.service.login.UserLoginService;
import com.polarbear.service.login.bean.LoginData;
import com.polarbear.service.shopcart.ShopcartService;
import com.polarbear.util.JsonResult;
import com.polarbear.util.cookie.CookieHelper;
import com.polarbear.util.cookie.UrlUtil;
import com.polarbear.util.factory.CurrentThreadUserFactory;

@Controller
public class LoginController {
    private Log log = LogFactory.getLog(LoginController.class);
    @Autowired(required = false)
    private UserLoginService loginService;
    @Autowired(required = false)
    private ShopcartService shopcartService;
    public static final String USER_LOGIN_COOKIE = "Login_User";
    public static final String SHOPCART_COOKIE = "shopcart";

    @RequestMapping(value = { "login.json", "login.do" }, method = { RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public Object login(HttpServletResponse response, HttpServletRequest request, @CookieValue(value = "shopcart", required = false, defaultValue = "") String shopcartCookieData,
            @RequestParam("uname") String uname, @RequestParam("password") String password) throws MalformedURLException, LoginException, DaoException {
        log.debug("uname:" + uname + ",password:" + password);
        validate(uname, password);
        LoginData<User> userLoginData = loginService.login(uname, password);
        setLoginUserCookie(response, request, userLoginData);
        CurrentThreadUserFactory.setUser(userLoginData.getUser());
        synchData(response, request, shopcartCookieData);
        return new JsonResult(SUCCESS).put(userLoginData);
    }

    private void synchData(HttpServletResponse response, HttpServletRequest request, String shopcartCookieData) {
        try {
            shopcartService.synchClientShopcartData(shopcartCookieData);
            clearShopcartCookie(response, request);
        } catch (Exception e) {
            log.error("login synch data error, msg:" + e.getMessage());
        }
    }

    private void clearShopcartCookie(HttpServletResponse response, HttpServletRequest request) throws MalformedURLException {
        String domain = UrlUtil.getTopDomainWithoutSubdomain(request.getRequestURL().toString());
        CookieHelper.setCookie(response, SHOPCART_COOKIE, "", domain, 0);
    }

    private void setLoginUserCookie(HttpServletResponse response, HttpServletRequest request, LoginData<User> userLoginData) throws MalformedURLException {
        String domain = UrlUtil.getTopDomainWithoutSubdomain(request.getRequestURL().toString());
        CookieHelper.setCookie(response, USER_LOGIN_COOKIE, userLoginData.getAuthEncode(), domain, 0);
    }

    private void validate(String uname, String password) throws LoginException {
        if (StringUtils.isNullOrEmpty(uname) || StringUtils.isNullOrEmpty(password)) {
            throw new LoginException(LOGIN_NAME_PWD_ERR.emsg());
        }
    }
}
