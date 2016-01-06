package com.polarbear.web.filter.login;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.polarbear.ValidateException;
import com.polarbear.util.cookie.CookieHelper;
import com.polarbear.util.cookie.UserCookieUtil;

public class LoginUserFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String loginUserEncoder = CookieHelper.getCookieValue(httpRequest, UserCookieUtil.COOKIE_NAME);
        try {
            long uid = LoginUserDecoder.decodeUserId(loginUserEncoder);
            httpRequest.setAttribute("uid", uid);
        } catch (ValidateException e) {
            throw new ServletException(e.getMessage());
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}