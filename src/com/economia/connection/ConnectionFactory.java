package com.economia.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Connection factory.
 */
public class ConnectionFactory {
    public Connection getConnection() {
        try {
            Properties properties = new Properties();
            properties.setProperty("user", "db2admin");
            properties.setProperty("password", "db2admin");
            properties.setProperty("currentSchema", "ECONOMIA");
            return DriverManager.getConnection("jdbc:db2://localhost:50000/ECONOMIA", properties);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
