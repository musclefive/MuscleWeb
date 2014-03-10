package com.musclefive.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by shixin on 14-3-8.
 */
public class ForwardedServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processRequest(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processRequest(request, response);
    }

    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter printWriter = response.getWriter();
        printWriter.println("Entering ForwardedServlet");

        if (request.getAttribute("shixin") != null) {
            Shixin me = (Shixin) request.getAttribute("shixin");
            printWriter.println("Find Shixin' name:" + me.getName());
        }
        printWriter.println("Exiting ForwardedServlet");
        printWriter.close();
    }
}
