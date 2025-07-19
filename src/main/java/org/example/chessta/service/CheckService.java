package org.example.chessta.service;

import org.example.chessta.model.Figure;
import org.example.chessta.model.simulation.SimulatedGame;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckService {
    private final MoveValidator moveValidator;

    public CheckService(MoveValidator moveValidator) {
        this.moveValidator = moveValidator;
    }

    public boolean isKingInCheck(SimulatedGame game, boolean isWhite) {
        List<Figure> gameFigures = game.getFigures();
        Figure king = game.getKing(isWhite);
        List<Figure> opponentFigures = game.getFiguresByColor(!isWhite);

        for (Figure opponent : opponentFigures) {
            if (moveValidator.isValidMove(gameFigures, opponent, king.getBoard_row(), king.getBoard_column())) {
                return true;
            }
        }

        return false;
    }
}
