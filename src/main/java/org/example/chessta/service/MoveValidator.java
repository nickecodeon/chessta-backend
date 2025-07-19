package org.example.chessta.service;

import org.example.chessta.model.Figure;
import org.example.chessta.model.FigureType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoveValidator {
    public MoveValidator() {}

    public boolean isValidMove(List<Figure> gameFigures, Figure figure, int toRow, int toCol) {

        Figure target = getFigureAtPosition(gameFigures, toRow, toCol);
        boolean isCapture = target != null;

        if (isCapture && target.isWhite() == figure.isWhite()) {
            return false;
        }

        boolean basicValid = switch (figure.getType()) {
            case KING -> validateKingMove(figure, toRow, toCol);
            case QUEEN -> validateQueenMove(figure, toRow, toCol);
            case ROOK -> validateRookMove(figure, toRow, toCol);
            case BISHOP -> validateBishopMove(figure, toRow, toCol);
            case KNIGHT -> validateKnightMove(figure, toRow, toCol);
            case PAWN -> validatePawnMove(figure, toRow, toCol, isCapture);
        };

        if (!basicValid) return false;

        return !requiresClearPath(figure.getType()) ||
                isPathClear(gameFigures, figure.getBoard_row(), figure.getBoard_column(), toRow, toCol);
    }

    public Figure getFigureAtPosition(List<Figure> gameFigures, int row, int col) {
        for (Figure figure : gameFigures) {
            if (figure != null && figure.getBoard_row() == row && figure.getBoard_column() == col) {
                return figure;
            }
        }

        return null;
    }

    private boolean requiresClearPath(FigureType type) {
        return type == FigureType.ROOK || type == FigureType.BISHOP || type == FigureType.QUEEN;
    }

    public boolean isPathClear(List<Figure> gameFigures, int fromRow, int fromCol, int toRow, int toCol) {
        int rowStep = Integer.compare(toRow, fromRow);
        int colStep = Integer.compare(toCol, fromCol);

        int currentRow = fromRow + rowStep;
        int currentCol = fromCol + colStep;

        while (currentRow != toRow || currentCol != toCol) {
            if (getFigureAtPosition(gameFigures, currentRow, currentCol) != null) {
                return false;
            }
            currentRow += rowStep;
            currentCol += colStep;
        }

        return true;
    }

    private boolean validateKingMove(Figure king, int toRow, int toCol) {
        int rowDiff = Math.abs(toRow - king.getBoard_row());
        int colDiff = Math.abs(toCol - king.getBoard_column());

        return rowDiff <= 1 && colDiff <= 1;
    }

    private boolean validateQueenMove(Figure queen, int toRow, int toCol) {
        int rowDiff = Math.abs(toRow - queen.getBoard_row());
        int colDiff = Math.abs(toCol - queen.getBoard_column());

        return rowDiff == colDiff || rowDiff == 0 || colDiff == 0;
    }

    private boolean validateRookMove(Figure rook, int toRow, int toCol) {
        int rowDiff = Math.abs(toRow - rook.getBoard_row());
        int colDiff = Math.abs(toCol - rook.getBoard_column());

        return rowDiff == 0 || colDiff == 0;
    }

    private boolean validateBishopMove(Figure bishop, int toRow, int toCol) {
        int rowDiff = Math.abs(toRow - bishop.getBoard_row());
        int colDiff = Math.abs(toCol - bishop.getBoard_column());

        return rowDiff == colDiff;
    }

    private boolean validateKnightMove(Figure knight, int toRow, int toCol) {
        int rowDiff = Math.abs(toRow - knight.getBoard_row());
        int colDiff = Math.abs(toCol - knight.getBoard_column());

        return (rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2);
    }

    private boolean validatePawnMove(Figure pawn, int toRow, int toCol, boolean isCapture) {
        int rowDiff = toRow - pawn.getBoard_row();
        int colDiff = Math.abs(toCol - pawn.getBoard_column());

        if (pawn.isWhite()) {
            if (isCapture) {
                return rowDiff == 1 && colDiff == 1;
            } else {
                if (colDiff != 0) return false;
                if (rowDiff == 1) return true;
                return rowDiff == 2 && pawn.getBoard_row() == 1;
            }
        } else {
            if (isCapture) {
                return rowDiff == -1 && colDiff == 1;
            } else {
                if (colDiff != 0) return false;
                if (rowDiff == -1) return true;
                return rowDiff == -2 && pawn.getBoard_row() == 6;
            }
        }
    }
}
