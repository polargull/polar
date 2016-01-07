package com.polarbear.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PerformanceMonitorFilter implements Filter {
    private static Log log = LogFactory.getLog(PerformanceMonitorFilter.class);

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        long beginTime = System.currentTimeMillis();
        chain.doFilter(request, response);
        StringBuilder infosb = new StringBuilder("url:").append(httpRequest.getRequestURI())
        .append(", time consuming:").append(System.currentTimeMillis() - beginTime).append("ms");
        log.info(infosb.toString());
        log.debug("response:"+response.getCharacterEncoding());
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

}
