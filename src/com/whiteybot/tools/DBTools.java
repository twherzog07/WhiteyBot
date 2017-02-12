package com.whiteybot.tools;

import java.sql.Connection;
import java.sql.SQLException;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

/**
 * Created by Travis on 2/10/2017.
 */
public class DBTools {
    public static Connection connectToDatabase(String userName, String password, String serverName) {
        if (userName == null || password == null || serverName == null)
            return null;

        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser(userName);
        dataSource.setPassword(password);
        dataSource.setServerName(serverName);

        try (Connection conn = dataSource.getConnection()) {
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
