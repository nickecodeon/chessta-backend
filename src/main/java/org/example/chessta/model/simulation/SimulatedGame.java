package org.example.chessta.model.simulation;

import org.example.chessta.model.Figure;
import org.example.chessta.model.FigureType;

import java.util.List;
import java.util.UUID;

public record SimulatedGame(List<Figure> figures) {

    public Figure getFigureAt(int row, int col) {
        return figures.stream()
                .filter(f -> f.getBoard_row() == row && f.getBoard_column() == col)
                .findFirst()
                .orElse(null);
    }

    public Figure getFigureById(UUID id) {
        return figures.stream()
                .filter(f -> f.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Figure> getFigures() {
        return figures;
    }

    public List<Figure> getFiguresByColor(boolean isWhite) {
        return figures.stream()
                .filter(f -> f.isWhite() == isWhite)
                .toList();
    }

    public void moveFigure(Figure figure, int toRow, int toCol) {
        figure.setBoard_row(toRow);
        figure.setBoard_column(toCol);
    }

    public void captureFigure(Figure target) {
        figures.removeIf(f -> f.getId().equals(target.getId()));
    }

    public Figure getKing(boolean isWhite) {
        return figures.stream()
                .filter(f -> f.getType() == FigureType.KING && f.isWhite() == isWhite)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("King not found"));
    }
}