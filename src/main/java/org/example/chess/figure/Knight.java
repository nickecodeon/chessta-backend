package org.example.chess.figure;

import org.example.chess.game.Figure;

public class Knight extends Figure {

    // Konstruktor für neue Figuren
    public Knight(int row, int column, boolean isWhite) {
        super(row, column, isWhite);
    }

    // Konstruktor für geladene Figuren
    public Knight(int id, int row, int column, boolean isWhite, boolean isCaptured) {
        super(id, row, column, isWhite, isCaptured);
    }

    @Override
    public boolean isValidMove(int row, int column) {
        int rowDiff = Math.abs(row - getRow());
        int colDiff = Math.abs(column - getColumn());

        return (rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2);
    }
}