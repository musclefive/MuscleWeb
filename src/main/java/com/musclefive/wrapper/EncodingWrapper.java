package com.musclefive.wrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.UnsupportedEncodingException;

/**
 * Created by shixin on 14-3-8.
 */
public class EncodingWrapper extends HttpServletRequestWrapper {
    private String tomcatEncoding = "ISO-8859-1";
    private String browserEncoding;

    public EncodingWrapper(HttpServletRequest request, String tomcatEncoding, String browserEncoding) {
        super(request);
        this.tomcatEncoding = tomcatEncoding;
        this.browserEncoding = browserEncoding;
    }

    @Override
    public String getParameter(String parameterName) {
        try {
            byte[] bytes = parameterName.getBytes(this.tomcatEncoding);
            return new String(bytes, browserEncoding);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
