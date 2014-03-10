package com.musclefive.listener;

import com.musclefive.db.AbstractDBAccessor;
import com.musclefive.servlet.Shixin;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import java.sql.SQLException;

/**
 * Created by shixin on 14-3-8.
 */
public class ServletContextListener implements javax.servlet.ServletContextListener {
    private Logger logger = Logger.getLogger(ServletContextListener.class);

    private ServletContext servletContext;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        logger.info("Enter ServletContextListener.contextInitialized()");

        servletContext = servletContextEvent.getServletContext();

        String dbHost = servletContext.getInitParameter("mysql_host");
        String port = servletContext.getInitParameter("mysql_port");
        String db = servletContext.getInitParameter("mysql_db");
        String url = "jdbc:mysql://" + (dbHost + ":" + port + "/" + db);
        String user = servletContext.getInitParameter("mysql_user");
        String password = servletContext.getInitParameter("mysql_password");
        String dbConnectionNum = servletContext.getInitParameter("mysql_connection_num");
        AbstractDBAccessor.initConnection(url, user, password, Integer.parseInt(dbConnectionNum));

        try {
            logger.info("Table counts : " + new MyDBAccessor().queryTableCounts());
        } catch (SQLException e) {
            logger.error(e);
        }

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
    }

    private static class MyDBAccessor extends AbstractDBAccessor {
        public int queryTableCounts() throws SQLException {
            QueryRunner queryRunner = getQueryRunner();
            return queryRunner.update("show tables");
        }
    }
}