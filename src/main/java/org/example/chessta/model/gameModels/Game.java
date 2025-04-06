package org.example.chessta.model.gameModels;

import jakarta.persistence.*;
import org.example.chessta.model.figureModels.*;
import org.example.chessta.service.FigureService;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

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
    private List<Integer> figureIds = new ArrayList<>();

    // Standardkonstruktor (erforderlich für JPA)
    protected Game() {
    }

    // Konstruktor für ein neues Spiel
    public Game(String whitePlayerName, String blackPlayerName, FigureService figureService) {
        this.whitePlayer = new Player(whitePlayerName, true);
        this.blackPlayer = new Player(blackPlayerName, false);
        this.currentPlayer = this.whitePlayer;
        initializeBoard(figureService);
    }

    // --- Methoden ---

    public void initializeBoard(FigureService figureService) {
        List<Figure> figures = List.of(
                new Rook(1, 1, true), new Knight(1, 2, true), new Bishop(1, 3, true),
                new Queen(1, 4, true), new King(1, 5, true), new Bishop(1, 6, true),
                new Knight(1, 7, true), new Rook(1, 8, true),
                new Rook(8, 1, false), new Knight(8, 2, false), new Bishop(8, 3, false),
                new Queen(8, 4, false), new King(8, 5, false), new Bishop(8, 6, false),
                new Knight(8, 7, false), new Rook(8, 8, false)
        );

        for (int col = 1; col <= 8; col++) {
            figures.add(new Pawn(2, col, true));
            figures.add(new Pawn(7, col, false));
        }

        List<Figure> savedFigures = figureService.saveFigures(figures);  // Speichert alle auf einmal
        this.figureIds = savedFigures.stream().map(Figure::getId).toList();
    }

    // --- Getter und Setter ---

    public int getId() {
        return id;
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }

    public Player getBlackPlayer() {
        return blackPlayer;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public List<Integer> getFigureIds() {
        return figureIds;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setWhitePlayer(Player whitePlayer) {
        this.whitePlayer = whitePlayer;
    }

    public void setBlackPlayer(Player blackPlayer) {
        this.blackPlayer = blackPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setFigureIds(List<Integer> figureIds) {
        this.figureIds = figureIds;
    }
}