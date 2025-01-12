package org.example.chess.figure;

import org.example.chess.game.Figure;

public class Queen extends Figure {

    // Konstruktor für neue Figuren
    public Queen(int row, int column, boolean isWhite) {
        super(row, column, isWhite);
    }

    // Konstruktor für geladene Figuren
    public Queen(int id, int row, int column, boolean isWhite, boolean isCaptured) {
        super(id, row, column, isWhite, isCaptured);
    }

    @Override
    public boolean isValidMove(int row, int column) {
        int rowDiff = Math.abs(row - getRow());
        int colDiff = Math.abs(column - getColumn());

        // Eine Dame kann sich diagonal, horizontal oder vertikal bewegen
        return rowDiff == colDiff || rowDiff == 0 || colDiff == 0;
    }
}