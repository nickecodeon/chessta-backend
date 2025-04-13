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
    public boolean isValidMove(int row, int column) {
        int colDiff = Math.abs(column - getBoard_column());
        int rowDiff = row - getBoard_row();

        if (isWhite()) {
            // Weißer Bauer kann nur vorwärts ziehen
            if (colDiff == 0 && rowDiff == 1) {
                return true; // Ein Feld vorwärts
            }

            if (colDiff == 0 && rowDiff == 2 && getBoard_row() == 2) {
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

            if (colDiff == 0 && rowDiff == -2 && getBoard_row() == 7) {
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