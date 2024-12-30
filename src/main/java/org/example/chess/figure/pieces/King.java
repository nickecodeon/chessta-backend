package org.example.chess.figure.pieces;

import org.example.chess.figure.Figure;

public class King extends Figure {
    public King(int x, int y, boolean isWhite) {
        super(x, y, isWhite);
    }

    @Override
    public boolean isValidMove(int x, int y) {
        int dx = Math.abs(x - getColumn());
        int dy = Math.abs(y - getRow());

        return dx <= 1 && dy <= 1;
    }
}
