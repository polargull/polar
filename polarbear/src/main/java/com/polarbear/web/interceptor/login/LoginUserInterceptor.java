package com.polarbear.web.interceptor.login;

import static com.polarbear.util.Constants.ResultState.NEED_LOGIN;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;
import com.polarbear.ValidateException;
import com.polarbear.dao.BaseDao;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.User;
import com.polarbear.util.JsonResult;
import com.polarbear.util.cookie.CookieHelper;
import com.polarbear.util.cookie.UserCookieUtil;
import com.polarbear.util.factory.CurrentThreadUserFactory;

public class LoginUserInterceptor extends HandlerInterceptorAdapter {
    private Log log = LogFactory.getLog(LoginUserInterceptor.class);
    private static Set<String> mayPassUrlsWhenNoLogin = new HashSet<String>();
    
    static {
        mayPassUrlsWhenNoLogin.add("/shopcart/getMyShopcart.json");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("");
        try {
            decodeAndSetUserToThreadLocal(request);            
        } catch (ValidateException e) {
            response.getWriter().write(JSONObject.toJSONString(new JsonResult(e.state)));
            return false;
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.debug("");
        CurrentThreadUserFactory.remove();
    }

    private void decodeAndSetUserToThreadLocal(HttpServletRequest request) throws ValidateException, DaoException {
        long uid = decodeUserId(request);
        CurrentThreadUserFactory.setUser(getUser(request, uid));
    }

    private User getUser(HttpServletRequest request, long uid) throws DaoException {
        BaseDao<User> userDao = getUserDao(request);
        User u = userDao.findById(User.class, uid);
        return u;
    }

    private long decodeUserId(HttpServletRequest request) throws ValidateException {
        String loginUserEncoder = CookieHelper.getCookieValue(request, UserCookieUtil.COOKIE_NAME);
        long uid;
        try {
            uid = LoginUserDecoder.decodeUserId(loginUserEncoder);
        } catch (ValidateException e) {
            if (mayPassUrlsWhenNoLogin.contains(request.getRequestURI())) {
                return 0;
            }
            throw new ValidateException(NEED_LOGIN);
        }
        return uid;
    }

    @SuppressWarnings("unchecked")
    private BaseDao<User> getUserDao(HttpServletRequest request) {
        WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());
        return context.getBean("baseDao", BaseDao.class);
    }
}