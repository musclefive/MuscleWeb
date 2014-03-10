package com.musclefive.filter;

import com.musclefive.wrapper.EncodingWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by shixin on 14-3-8.
 */

public class RequestEncodingFilter implements Filter {
    private String tomcatEncoding;
    private String browserEncoding;
    private ServletContext servletContext;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.tomcatEncoding = filterConfig.getInitParameter("tomcat_encoding");
        this.browserEncoding = filterConfig.getInitParameter("browser_encoding");
        this.servletContext = filterConfig.getServletContext();

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        this.servletContext.log("Enter RequestEncodingFilter doFilter.");
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        if ("get".equalsIgnoreCase(httpServletRequest.getMethod())) {
            httpServletRequest = new EncodingWrapper(httpServletRequest, tomcatEncoding, browserEncoding);
        } else {
            httpServletRequest.setCharacterEncoding(browserEncoding);
        }

        filterChain.doFilter(httpServletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        this.servletContext.log("Enter RequestEncodingFilter destroy.");
    }
}
