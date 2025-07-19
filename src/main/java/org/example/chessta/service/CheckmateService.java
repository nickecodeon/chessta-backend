package org.example.chessta.service;

import org.example.chessta.model.Figure;
import org.example.chessta.model.Game;
import org.example.chessta.model.simulation.SimulatedGame;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckmateService {
    private final FigureService figureService;
    private final MoveService moveService;
    private final MoveValidator moveValidator;
    private final CheckService checkService;

    public CheckmateService(FigureService figureService,
                            MoveService moveService,
                            MoveValidator moveValidator,
                            CheckService checkService) {
        this.figureService = figureService;
        this.moveService = moveService;
        this.moveValidator = moveValidator;
        this.checkService = checkService;
    }

    public boolean isCheckmate(Game game, boolean isWhite) {
        SimulatedGame gameCopy = new SimulatedGame(figureService.getFiguresByGame(game));
        if (!checkService.isKingInCheck(gameCopy, isWhite)) {
            return false;
        }

        List<Figure> ownFigures = gameCopy.getFiguresByColor(isWhite);

        for (Figure fig : ownFigures) {
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    if (!moveValidator.isValidMove(gameCopy.getFigures(), fig, row, col)) continue;

                    SimulatedGame simulatedGame = moveService.simulateMove(game, fig, row, col);
                    if (!checkService.isKingInCheck(simulatedGame, isWhite)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }
}
