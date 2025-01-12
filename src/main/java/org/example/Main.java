package org.example;

import org.example.chess.database.*;
import org.example.chess.game.*;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Initialisiere die Datenbank
        DatabaseSetup.initialize();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Willkommen bei Schach!");
        System.out.println("1. Neues Spiel starten");
        System.out.println("2. Gespeichertes Spiel laden");
        int choice = scanner.nextInt();

        Game game;
        if (choice == 1) {
            game = startNewGame(scanner);
        } else {
            game = loadSavedGame(scanner);
        }

        if (game != null) {
            playGame(game, scanner);
        } else {
            System.out.println("Kein Spiel gefunden oder erstellt.");
        }
    }

    private static Game startNewGame(Scanner scanner) {
        System.out.println("Name des weißen Spielers:");
        String whitePlayerName = scanner.next();

        System.out.println("Name des schwarzen Spielers:");
        String blackPlayerName = scanner.next();

        Game game = new Game(whitePlayerName, blackPlayerName);

        // Speichere das neue Spiel in der Datenbank
        GameRepository.saveGame(game);
        System.out.println("Neues Spiel erstellt. Spiel-ID: " + game.getId());
        return game;
    }

    private static Game loadSavedGame(Scanner scanner) {
        System.out.println("Bitte geben Sie die ID des Spiels ein, das Sie laden möchten:");
        int gameId = scanner.nextInt();

        Game game = GameRepository.loadGame(gameId);
        if (game == null) {
            System.out.println("Spiel nicht gefunden.");
        } else {
            System.out.println("Spiel erfolgreich geladen. Spiel-ID: " + gameId);
        }
        return game;
    }

    private static void playGame(Game game, Scanner scanner) {
        while (true) {
            System.out.println("Aktueller Spieler: " + game.getCurrentPlayer().getName());
            System.out.println("Bitte geben Sie Ihren Zug ein (Format: vonReihe vonSpalte zuReihe zuSpalte):");

            int fromRow = scanner.nextInt();
            int fromCol = scanner.nextInt();
            int toRow = scanner.nextInt();
            int toCol = scanner.nextInt();

            boolean moveSuccessful = game.moveFigure(fromRow, fromCol, toRow, toCol);
            if (moveSuccessful) {
                // Speichere das Spiel nach jedem erfolgreichen Zug
                GameRepository.saveGame(game);
                System.out.println("Zug erfolgreich gespeichert.");
            } else {
                System.out.println("Ungültiger Zug. Versuchen Sie es erneut.");
            }

            // Optional: Beende das Spiel
            System.out.println("Möchten Sie das Spiel beenden? (j/n)");
            String quit = scanner.next();
            if (quit.equalsIgnoreCase("j")) {
                System.out.println("Spiel gespeichert. Auf Wiedersehen!");
                break;
            }
        }
    }
}