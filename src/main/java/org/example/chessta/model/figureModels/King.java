package org.example.chessta.model.figureModels;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.example.chessta.model.gameModels.Figure;

@Entity
@DiscriminatorValue("King")
public class King extends Figure {
    public King() {
    }

    public King(int row, int col, boolean isWhite) {
        super(row, col, isWhite);
    }

    @Override
    public boolean isValidMove(int row, int col) {
        int rowDiff = Math.abs(row - getBoard_row());
        int colDiff = Math.abs(col - getBoard_column());

        return rowDiff <= 1 && colDiff <= 1;
    }
}
