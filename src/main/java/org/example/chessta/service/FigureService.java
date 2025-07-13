package org.example.chessta.service;

import org.example.chessta.model.Figure;
import org.example.chessta.model.Game;
import org.example.chessta.repository.FigureRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FigureService {

    private final FigureRepository figureRepository;

    public FigureService(FigureRepository figureRepository) {
        this.figureRepository = figureRepository;
    }

    public List<Figure> saveFigures(List<Figure> figures) {
        return figureRepository.saveAll(figures);
    }

    public Figure getFigureById(UUID figureId) {
        return figureRepository.findById(figureId)
                .orElseThrow(() -> new RuntimeException("Figure with ID: " + figureId + " not found."));
    }

    public Figure getFigureAtPosition(Game game, int row, int col) {
        List<UUID> figureIds = game.getFigureIds();

        for (UUID figureId : figureIds) {
            Figure figure = figureRepository.findById(figureId).orElse(null);

            if (figure != null && figure.getBoard_row() == row && figure.getBoard_column() == col) {
                return figure;
            }
        }

        return null;
    }

    public boolean isPathClear(Game game, int fromRow, int fromCol, int toRow, int toCol) {
        int rowStep = Integer.compare(toRow, fromRow);
        int colStep = Integer.compare(toCol, fromCol);

        int currentRow = fromRow + rowStep;
        int currentCol = fromCol + colStep;

        while (currentRow != toRow || currentCol != toCol) {
            if (getFigureAtPosition(game, currentRow, currentCol) != null) {
                return false;
            }
            currentRow += rowStep;
            currentCol += colStep;
        }

        return true;
    }

    public void captureFigure(Figure figure) {
        figure.setCaptured(true);
        figureRepository.save(figure);
    }

    public void moveFigure(Figure figure, int row, int col) {
        figure.setBoard_row(row);
        figure.setBoard_column(col);
        figureRepository.save(figure);
    }

    public List<Figure> getFiguresByGame(Game game) {
        return figureRepository.findAllById(game.getFigureIds());
    }

    public void deleteFigureById(UUID figureId) {
        figureRepository.deleteById(figureId);
    }
}
