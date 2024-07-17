package org.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHelper {

    public static final String DB_NAME = "CoffeeShop";
    public static final String USERNAME = "sa";
    public static final String PASSWORD = "123123qwe";

    public static Connection getConnetion() throws SQLException, ClassNotFoundException {
        return getConnection(DB_NAME, USERNAME, PASSWORD);
    }
    public static Connection getConnection(String dbName, String userName, String passowrd) throws SQLException, ClassNotFoundException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String dbURL = "jdbc:sqlserver://localhost:1433;database=%s;username=%s;password=%s;encrypt=true;trustServerCertificate=true;";
        Connection connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;database=CoffeeShop;username=sa;password=123123qwe;encrypt=true;trustServerCertificate=true;");
        return connection;
    }
}
