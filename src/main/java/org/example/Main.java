package org.example;

import org.example.chess.database.*;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starte das Schachspiel...");

        // Initialisiere die Datenbank
        DatabaseSetup.initialize();

        // Beispiel: Verbindung testen
        try (var connection = Database.connect()) {
            if (connection != null) {
                System.out.println("Verbindung erfolgreich getestet.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}