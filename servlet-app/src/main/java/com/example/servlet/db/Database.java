package com.example.servlet.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Database {

    private static final String url = System.getenv().getOrDefault("DB_URL", "jdbc:postgresql://db:5432/postgres");
    private static final String user = System.getenv().getOrDefault("DB_USER", "postgres");
    private static final String password = System.getenv().getOrDefault("DB_PASSWORD", "postgres");

    public static Connection getConnection() throws Exception {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Postgres JDBC driver not found on classpath", e);
        }
        return DriverManager.getConnection(url, user, password);
    }

    public static String getVersion() throws Exception {
        try (Connection c = getConnection();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery("SELECT version()")) {
            if (rs.next()) {
                return rs.getString(1);
            }
            return "unknown";
        }
    }

}
