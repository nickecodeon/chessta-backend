package org.example.chess.figure;

import org.example.chess.game.Figure;

public class Knight extends Figure {
    public Knight(int x, int y, boolean isWhite) {
        super(x, y, isWhite);
    }

    public Knight(int id, int x, int y, boolean isWhite, boolean isCaptured) {
        super(id, x, y, isWhite, isCaptured);
    }

    @Override
    public boolean isValidMove(int x, int y) {
        int dx = Math.abs(x - getColumn());
        int dy = Math.abs(y - getRow());

        return (dx == 2 && dy == 1) || (dx == 1 && dy == 2);
    }
}
