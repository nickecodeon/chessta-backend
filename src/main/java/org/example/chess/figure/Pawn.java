package org.example.chess.figure;

import org.example.chess.game.Figure;

public class Pawn extends Figure {

    // Konstruktor für neue Bauern
    public Pawn(int row, int column, boolean isWhite) {
        super(row, column, isWhite);
    }

    // Konstruktor für geladene Bauern
    public Pawn(int id, int row, int column, boolean isWhite, boolean isCaptured) {
        super(id, row, column, isWhite, isCaptured);
    }

    @Override
    public boolean isValidMove(int row, int column) {
        int colDiff = Math.abs(column - getColumn());
        int rowDiff = row - getRow();

        if (isWhite()) {
            // Weißer Bauer kann nur vorwärts ziehen
            if (colDiff == 0 && rowDiff == 1) {
                return true; // Ein Feld vorwärts
            }

            if (colDiff == 0 && rowDiff == 2 && getRow() == 2) {
                return true; // Zwei Felder vorwärts aus der Grundstellung
            }
            /*
            if (colDiff == 1 && rowDiff == 1) {
                return true; // Diagonaler Zug zum Schlagen
            }
             */
        } else {
            // Schwarzer Bauer kann nur rückwärts ziehen
            if (colDiff == 0 && rowDiff == -1) {
                return true; // Ein Feld rückwärts
            }

            if (colDiff == 0 && rowDiff == -2 && getRow() == 7) {
                return true; // Zwei Felder rückwärts aus der Grundstellung
            }

            /*
            if (colDiff == 1 && rowDiff == -1) {
                return true; // Diagonaler Zug zum Schlagen
            }
             */
        }
        return false;
    }
}