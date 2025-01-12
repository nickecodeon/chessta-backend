package org.example.chess.figure;

import org.example.chess.game.Figure;

public class Bishop extends Figure {
    public Bishop(int row, int col, boolean isWhite) {
        super(row, col, isWhite);
    }

    public Bishop(int id, int row, int col, boolean isWhite, boolean isCaptured) {
        super(id, row, col, isWhite, isCaptured);
    }

    @Override
    public boolean isValidMove(int row, int col) {
        int rowDiff = Math.abs(row - getRow());
        int colDiff = Math.abs(col - getColumn());

        return rowDiff == colDiff;
    }
}
