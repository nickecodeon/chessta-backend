package org.example.chess.database;

import org.example.chess.game.*;
import org.example.chess.figure.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;

public class GameRepository {

    public static void saveGame(Game game) {
        String sql = "UPDATE Game SET currentPlayerId = ? WHERE id = ?";
        try (Connection conn = Database.connect()) {
            // Spieler zuerst speichern, um ihre IDs zu setzen
            savePlayers(conn, game);

            // Neues Spiel oder bestehendes Spiel aktualisieren
            if (game.getId() == 0) {
                insertGame(conn, game); // Neues Spiel
            } else {
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setInt(1, game.getCurrentPlayer().getId());
                    pstmt.setInt(2, game.getId());
                    pstmt.executeUpdate();
                }
            }

            saveFigures(conn, game);

            System.out.println("Spiel wurde erfolgreich gespeichert.");
        } catch (SQLException e) {
            System.err.println("Fehler beim Speichern des Spiels: " + e.getMessage());
        }
    }

    private static void insertGame(Connection conn, Game game) throws SQLException {
        String sql = "INSERT INTO Game (whitePlayerId, blackPlayerId, currentPlayerId) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, game.getWhitePlayer().getId());
            pstmt.setInt(2, game.getBlackPlayer().getId());
            pstmt.setInt(3, game.getCurrentPlayer().getId());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                game.setId(generatedId);
            }
        }
    }

    private static void savePlayers(Connection conn, Game game) throws SQLException {
        savePlayer(conn, game.getWhitePlayer(), game.getId());
        savePlayer(conn, game.getBlackPlayer(), game.getId());
    }

    private static void savePlayer(Connection conn, Player player, int gameId) throws SQLException {
        String sql = """
            UPDATE Player
            SET name = ?, isWhite = ?, gameId = ?
            WHERE id = ?
            """;

        if (player.getId() == 0) {
            // Spieler einfügen und ID setzen
            insertPlayer(conn, player, gameId);
        } else {
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                // Spieler updaten
                pstmt.setString(1, player.getName());
                pstmt.setBoolean(2, player.isWhite());
                pstmt.setInt(3, gameId);
                pstmt.setInt(4, player.getId());
                pstmt.executeUpdate();
            }
        }
    }

    private static void insertPlayer(Connection conn, Player player, int gameId) throws SQLException {
        String sql = """
            INSERT INTO Player (name, isWhite, gameId)
            VALUES (?, ?, ?)
            """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, player.getName());
            pstmt.setBoolean(2, player.isWhite());
            pstmt.setInt(3, gameId);
            pstmt.executeUpdate();

            // Generierte ID abrufen und dem Spieler zuweisen
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    player.setId(generatedId);
                }
            }
        }
    }

    private static void saveFigures(Connection conn, Game game) throws SQLException {
        for (Figure[] row : game.getBoard()) {
            for (Figure figure : row) {
                saveFigure(conn, figure, game.getId());
            }
        }
    }

    public static void saveFigure(Connection conn, Figure figure, int gameId) throws SQLException {
        String sql = """
            INSERT INTO Figure (id, type, isWhite, row, column, isCaptured, gameId)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            ON CONFLICT(id) DO UPDATE SET
                row = excluded.row,
                column = excluded.column,
                isCaptured = excluded.isCaptured
            """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            if (figure != null) {
                if (figure.getId() == 0) {
                    // Falls keine ID, zuerst speichern und ID setzen
                    insertFigure(conn, figure, gameId);
                } else {
                    // Falls ID vorhanden, aktualisiere die Figur
                    pstmt.setInt(1, figure.getId());
                    pstmt.setString(2, figure.getClass().getSimpleName());
                    pstmt.setBoolean(3, figure.isWhite());
                    pstmt.setInt(4, figure.getRow());
                    pstmt.setInt(5, figure.getColumn());
                    pstmt.setBoolean(6, figure.isCaptured());
                    pstmt.setInt(7, gameId);
                    pstmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            System.err.println("Fehler beim Speichern der Figur: " + e.getMessage());
        }
    }

    private static void insertFigure(Connection conn, Figure figure, int gameId) throws SQLException {
        String sql = """
            INSERT INTO Figure (type, isWhite, row, column, isCaptured, gameId)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, figure.getClass().getSimpleName());
            pstmt.setBoolean(2, figure.isWhite());
            pstmt.setInt(3, figure.getRow());
            pstmt.setInt(4, figure.getColumn());
            pstmt.setBoolean(5, figure.isCaptured());
            pstmt.setInt(6, gameId);
            pstmt.executeUpdate();

            // Generierte ID abrufen und der Figur zuweisen
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    figure.setId(generatedId);
                }
            }
        } catch (SQLException e) {
            System.err.println("Fehler beim Speichern der Figur: " + e.getMessage());
        }
    }

    public static Game loadGame(int gameId) {
        try (Connection conn = Database.connect()) {
            Game game = loadGameInfo(conn, gameId);
            if (game != null) {
                game.setWhitePlayer(loadPlayer(conn, game.getWhitePlayerId()));
                game.setBlackPlayer(loadPlayer(conn, game.getBlackPlayerId()));
                game.setBoard(loadBoard(conn, gameId));
            }
            return game;
        } catch (SQLException e) {
            System.err.println("Fehler beim Laden des Spiels: " + e.getMessage());
            return null;
        }
    }

    private static Game loadGameInfo(Connection conn, int gameId) throws SQLException {
        String sql = "SELECT * FROM Game WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, gameId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Game(
                        rs.getInt("id"),
                        loadPlayer(conn, rs.getInt("whitePlayerId")),
                        loadPlayer(conn, rs.getInt("blackPlayerId")),
                        rs.getInt("currentPlayerId") == rs.getInt("whitePlayerId")
                                ? loadPlayer(conn, rs.getInt("whitePlayerId"))
                                : loadPlayer(conn, rs.getInt("blackPlayerId")),
                        new Figure[8][8]
                );
            }
        }
        return null;
    }

    private static Player loadPlayer(Connection conn, int playerId) throws SQLException {
        String sql = "SELECT * FROM Player WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, playerId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Player(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getBoolean("isWhite"),
                        new ArrayList<>()
                );
            }
        }
        return null;
    }

    private static Figure[][] loadBoard(Connection conn, int gameId) throws SQLException {
        Figure[][] board = new Figure[8][8];
        String sql = "SELECT * FROM Figure WHERE gameId = ? AND isCaptured = false";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, gameId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String type = rs.getString("type");
                int id = rs.getInt("id");
                int row = rs.getInt("row");
                int column = rs.getInt("column");
                boolean isWhite = rs.getBoolean("isWhite");
                boolean isCaptured = rs.getBoolean("isCaptured");

                Figure figure = createFigure(type, id, row, column, isWhite, isCaptured);
                board[row - 1][column - 1] = figure;
            }
        }
        return board;
    }

    private static Figure createFigure(String type, int id, int row, int column, boolean isWhite, boolean isCaptured) {
        switch (type) {
            case "Pawn":
                return new Pawn(id, row, column, isWhite, isCaptured);
            case "Rook":
                return new Rook(id, row, column, isWhite, isCaptured);
            case "Knight":
                return new Knight(id, row, column, isWhite, isCaptured);
            case "Bishop":
                return new Bishop(id, row, column, isWhite, isCaptured);
            case "Queen":
                return new Queen(id, row, column, isWhite, isCaptured);
            case "King":
                return new King(id, row, column, isWhite, isCaptured);
            default:
                throw new IllegalArgumentException("Unbekannter Figurtyp: " + type);
        }
    }
}