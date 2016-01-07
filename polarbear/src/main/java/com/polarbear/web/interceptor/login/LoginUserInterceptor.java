package com.polarbear.web.interceptor.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

public class LoginUserInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            setUserToRequest(request);
        } catch (ValidateException e) {
            response.getWriter().write(JSONObject.toJSONString(new JsonResult(e.state)));
            return false;
        }
        return true;
    }

    private void setUserToRequest(HttpServletRequest request) throws ValidateException, DaoException {
        long uid = decodeUserId(request);        
        request.setAttribute("user", getUser(request, uid));
    }

    private User getUser(HttpServletRequest request, long uid) throws DaoException {
        BaseDao<User> userDao = getUserDao(request);
        User u = userDao.findById(User.class, uid);
        return u;
    }

    private long decodeUserId(HttpServletRequest request) throws ValidateException {
        String loginUserEncoder = CookieHelper.getCookieValue(request, UserCookieUtil.COOKIE_NAME);
        long uid = LoginUserDecoder.decodeUserId(loginUserEncoder);
        return uid;
    }

    @SuppressWarnings("unchecked")
    private BaseDao<User> getUserDao(HttpServletRequest request) {
        WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());
        return context.getBean("baseDao", BaseDao.class);
    }
}