package org.example.chessta.dto;

import org.example.chessta.model.Move;

public class MoveDTO {
    private int fromRow;
    private int fromCol;
    private int toRow;
    private int toCol;

    public MoveDTO (int fromRow, int fromCol, int toRow, int toCol) {
        this.fromRow = fromRow;
        this.fromCol = fromCol;
        this.toRow = toRow;
        this.toCol = toCol;
    }

    public static MoveDTO fromEntity(Move move) {
        return new MoveDTO(
                move.getFromRow(),
                move.getFromCol(),
                move.getToRow(),
                move.getToCol()
        );
    }

    // --- Getter & Setter ---

    @SuppressWarnings("unused")
    public int getFromRow() {
        return fromRow;
    }

    @SuppressWarnings("unused")
    public void setFromRow(int fromRow) {
        this.fromRow = fromRow;
    }

    @SuppressWarnings("unused")
    public int getFromCol() {
        return fromCol;
    }

    @SuppressWarnings("unused")
    public void setFromCol(int fromCol) {
        this.fromCol = fromCol;
    }

    @SuppressWarnings("unused")
    public int getToRow() {
        return toRow;
    }

    @SuppressWarnings("unused")
    public void setToRow(int toRow) {
        this.toRow = toRow;
    }

    @SuppressWarnings("unused")
    public int getToCol() {
        return toCol;
    }

    @SuppressWarnings("unused")
    public void setToCol(int toCol) {
        this.toCol = toCol;
    }
}