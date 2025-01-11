package org.example.chess.figure;

import org.example.chess.game.Figure;

public class Queen extends Figure {
    public Queen(int x, int y, boolean isWhite) {
        super(x, y, isWhite);
    }

    public Queen(int id, int x, int y, boolean isWhite, boolean isCaptured) {
        super(id, x, y, isWhite, isCaptured);
    }

    @Override
    public boolean isValidMove(int x, int y) {
        int dx = Math.abs(x - getColumn());
        int dy = Math.abs(y - getRow());

        return dx == dy || dx == 0 || dy == 0;
    }
}
