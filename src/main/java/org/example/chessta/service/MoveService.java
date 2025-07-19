package org.example.chessta.service;

import org.example.chessta.model.Figure;
import org.example.chessta.model.Game;
import org.example.chessta.model.Move;
import org.example.chessta.model.simulation.SimulatedGame;
import org.example.chessta.repository.MoveRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.ArrayList;

@Service
public class MoveService {
    private final MoveRepository moveRepository;
    private final FigureService figureService;

    public MoveService(MoveRepository moveRepository, FigureService figureService) {
        this.moveRepository = moveRepository;
        this.figureService = figureService;
    }

    public void createMove(Figure figure, int toRow, int toCol, boolean isCapture, Game game, int inGameCount) {
        Move move = new Move(figure.getBoard_row(), figure.getBoard_column(),
                toRow, toCol, isCapture, figure.getClass().getSimpleName(),
                figure, game, inGameCount
        );
        moveRepository.save(move);
    }

    public Move getMoveById(UUID moveId) {
        return moveRepository.findById(moveId)
                .orElseThrow(() -> new RuntimeException("Move with ID: " + moveId + " not found."));
    }

    public void deleteMovesForGame(UUID gameId) {
        List<Move> moves = moveRepository.findByGameId(gameId);
        moveRepository.deleteAll(moves);
    }

    public SimulatedGame simulateMove(Game game, Figure originalFigure, int toRow, int toCol) {
        List<Figure> originalFigures = figureService.getFiguresByGame(game);
        List<Figure> copiedFigures = originalFigures.stream()
                .map(figureService::copyFigure)
                .collect(Collectors.toCollection(ArrayList::new));

        SimulatedGame simulatedGame = new SimulatedGame(copiedFigures);

        Figure moving = simulatedGame.getFigureById(originalFigure.getId());
        Figure target = simulatedGame.getFigureAt(toRow, toCol);

        if (target != null) {
            simulatedGame.captureFigure(target);
        }

        simulatedGame.moveFigure(moving, toRow, toCol);

        return simulatedGame;
    }
}
