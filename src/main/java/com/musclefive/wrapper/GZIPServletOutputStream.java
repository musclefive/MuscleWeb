package com.musclefive.wrapper;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

/**
 * Created by shixin on 14-3-9.
 */
public class GZIPServletOutputStream extends ServletOutputStream{
    private GZIPOutputStream gzipOutputStream;

    public GZIPServletOutputStream(ServletOutputStream servletOutputStream) throws IOException {
        this.gzipOutputStream = new GZIPOutputStream(servletOutputStream);
    }

    @Override
    public void write(int b) throws IOException {
        this.gzipOutputStream.write(b);
    }

    public GZIPOutputStream getGzipOutputStream(){
        return this.gzipOutputStream;
    }
}
