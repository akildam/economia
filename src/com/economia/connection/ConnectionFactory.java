package com.economia.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Renato
 */
public class ConnectionFactory {
    public Connection getConnection() {
        try {
            Class.forName("com.ibm.db2.jcc.DB2Jcc");
            return DriverManager.getConnection("jdbc:db2://localhost:50000/ECONOMIA", "db2admin", "db2admin");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }
}
