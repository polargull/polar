package com.polarbear.web.filter.monitor;

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
        log.info("url:" + httpRequest.getRequestURI() + ", time consuming:" + (System.currentTimeMillis() - beginTime));
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

}
