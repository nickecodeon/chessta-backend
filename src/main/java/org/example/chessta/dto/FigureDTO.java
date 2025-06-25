package org.example.chessta.dto;

import org.example.chessta.model.gameModels.Figure;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FigureDTO {
    private UUID id;
    private String type;
    private int row;
    private int col;
    private boolean isWhite;
    private boolean isCaptured;

    public FigureDTO(UUID id, String type, int row, int col, boolean isWhite, boolean isCaptured) {
        this.id = id;
        this.type = type;
        this.row = row;
        this.col = col;
        this.isWhite = isWhite;
        this.isCaptured = isCaptured;
    }

    public static FigureDTO fromEntity(Figure figure) {
        return new FigureDTO(
                figure.getId(),
                figure.getClass().getSimpleName(),
                figure.getBoard_row(),
                figure.getBoard_column(),
                figure.isWhite(),
                figure.isCaptured()
        );
    }

    public static List<FigureDTO> fromEntity(List<Figure> figures) {
        List<FigureDTO> figureDTOs = new ArrayList<>();
        for (Figure figure : figures) {
            figureDTOs.add(fromEntity(figure));
        }
        return figureDTOs;
    }

    // --- Getter & Setter ---

    @SuppressWarnings("unused")
    public UUID getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public String getType() {
        return type;
    }

    @SuppressWarnings("unused")
    public int getRow() {
        return row;
    }

    @SuppressWarnings("unused")
    public int getCol() {
        return col;
    }

    @SuppressWarnings("unused")
    public boolean getIsWhite() {
        return isWhite;
    }

    @SuppressWarnings("unused")
    public boolean getIsCaptured() {
        return isCaptured;
    }

    @SuppressWarnings("unused")
    public void setId(UUID id) {
        this.id = id;
    }

    @SuppressWarnings("unused")
    public void setType(String type) {
        this.type = type;
    }

    @SuppressWarnings("unused")
    public void setRow(int row) {
        this.row = row;
    }

    @SuppressWarnings("unused")
    public void setCol(int col) {
        this.col = col;
    }

    @SuppressWarnings("unused")
    public void setIsWhite(boolean white) {
        this.isWhite = white;
    }

    @SuppressWarnings("unused")
    public void setIsCaptured(boolean captured) {
        this.isCaptured = captured;
    }
}
