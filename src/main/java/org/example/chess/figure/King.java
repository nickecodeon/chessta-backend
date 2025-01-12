package org.example.chess.figure;

import org.example.chess.game.Figure;

public class King extends Figure {
    public King(int row, int col, boolean isWhite) {
        super(row, col, isWhite);
    }

    public King(int id, int row, int col, boolean isWhite, boolean isCaptured) {
        super(id, row, col, isWhite, isCaptured);
    }

    @Override
    public boolean isValidMove(int row, int col) {
        int rowDiff = Math.abs(row - getRow());
        int colDiff = Math.abs(col - getColumn());

        return rowDiff <= 1 && colDiff <= 1;
    }
}
