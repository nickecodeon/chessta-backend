package org.example.chessta.model.gameModels;

import jakarta.persistence.*;
import org.example.chessta.model.figureModels.*;
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

    // --- Konstruktoren ---

    protected Game() {}

    // Konstruktor für ein neues Spiel
    public Game(String whitePlayerName, String blackPlayerName, FigureService figureService) {
        this.whitePlayer = new Player(whitePlayerName, true);
        this.blackPlayer = new Player(blackPlayerName, false);
        this.currentPlayer = this.whitePlayer;
        initializeBoard(figureService);
    }

    // --- Methoden ---

    public void initializeBoard(FigureService figureService) {
        List<Figure> figures = new ArrayList<>(List.of(
                new Rook(0, 0, true), new Knight(0, 1, true), new Bishop(0, 2, true),
                new Queen(0, 3, true), new King(0, 4, true), new Bishop(0, 5, true),
                new Knight(0, 6, true), new Rook(0, 7, true),
                new Rook(7, 0, false), new Knight(7, 1, false), new Bishop(7, 2, false),
                new Queen(7, 3, false), new King(7, 4, false), new Bishop(7, 5, false),
                new Knight(7, 6, false), new Rook(7, 7, false)
        ));

        for (int col = 0; col < 8; col++) {
            figures.add(new Pawn(1, col, true));  // Weiße Bauern in Reihe 1 (zweite Zeile)
            figures.add(new Pawn(6, col, false)); // Schwarze Bauern in Reihe 6 (vorletzte Zeile)
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
}