package org.example.chessta.service;

import org.example.chessta.repository.*;
import org.example.chessta.model.gameModels.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private FigureService figureService;

    @Autowired
    private MoveService moveService;

    public GameService(GameRepository gameRepository, FigureService figureService, MoveService moveService) {
        this.gameRepository = gameRepository;
        this.figureService = figureService;
        this.moveService = moveService;
    }

    public Game createNewGame(String whitePlayerName, String blackPlayerName) {
        Game game = new Game(whitePlayerName, blackPlayerName, figureService);
        return gameRepository.save(game);
    }

    public Game loadGame(int gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Spiel mit ID " + gameId + " nicht gefunden."));
    }

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    public boolean deleteGame(int gameId) {
        deleteFiguresFromGame(gameId);
        moveService.deleteMovesForGame(gameId);
        gameRepository.deleteById(gameId);
        return !gameRepository.existsById(gameId);
    }

    public void deleteFiguresFromGame(int gameId) {
        List<Figure> figures = getFiguresInGame(gameId);
        for (Figure figure : figures) {
            figureService.deleteFigureById(figure.getId());
        }
    }

    public boolean executeMove(int gameId, int fromRow, int fromCol, int toRow, int toCol) {
        // Lade das Spiel
        Game game = loadGame(gameId);

        // Finde die Figur an der Startposition
        Figure movingFigure = figureService.findFigureAtPosition(game, fromRow, fromCol);
        if (movingFigure == null) {
            return false;
        }

        // Validierung des Zugs
        if (!movingFigure.isValidMove(toRow, toCol)) {
            return false;
        }

        // Finde die Figur an der Zielposition
        Figure targetFigure = figureService.findFigureAtPosition(game, toRow, toCol);

        // Entferne geschlagene Figuren
        if (targetFigure != null && targetFigure.isWhite() != movingFigure.isWhite()) {
            figureService.captureFigure(targetFigure);
        }

        // Aktualisiere die Position der Figur
        figureService.moveFigure(movingFigure, toRow, toCol);

        // Speichere den Move
        moveService.createMove(movingFigure, toRow, toCol, targetFigure != null, game);

        // Aktualisiere den Spielzug
        game.setCurrentPlayer(game.getCurrentPlayer() == game.getWhitePlayer() ? game.getBlackPlayer() : game.getWhitePlayer());
        gameRepository.save(game);

        System.out.println("Zug erfolgreich ausgeführt!");
        return true;
    }


    public List<Figure> getFiguresInGame(int gameId) {
        Game game = loadGame(gameId);
        return figureService.getFiguresByGame(game);
    }

}