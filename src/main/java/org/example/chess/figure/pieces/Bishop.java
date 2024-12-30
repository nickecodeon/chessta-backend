package org.example.chess.figure.pieces;

import org.example.chess.figure.Figure;

public class Bishop extends Figure {
    public Bishop(int x, int y, boolean isWhite) {
        super(x, y, isWhite);
    }

    @Override
    public boolean isValidMove(int x, int y) {
        int dx = Math.abs(x - getColumn());
        int dy = Math.abs(y - getRow());

        return dx == dy;
    }
}
