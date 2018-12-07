package com.microsoft.order.common.web.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class RepeatedlyReadFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(RepeatedlyReadFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ServletRequest requestWrapper = null;
        if (request instanceof HttpServletRequest) {
            if (((HttpServletRequest) request).getRequestURI().endsWith("prometheus")) {
                chain.doFilter(request, response);
                return;
            }
            if ("get".equalsIgnoreCase(((HttpServletRequest) request).getMethod())) {
                chain.doFilter(request, response);
                return;
            }
            requestWrapper = new RepeatedlyReadRequestWrapper((HttpServletRequest) request);

        }
        if (null == requestWrapper) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(requestWrapper, response);
        }
    }

    @Override
    public void destroy() {

    }
}
