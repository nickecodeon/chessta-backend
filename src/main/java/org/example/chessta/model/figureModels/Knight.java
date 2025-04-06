package org.example.chessta.model.figureModels;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.example.chessta.model.gameModels.Figure;

@Entity
@DiscriminatorValue("Knight")
public class Knight extends Figure {
    public Knight() {
    }

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
        int rowDiff = Math.abs(row - getBoard_row());
        int colDiff = Math.abs(column - getBoard_column());

        return (rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2);
    }
}