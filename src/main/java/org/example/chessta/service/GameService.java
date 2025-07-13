package org.example.chessta.service;

import org.example.chessta.dto.FigureDTO;
import org.example.chessta.dto.MoveDTO;
import org.example.chessta.model.Figure;
import org.example.chessta.model.Game;
import org.example.chessta.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GameService {

    private final GameRepository gameRepository;

    private final FigureService figureService;

    private final MoveService moveService;

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
        MoveValidator moveValidator = new MoveValidator(figureService);

        Figure movingFigure = figureService.getFigureAtPosition(game, moveDTO.getFromRow(), moveDTO.getFromCol());
        if (movingFigure == null || movingFigure.isWhite() != game.getCurrentPlayer().isWhite()) {
            return false;
        }

        if (!moveValidator.isValidMove(movingFigure, moveDTO.getToRow(), moveDTO.getToCol(), game)) {
            return false;
        }

        Figure targetFigure = figureService.getFigureAtPosition(game, moveDTO.getToRow(), moveDTO.getToCol());
        boolean isCapture = targetFigure != null;

        if (isCapture) {
            figureService.captureFigure(targetFigure);
        }

        figureService.moveFigure(movingFigure, moveDTO.getToRow(), moveDTO.getToCol());

        int inGameCount = game.getMoveCount();
        moveService.createMove(movingFigure, moveDTO.getToRow(), moveDTO.getToCol(), isCapture, game, inGameCount);

        game.setMoveCount(inGameCount + 1);

        game.setCurrentPlayer(
                game.getCurrentPlayer() == game.getWhitePlayer()
                        ? game.getBlackPlayer()
                        : game.getWhitePlayer()
        );
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