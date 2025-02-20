package org.example.chessta.service;

import org.example.chessta.model.game.Figure;
import org.example.chessta.model.game.Game;
import org.example.chessta.repository.FigureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FigureService {

    @Autowired
    private FigureRepository figureRepository;

    public FigureService(FigureRepository figureRepository) {
        this.figureRepository = figureRepository;
    }

    public Figure saveFigure(Figure figure) {
        return figureRepository.save(figure);
    }

    public Figure getFigureById(int figureId) {
        return figureRepository.findById(figureId)
                .orElseThrow(() -> new RuntimeException("Figur mit ID " + figureId + " nicht gefunden."));
    }

    public Figure findFigureAtPosition(Game game, int row, int col) {
        List<Integer> figureIds = game.getFigureIds();

        for (Integer figureId : figureIds) {
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
        List<Integer> figureIds = game.getFigureIds();
        List<Figure> figures = new ArrayList<>();

        for (Integer figureId : figureIds) {
            Figure figure = figureRepository.findById(figureId).orElse(null);
            if (figure != null) {
                figures.add(figure);
            }
        }

        return figures;
    }
}
