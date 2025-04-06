package org.example.chessta.dto;

import org.example.chessta.model.gameModels.Figure;

import java.util.ArrayList;
import java.util.List;

public class FigureDTO {
    private int id;
    private String type;
    private int row;
    private int col;
    private boolean isWhite;
    private boolean isCaptured;

    public FigureDTO(int id, String type, int row, int col, boolean isWhite, boolean isCaptured) {
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
    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public boolean isCaptured() {
        return isCaptured;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setWhite(boolean white) {
        this.isWhite = white;
    }

    public void setCaptured(boolean captured) {
        this.isCaptured = captured;
    }
}
