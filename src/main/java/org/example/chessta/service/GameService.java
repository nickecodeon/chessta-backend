package org.example.chessta.service;

import org.example.chessta.repository.*;
import org.example.chessta.model.game.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private FigureRepository figureRepository;

    /**
     * Erstellt ein neues Spiel und initialisiert das Spielbrett.
     */
    public Game createNewGame(String whitePlayerName, String blackPlayerName) {
        Game game = new Game(whitePlayerName, blackPlayerName);
        game.initializeBoard(figureRepository);
        return gameRepository.save(game);
    }

    /**
     * Lädt ein Spiel anhand seiner ID.
     */
    public Game loadGame(int gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Spiel mit ID " + gameId + " nicht gefunden."));
    }

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    public boolean deleteGame(int gameId) {
        gameRepository.deleteById(gameId);

        return !gameRepository.existsById(gameId);
    }

    /**
     * Führt einen Zug aus und speichert die Änderungen in der Datenbank.
     */
    public boolean executeMove(int gameId, int fromRow, int fromCol, int toRow, int toCol) {
        // Lade das Spiel
        Game game = loadGame(gameId);

        // Finde die Figur an der Startposition
        Figure movingFigure = findFigureAtPosition(game, fromRow, fromCol);
        if (movingFigure == null) {
            return false;
        }

        // Validierung des Zugs
        if (!movingFigure.isValidMove(toRow, toCol)) {
            return false;
        }

        // Finde die Figur an der Zielposition
        Figure targetFigure = findFigureAtPosition(game, toRow, toCol);

        // Entferne geschlagene Figuren
        if (targetFigure != null && targetFigure.isWhite() != movingFigure.isWhite()) {
            targetFigure.setCaptured(true);
            figureRepository.save(targetFigure);
        }

        // Aktualisiere die Position der Figur
        movingFigure.setBoard_row(toRow);
        movingFigure.setBoard_column(toCol);
        figureRepository.save(movingFigure);

        // Aktualisiere den Spielerzug
        game.setCurrentPlayer(game.getCurrentPlayer() == game.getWhitePlayer() ? game.getBlackPlayer() : game.getWhitePlayer());
        gameRepository.save(game);

        System.out.println("Zug erfolgreich ausgeführt!");
        return true;
    }

    /**
     * Findet eine Figur an einer bestimmten Position im Spiel.
     */
    private Figure findFigureAtPosition(Game game, int row, int col) {
        List<Integer> figureIds = game.getFigureIds();
        return figureIds.stream()
                .map(id -> figureRepository.findById(id)
                        .orElse(null))
                .filter(figure -> figure != null && figure.getBoard_row() == row && figure.getBoard_column() == col)
                .findFirst()
                .orElse(null);
    }

    /**
     * Prüft, ob eine Position auf dem Spielfeld besetzt ist.
     */
    public boolean isPositionOccupied(Game game, int row, int col) {
        return findFigureAtPosition(game, row, col) != null;
    }
}