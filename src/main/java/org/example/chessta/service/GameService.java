package org.example.chessta.service;

import org.example.chessta.dto.FigureDTO;
import org.example.chessta.dto.MoveDTO;
import org.example.chessta.model.Figure;
import org.example.chessta.model.Game;
import org.example.chessta.model.simulation.SimulatedGame;
import org.example.chessta.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GameService {

    private final GameRepository gameRepository;

    private final FigureService figureService;
    private final MoveService moveService;
    private final MoveValidator moveValidator;
    private final CheckService checkService;
    private final CheckmateService checkmateService;

    public GameService(GameRepository gameRepository, FigureService figureService,
                       MoveService moveService, MoveValidator moveValidator,
                       CheckService checkService, CheckmateService checkmateService)
    {
        this.gameRepository = gameRepository;
        this.figureService = figureService;
        this.moveService = moveService;
        this.moveValidator = moveValidator;
        this.checkService = checkService;
        this.checkmateService = checkmateService;
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

        List<Figure> gameFigures = figureService.getFiguresByGame(game);

        Figure movingFigure = figureService.getFigureAtPosition(game, moveDTO.getFromRow(), moveDTO.getFromCol());
        if (movingFigure == null || movingFigure.isWhite() != game.getCurrentPlayer().isWhite()) {
            System.out.println(movingFigure);
            return false;
        }

        if (!moveValidator.isValidMove(gameFigures, movingFigure, moveDTO.getToRow(), moveDTO.getToCol())) {
            System.out.println("Not valid move.");
            return false;
        }

        SimulatedGame simulatedGame = moveService.simulateMove(game, movingFigure, moveDTO.getToRow(), moveDTO.getToCol());
        boolean selfInCheck = checkService.isKingInCheck(simulatedGame, movingFigure.isWhite());

        if (selfInCheck) {
            System.out.println("selfInCheck");
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

        SimulatedGame newGameState = new SimulatedGame(figureService.getFiguresByGame(game));

        boolean isCheck = checkService.isKingInCheck(newGameState, game.getCurrentPlayer().isWhite());
        boolean isCheckmate = checkmateService.isCheckmate(game, game.getCurrentPlayer().isWhite());

        if (isCheckmate) {
            game.setFinished(true);
        }

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