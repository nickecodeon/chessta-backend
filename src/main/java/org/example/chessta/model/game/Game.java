package org.example.chessta.model.game;

import jakarta.persistence.*;
import org.example.chessta.repository.FigureRepository;
import org.example.chessta.model.figure.*;

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
    public Game(String whitePlayerName, String blackPlayerName) {
        this.whitePlayer = new Player(whitePlayerName, true);
        this.blackPlayer = new Player(blackPlayerName, false);
        this.currentPlayer = this.whitePlayer;
    }

    // Konstruktor für geladene Spiele
    public Game(int id, Player whitePlayer, Player blackPlayer, Player currentPlayer, List<Integer> figureIds) {
        this.id = id;
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        this.currentPlayer = currentPlayer;
        this.figureIds = figureIds;
    }

    // --- Methoden ---

    /**
     * Initialisiert das Spielbrett, erstellt die Figuren und fügt ihre IDs hinzu.
     */
    public void initializeBoard(FigureRepository figureRepository) {
        // IDs der Figuren für das Spiel
        List<Integer> figureIds = new ArrayList<>();

        // Weiß: Backrow
        figureIds.add(figureRepository.save(new Rook(1, 1, true)).getId());
        figureIds.add(figureRepository.save(new Knight(1, 2, true)).getId());
        figureIds.add(figureRepository.save(new Bishop(1, 3, true)).getId());
        figureIds.add(figureRepository.save(new Queen(1, 4, true)).getId());
        figureIds.add(figureRepository.save(new King(1, 5, true)).getId());
        figureIds.add(figureRepository.save(new Bishop(1, 6, true)).getId());
        figureIds.add(figureRepository.save(new Knight(1, 7, true)).getId());
        figureIds.add(figureRepository.save(new Rook(1, 8, true)).getId());

        // Weiß: Pawns
        for (int col = 1; col <= 8; col++) {
            figureIds.add(figureRepository.save(new Pawn(2, col, true)).getId());
        }

        // Schwarz: Backrow
        figureIds.add(figureRepository.save(new Rook(8, 1, false)).getId());
        figureIds.add(figureRepository.save(new Knight(8, 2, false)).getId());
        figureIds.add(figureRepository.save(new Bishop(8, 3, false)).getId());
        figureIds.add(figureRepository.save(new Queen(8, 4, false)).getId());
        figureIds.add(figureRepository.save(new King(8, 5, false)).getId());
        figureIds.add(figureRepository.save(new Bishop(8, 6, false)).getId());
        figureIds.add(figureRepository.save(new Knight(8, 7, false)).getId());
        figureIds.add(figureRepository.save(new Rook(8, 8, false)).getId());

        // Schwarz: Pawns
        for (int col = 1; col <= 8; col++) {
            figureIds.add(figureRepository.save(new Pawn(7, col, false)).getId());
        }

        // Figuren-IDs zum Spiel hinzufügen
        this.figureIds = figureIds;
    }

    /**
     * Lädt eine Figur anhand ihrer ID.
     */
    public Figure getFigureById(FigureRepository figureRepository, int figureId) {
        return figureRepository.findById(figureId)
                .orElseThrow(() -> new RuntimeException("Figur mit ID " + figureId + " nicht gefunden."));
    }

    /**
     * Überprüft, ob eine Position auf dem Spielfeld besetzt ist.
     */
    public boolean isPositionOccupied(FigureRepository figureRepository, int row, int col) {
        return figureIds.stream()
                .map(id -> getFigureById(figureRepository, id))
                .anyMatch(figure -> figure.getBoard_row() == row && figure.getBoard_column() == col);
    }

    /**
     * Überprüft, ob eine gegnerische Figur an einer bestimmten Position steht.
     */
    public boolean isEnemyAtPosition(FigureRepository figureRepository, int row, int col, boolean isWhitePlayer) {
        return figureIds.stream()
                .map(id -> getFigureById(figureRepository, id))
                .filter(figure -> figure.getBoard_row() == row && figure.getBoard_column() == col)
                .anyMatch(figure -> figure.isWhite() != isWhitePlayer);
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