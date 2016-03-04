package com.polarbear.web.interceptor.login;

import static com.polarbear.util.Constants.ResultState.NEED_LOGIN;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;
import com.polarbear.ValidateException;
import com.polarbear.dao.BaseDao;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.Admin;
import com.polarbear.util.JsonResult;
import com.polarbear.util.cookie.CookieHelper;
import com.polarbear.util.factory.CurrentThreadAdminFactory;
import com.polarbear.web.login.back.LoginController;

public class LoginAdminInterceptor extends HandlerInterceptorAdapter {
    private Log log = LogFactory.getLog(LoginAdminInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            AdminAuth annotation = method.getAnnotation(AdminAuth.class);
            if (annotation == null)
                return true;
            decodeAndSetAdminToThreadLocal(request);
        } catch (ValidateException e) {
            response.getWriter().write(JSONObject.toJSONString(new JsonResult(e.state)));
            return false;
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        CurrentThreadAdminFactory.remove();
    }

    private void decodeAndSetAdminToThreadLocal(HttpServletRequest request) throws ValidateException, DaoException {
        long uid = decodeUserId(request);
        CurrentThreadAdminFactory.setAdmin(getAdmin(request, uid));
    }

    private Admin getAdmin(HttpServletRequest request, long uid) throws DaoException {
        BaseDao<Admin> userDao = getAdminDao(request);
        Admin admin = userDao.findById(Admin.class, uid);
        return admin;
    }

    private long decodeUserId(HttpServletRequest request) throws ValidateException {
        String loginUserEncoder = CookieHelper.getCookieValue(request, LoginController.ADMIN_LOGIN_COOKIE);
        long uid;
        try {
            uid = LoginUserDecoder.decodeUserId(loginUserEncoder);
        } catch (ValidateException e) {
            throw new ValidateException(NEED_LOGIN);
        }
        return uid;
    }

    @SuppressWarnings("unchecked")
    private BaseDao<Admin> getAdminDao(HttpServletRequest request) {
        WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());
        return context.getBean("baseDao", BaseDao.class);
    }
}