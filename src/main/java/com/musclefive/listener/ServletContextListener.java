package com.musclefive.listener;

import com.musclefive.servlet.Shixin;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

/**
 * Created by shixin on 14-3-8.
 */
public class ServletContextListener implements javax.servlet.ServletContextListener {
    private ServletContext servletContext;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        servletContext = servletContextEvent.getServletContext();
        servletContext.log("Enter ServletContextListener.contextInitialized()");

        String dbHost = servletContext.getInitParameter("mysql_host");
        String port = servletContext.getInitParameter("mysql_port");
        String db = servletContext.getInitParameter("mysql_db");
        String user = servletContext.getInitParameter("mysql_user");
        String password = servletContext.getInitParameter("mysql_password");
        //TODO: db initialization.

        //Set context scope attribute.
        Shixin root = new Shixin();
        root.setName("root");
        servletContext.setAttribute("root", root);

        //set session configuration.
        //SessionCookieConfig sessionCookieConfig = servletContext.getSessionCookieConfig();
        //sessionCookieConfig.setName("muscle-five-session-id");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        //TODO: db destroy.
        servletContext.log("Enter ServletContextListener.contextDestroyed()");
    }
}