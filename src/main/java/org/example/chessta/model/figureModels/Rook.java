package org.example.chessta.model.figureModels;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.example.chessta.model.gameModels.Figure;

@Entity
@DiscriminatorValue("Rook")
public class Rook extends Figure {
    public Rook() {
    }

    // Konstruktor für neue Figuren
    public Rook(int row, int column, boolean isWhite) {
        super(row, column, isWhite);
    }

    @Override
    public boolean isValidMove(int row, int column) {
        int rowDiff = Math.abs(row - getBoard_row());
        int colDiff = Math.abs(column - getBoard_column());

        // Ein Turm kann sich nur horizontal oder vertikal bewegen
        return rowDiff == 0 || colDiff == 0;
    }
}