package org.example.chessta.dto;

import org.example.chessta.model.gameModels.Move;

import java.util.ArrayList;
import java.util.List;

public class MoveDTO {
    private int fromRow;
    private int fromCol;
    private int toRow;
    private int toCol;
    private boolean isCapture;
    private String figureType;

    public MoveDTO (int fromRow, int fromCol, int toRow, int toCol, boolean isCapture, String figureType) {
        this.fromRow = fromRow;
        this.fromCol = fromCol;
        this.toRow = toRow;
        this.toCol = toCol;
        this.isCapture = isCapture;
        this.figureType = figureType;
    }

    public static MoveDTO fromEntity(Move move) {
        return new MoveDTO(
                move.getFromRow(),
                move.getFromCol(),
                move.getToRow(),
                move.getToCol(),
                move.isCapture(),
                move.getFigureType()
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

    public boolean isCapture() {
        return isCapture;
    }

    public void setCapture(boolean capture) {
        isCapture = capture;
    }

    public String getFigureType() {
        return figureType;
    }

    public void setFigureType(String figureType) {
        this.figureType = figureType;
    }
}
