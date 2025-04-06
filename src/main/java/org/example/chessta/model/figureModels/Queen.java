package org.example.chessta.model.figureModels;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.example.chessta.model.gameModels.Figure;

@Entity
@DiscriminatorValue("Queen")
public class Queen extends Figure {
    public Queen() {
    }

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
        int rowDiff = Math.abs(row - getBoard_row());
        int colDiff = Math.abs(column - getBoard_column());

        // Eine Dame kann sich diagonal, horizontal oder vertikal bewegen
        return rowDiff == colDiff || rowDiff == 0 || colDiff == 0;
    }
}