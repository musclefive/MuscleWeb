package com.musclefive.filter;

import com.musclefive.wrapper.EncodingWrapper;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by shixin on 14-3-8.
 */

public class RequestEncodingFilter implements Filter {
    private static Logger logger = Logger.getLogger(RequestEncodingFilter.class);

    private String tomcatEncoding;
    private String browserEncoding;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.tomcatEncoding = filterConfig.getInitParameter("tomcat_encoding");
        this.browserEncoding = filterConfig.getInitParameter("browser_encoding");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("Enter RequestEncodingFilter doFilter.");

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
    }
}
