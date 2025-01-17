package org.example.chessta.model.figure;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.example.chessta.model.game.Figure;

@Entity
@DiscriminatorValue("Bishop")
public class Bishop extends Figure {
    public Bishop() {
    }

    public Bishop(int row, int col, boolean isWhite) {
        super(row, col, isWhite);
    }

    public Bishop(int id, int row, int col, boolean isWhite, boolean isCaptured) {
        super(id, row, col, isWhite, isCaptured);
    }

    @Override
    public boolean isValidMove(int row, int col) {
        int rowDiff = Math.abs(row - getBoard_row());
        int colDiff = Math.abs(col - getBoard_column());

        return rowDiff == colDiff;
    }
}
