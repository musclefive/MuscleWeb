package com.musclefive.filter;

import com.musclefive.wrapper.GZIPWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

/**
 * Created by shixin on 14-3-9.
 */
public class CompressionFilter implements Filter {
    private ServletContext servletContext;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.servletContext = filterConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String encoding = httpServletRequest.getHeader("accept-encoding");
        if (null != encoding && encoding.indexOf("gzip") > -1) {
            GZIPWrapper gzipWrapper = new GZIPWrapper((HttpServletResponse) servletResponse);
            gzipWrapper.setHeader("content-encoding", "gzip");

            filterChain.doFilter(servletRequest, gzipWrapper);

            GZIPOutputStream gzipOutputStream = gzipWrapper.getGzipOutputStream();
            if (null != gzipOutputStream) {
                gzipOutputStream.close();
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
    }
}
