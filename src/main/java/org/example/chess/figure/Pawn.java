package org.example.chess.figure;

import org.example.chess.game.Figure;

public class Pawn extends Figure {
    public Pawn(int x, int y, boolean isWhite) {
        super(x, y, isWhite);
    }

    public Pawn(int id, int x, int y, boolean isWhite, boolean isCaptured) {
        super(id, x, y, isWhite, isCaptured);
    }

    @Override
    public boolean isValidMove(int x, int y) {
        int dx = Math.abs(x - getColumn());
        int dy = y - getRow();

        if (isWhite()) {
            if (dx == 0 && dy == 1) {
                return true;
            }

            if (dx == 0 && dy == 2 && getRow() == 2) {
                return true;
            }

            if (dx == 1 && dy == 1) {
                return true; // Hier sollte geprüft werden, ob eine gegnerische Figur diagonal steht
            }
        } else {
            if (dx == 0 && dy == -1) {
                return true;
            }

            if (dx == 0 && dy == -2 && getRow() == 7) {
                return true;
            }
            /*
            if (dx == 1 && dy == -1) {
                return true; // Hier sollte geprüft werden, ob eine gegnerische Figur diagonal steht
            }*/
        }
        return false;
    }
}
