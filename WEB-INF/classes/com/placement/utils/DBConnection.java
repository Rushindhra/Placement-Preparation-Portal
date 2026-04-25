package com.placement.utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database connection utility using singleton pattern.
 * Provides JDBC connections to the MySQL placement_portal database.
 */
public class DBConnection {

    private static final String URL      = "jdbc:mysql://localhost:3306/placement_portal?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USER     = "root";
    private static final String PASSWORD = "root";         // ← change to your MySQL password
    private static final String DRIVER   = "com.mysql.cj.jdbc.Driver";

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError("MySQL JDBC Driver not found: " + e.getMessage());
        }
    }

    /**
     * Returns a fresh Connection. Caller is responsible for closing it.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * Silently closes a connection (null-safe).
     */
    public static void close(Connection con) {
        if (con != null) {
            try { con.close(); } catch (SQLException ignored) {}
        }
    }
}
