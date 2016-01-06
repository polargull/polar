package com.polarbear.web.interceptor.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.polarbear.util.cookie.CookieHelper;
import com.polarbear.util.cookie.UserCookieUtil;
import com.polarbear.web.interceptor.AbstractHandlerInterceptor;

@Component
public class LoginUserInterceptor extends AbstractHandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String loginUserEncoder = CookieHelper.getCookieValue(request, UserCookieUtil.COOKIE_NAME);
        long uid = LoginUserDecoder.decodeUserId(loginUserEncoder);
        
        return true;
    }
}