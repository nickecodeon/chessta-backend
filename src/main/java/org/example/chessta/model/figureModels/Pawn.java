package org.example.chessta.model.figureModels;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.example.chessta.model.gameModels.Figure;

@Entity
@DiscriminatorValue("Pawn")
public class Pawn extends Figure {
    public Pawn() {
    }

    // Konstruktor für neue Bauern
    public Pawn(int row, int column, boolean isWhite) {
        super(row, column, isWhite);
    }

    @Override
    public boolean isValidMove(int row, int column, boolean isCapture) {
        int rowDiff = row - getBoard_row();
        int colDiff = Math.abs(column - getBoard_column());

        if (isWhite()) {
            if (isCapture) {
                return rowDiff == 1 && colDiff == 1;
            } else {
                if (colDiff != 0) return false;
                if (rowDiff == 1) return true;
                return rowDiff == 2 && getBoard_row() == 2;
            }
        } else {
            if (isCapture) {
                return rowDiff == -1 && colDiff == 1;
            } else {
                if (colDiff != 0) return false;
                if (rowDiff == -1) return true;
                return rowDiff == -2 && getBoard_row() == 7;
            }
        }
    }
}