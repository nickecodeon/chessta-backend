package org.example.chessta.dto;

import org.example.chessta.model.gameModels.Move;

import java.util.ArrayList;
import java.util.List;

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

    public static List<MoveDTO> fromEntity(List<Move> moves) {
        List<MoveDTO> moveDTOs = new ArrayList<>();
        for (Move move : moves) {
            moveDTOs.add(fromEntity(move));
        }
        return moveDTOs;
    }

    // --- Getter & Setter ---

    public int getFromRow() {
        return fromRow;
    }

    public void setFromRow(int fromRow) {
        this.fromRow = fromRow;
    }

    public int getFromCol() {
        return fromCol;
    }

    public void setFromCol(int fromCol) {
        this.fromCol = fromCol;
    }

    public int getToRow() {
        return toRow;
    }

    public void setToRow(int toRow) {
        this.toRow = toRow;
    }

    public int getToCol() {
        return toCol;
    }

    public void setToCol(int toCol) {
        this.toCol = toCol;
    }
}