package org.example.chessta.service;

import org.example.chessta.dto.FigureDTO;
import org.example.chessta.dto.MoveDTO;
import org.example.chessta.repository.*;
import org.example.chessta.model.gameModels.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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

    public Game loadGame(UUID gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game with ID: " + gameId + " not found."));
    }

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    public boolean deleteGame(UUID gameId) {
        deleteFiguresFromGame(gameId);
        moveService.deleteMovesForGame(gameId);
        gameRepository.deleteById(gameId);
        return !gameRepository.existsById(gameId);
    }

    public void deleteFiguresFromGame(UUID gameId) {
        List<Figure> figures = getFiguresInGame(gameId);
        for (Figure figure : figures) {
            figureService.deleteFigureById(figure.getId());
        }
    }

    public boolean executeMove(UUID gameId, MoveDTO moveDTO) {
        Game game = loadGame(gameId);

        Figure movingFigure = figureService.getFigureAtPosition(game, moveDTO.getFromRow(), moveDTO.getFromCol());
        if (movingFigure == null || movingFigure.isWhite() != game.getCurrentPlayer().isWhite()) {
            return false;
        }

        Figure targetFigure = figureService.getFigureAtPosition(game, moveDTO.getToRow(), moveDTO.getToCol());
        boolean isCapture = targetFigure != null;

        if (isCapture && targetFigure.isWhite() == movingFigure.isWhite()) {
            return false;
        }

        if (!movingFigure.isValidMove(moveDTO.getToRow(), moveDTO.getToCol(), isCapture)) {
            return false;
        }

        // Prüfen ob zwischen Bishop, Queen oder Rook andere Figuren stehen
        if (movingFigure.requiresClearPath() &&
                !figureService.isPathClear(game, moveDTO.getFromRow(), moveDTO.getFromCol(), moveDTO.getToRow(), moveDTO.getToCol())) {
            return false;
        }

        if (isCapture) {
            figureService.captureFigure(targetFigure);
        }

        figureService.moveFigure(movingFigure, moveDTO.getToRow(), moveDTO.getToCol());
        moveService.createMove(movingFigure, moveDTO.getToRow(), moveDTO.getToCol(), targetFigure != null, game);

        game.setCurrentPlayer(game.getCurrentPlayer() == game.getWhitePlayer() ? game.getBlackPlayer() : game.getWhitePlayer());
        gameRepository.save(game);

        System.out.println("Move successfully executed.");
        return true;
    }


    public List<Figure> getFiguresInGame(UUID gameId) {
        Game game = loadGame(gameId);
        return figureService.getFiguresByGame(game);
    }

    public List<FigureDTO> getFigureDTOsInGame(UUID gameId) {
        List<Figure> figures = getFiguresInGame(gameId);
        return FigureDTO.fromEntity(figures);
    }

}