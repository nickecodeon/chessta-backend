package org.example.chessta.model;

import jakarta.persistence.*;
import org.example.chessta.service.FigureService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Game {

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "white_player_id", nullable = false)
    private Player whitePlayer;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "black_player_id", nullable = false)
    private Player blackPlayer;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "current_player_id", nullable = false)
    private Player currentPlayer;

    @ElementCollection
    @CollectionTable(name = "game_figures", joinColumns = @JoinColumn(name = "game_id"))
    @Column(name = "figure_id")
    private List<UUID> figureIds = new ArrayList<>();

    private int moveCount = 0;

    private boolean finished = false;

    // --- Konstruktoren ---

    protected Game() {}

    public Game(String whitePlayerName, String blackPlayerName, FigureService figureService) {
        this.whitePlayer = new Player(whitePlayerName, true);
        this.blackPlayer = new Player(blackPlayerName, false);
        this.currentPlayer = this.whitePlayer;
        initializeBoard(figureService);
    }

    // --- Methoden ---

    public void initializeBoard(FigureService figureService) {
        List<Figure> figures = new ArrayList<>();

        // White Figures (Row 0)
        figures.add(new Figure(0, 0, true, FigureType.ROOK));
        figures.add(new Figure(0, 1, true, FigureType.KNIGHT));
        figures.add(new Figure(0, 2, true, FigureType.BISHOP));
        figures.add(new Figure(0, 3, true, FigureType.QUEEN));
        figures.add(new Figure(0, 4, true, FigureType.KING));
        figures.add(new Figure(0, 5, true, FigureType.BISHOP));
        figures.add(new Figure(0, 6, true, FigureType.KNIGHT));
        figures.add(new Figure(0, 7, true, FigureType.ROOK));

        // Black Figures (Row 7)
        figures.add(new Figure(7, 0, false, FigureType.ROOK));
        figures.add(new Figure(7, 1, false, FigureType.KNIGHT));
        figures.add(new Figure(7, 2, false, FigureType.BISHOP));
        figures.add(new Figure(7, 3, false, FigureType.QUEEN));
        figures.add(new Figure(7, 4, false, FigureType.KING));
        figures.add(new Figure(7, 5, false, FigureType.BISHOP));
        figures.add(new Figure(7, 6, false, FigureType.KNIGHT));
        figures.add(new Figure(7, 7, false, FigureType.ROOK));

        // Pawns
        for (int col = 0; col < 8; col++) {
            figures.add(new Figure(1, col, true, FigureType.PAWN));
            figures.add(new Figure(6, col, false, FigureType.PAWN));
        }

        List<Figure> savedFigures = figureService.saveFigures(figures);
        this.figureIds = savedFigures.stream().map(Figure::getId).toList();
    }

    // --- Getter & Setter ---

    @SuppressWarnings("unused")
    public UUID getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public Player getWhitePlayer() {
        return whitePlayer;
    }

    @SuppressWarnings("unused")
    public Player getBlackPlayer() {
        return blackPlayer;
    }

    @SuppressWarnings("unused")
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    @SuppressWarnings("unused")
    public List<UUID> getFigureIds() {
        return figureIds;
    }

    @SuppressWarnings("unused")
    public int getMoveCount() {
        return moveCount;
    }

    @SuppressWarnings("unused")
    public void setId(UUID id) {
        this.id = id;
    }

    @SuppressWarnings("unused")
    public void setWhitePlayer(Player whitePlayer) {
        this.whitePlayer = whitePlayer;
    }

    @SuppressWarnings("unused")
    public void setBlackPlayer(Player blackPlayer) {
        this.blackPlayer = blackPlayer;
    }

    @SuppressWarnings("unused")
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    @SuppressWarnings("unused")
    public void setFigureIds(List<UUID> figureIds) {
        this.figureIds = figureIds;
    }

    @SuppressWarnings("unused")
    public void setMoveCount(int moveCount) {
        this.moveCount = moveCount;
    }

    @SuppressWarnings("unused")
    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    @SuppressWarnings("unused")
    public boolean getFinished() {
        return finished;
    }
}