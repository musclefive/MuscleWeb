package com.musclefive.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by shixin on 14-3-8.
 */
public class IncludedServlet extends HttpServlet{
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processRequest(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processRequest(request, response);
    }

    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().println("Entering IncludedServlet");

        if(request.getAttribute("shixin") != null){
            Shixin me = (Shixin)request.getAttribute("shixin");
            response.getWriter().println("Find Shixin' name:" + me.getName());
        }
        response.getWriter().println("Exiting IncludedServlet");
    }
}
