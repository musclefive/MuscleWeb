package com.musclefive.db;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;

import javax.sql.DataSource;

/**
 * Created by shixin on 3/10/14.
 */
public class AbstractDBAccessor {
    private static final String MYSQL_DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
    private static DataSource dataSource = null;

    public static void initConnection(String url, String user, String password, int numConnections) {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(MYSQL_DRIVER_CLASS_NAME);
        ds.setUrl(url);
        ds.setUsername(user);
        ds.setPassword(password);
        ds.setMaxActive(numConnections);
        ds.setValidationQuery("/* ping */ select 1");
        ds.setTestOnBorrow(true);
        dataSource = ds;
    }

    protected QueryRunner getQueryRunner() {
        if (null == dataSource) {
            throw new RuntimeException("Can't get connection");
        }
        return new QueryRunner(dataSource);
    }
}
