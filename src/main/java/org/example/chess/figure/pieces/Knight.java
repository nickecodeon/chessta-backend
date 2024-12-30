package org.example.chess.figure.pieces;

import org.example.chess.figure.Figure;

public class Knight extends Figure {
    public Knight(int x, int y, boolean isWhite) {
        super(x, y, isWhite);
    }

    @Override
    public boolean isValidMove(int x, int y) {
        int dx = Math.abs(x - getColumn());
        int dy = Math.abs(y - getRow());

        return (dx == 2 && dy == 1) || (dx == 1 && dy == 2);
    }
}
