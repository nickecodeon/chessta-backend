package org.example.chess.figure;

import org.example.chess.game.Figure;

public class Rook extends Figure {

    // Konstruktor für neue Figuren
    public Rook(int row, int column, boolean isWhite) {
        super(row, column, isWhite);
    }

    // Konstruktor für geladene Figuren
    public Rook(int id, int row, int column, boolean isWhite, boolean isCaptured) {
        super(id, row, column, isWhite, isCaptured);
    }

    @Override
    public boolean isValidMove(int row, int column) {
        int rowDiff = Math.abs(row - getRow());
        int colDiff = Math.abs(column - getColumn());

        // Ein Turm kann sich nur horizontal oder vertikal bewegen
        return rowDiff == 0 || colDiff == 0;
    }
}