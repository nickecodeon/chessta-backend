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

    public Figure copyFigure(Figure original) {
        Figure copy = new Figure();
        copy.setId(original.getId());
        copy.setBoard_row(original.getBoard_row());
        copy.setBoard_column(original.getBoard_column());
        copy.setColor(original.isWhite());
        copy.setType(original.getType());
        return copy;
    }
}
