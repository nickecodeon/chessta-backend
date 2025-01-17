package org.example.chessta.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String DATABASE_URL = "jdbc:sqlite:./chessta.db";

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DATABASE_URL);
            System.out.println("Verbindung zur Datenbank hergestellt: chessta.db");
        } catch (SQLException e) {
            System.err.println("Fehler beim Verbinden mit der Datenbank: " + e.getMessage());
        }
        return conn;
    }
}
