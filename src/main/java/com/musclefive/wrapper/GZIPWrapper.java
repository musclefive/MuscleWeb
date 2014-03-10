package com.musclefive.wrapper;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.zip.GZIPOutputStream;

/**
 * Created by shixin on 14-3-9.
 */
public class GZIPWrapper extends HttpServletResponseWrapper {
    private GZIPServletOutputStream servletOutputStream;
    private PrintWriter printWriter;

    public GZIPWrapper(HttpServletResponse response) {
        super(response);
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (this.printWriter != null) {
            throw new IllegalStateException();
        }
        if (null == this.servletOutputStream) {
            this.servletOutputStream = new GZIPServletOutputStream(getResponse().getOutputStream());
        }
        return this.servletOutputStream;
    }

    public PrintWriter getPrintWriter() throws IOException {
        if (this.servletOutputStream != null) {
            throw new IllegalStateException();
        }
        if (null == this.printWriter) {
            this.servletOutputStream = new GZIPServletOutputStream(getResponse().getOutputStream());
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.servletOutputStream);
            this.printWriter = new PrintWriter(outputStreamWriter);
        }
        return this.printWriter;
    }

    @Override
    public void setContentLength(int length) {
    }

    public GZIPOutputStream getGzipOutputStream() {
        if (this.servletOutputStream == null) {
            return null;
        }
        return this.servletOutputStream.getGzipOutputStream();
    }
}
