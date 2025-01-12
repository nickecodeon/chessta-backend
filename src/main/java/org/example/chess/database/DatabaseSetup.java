package org.example.chess.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSetup {
    public static void initialize() {
        String gameTable = """
                CREATE TABLE IF NOT EXISTS Game (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    whitePlayerId INTEGER,
                    blackPlayerId INTEGER,
                    currentPlayerId INTEGER,
                    FOREIGN KEY (whitePlayerId) REFERENCES Player(id),
                    FOREIGN KEY (blackPlayerId) REFERENCES Player(id),
                    FOREIGN KEY (currentPlayerId) REFERENCES Player(id)
                );
                """;

        String playerTable = """
                CREATE TABLE IF NOT EXISTS Player (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    isWhite BOOLEAN NOT NULL,
                    gameId INTEGER NOT NULL,
                    FOREIGN KEY (gameId) REFERENCES Game(Id) ON DELETE CASCADE
                );
                """;

        String figureTable = """
                CREATE TABLE IF NOT EXISTS Figure (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    type TEXT NOT NULL,
                    isWhite BOOLEAN NOT NULL,
                    column INTEGER NOT NULL,
                    row INTEGER NOT NULL,
                    isCaptured BOOLEAN NOT NULL,
                    gameId INTEGER NOT NULL,
                    FOREIGN KEY (gameId) REFERENCES Game(id) ON DELETE CASCADE
                );
                """;

        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(gameTable);
            stmt.execute(playerTable);
            stmt.execute(figureTable);
            System.out.println("Tabellen erfolgreich erstellt oder bereits vorhanden.");
        } catch (SQLException e) {
            System.err.println("Fehler beim Erstellen der Tabellen" + e.getMessage());
        }
    }
}
