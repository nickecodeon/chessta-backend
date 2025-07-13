package org.example.chessta.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Move {

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    private int fromRow;
    private int fromCol;
    private int toRow;
    private int toCol;

    private boolean capture;

    @Column(nullable = false)
    private String figureType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "figure_id")
    private Figure figure;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    private int inGameCount;

    // --- Konstruktoren ---

    public Move() {}

    public Move(int fromRow, int fromCol, int toRow, int toCol, boolean capture,
                String figureType, Figure figure, Game game, int inGameCount) {
        this.fromRow = fromRow;
        this.fromCol = fromCol;
        this.toRow = toRow;
        this.toCol = toCol;
        this.capture = capture;
        this.figureType = figureType;
        this.figure = figure;
        this.game = game;
        this.inGameCount = inGameCount;
    }

    // --- Getter & Setter ---

    @SuppressWarnings("unused")
    public UUID getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public void setId(UUID id) {
        this.id = id;
    }

    @SuppressWarnings("unused")
    public int getFromRow() {
        return fromRow;
    }

    @SuppressWarnings("unused")
    public void setFromRow(int fromRow) {
        this.fromRow = fromRow;
    }

    @SuppressWarnings("unused")
    public int getFromCol() {
        return fromCol;
    }

    @SuppressWarnings("unused")
    public void setFromCol(int fromCol) {
        this.fromCol = fromCol;
    }

    @SuppressWarnings("unused")
    public int getToRow() {
        return toRow;
    }

    @SuppressWarnings("unused")
    public void setToRow(int toRow) {
        this.toRow = toRow;
    }

    public int getToCol() {
        return toCol;
    }

    @SuppressWarnings("unused")
    public void setToCol(int toCol) {
        this.toCol = toCol;
    }

    @SuppressWarnings("unused")
    public boolean isCapture() {
        return capture;
    }

    @SuppressWarnings("unused")
    public void setCapture(boolean capture) {
        this.capture = capture;
    }

    @SuppressWarnings("unused")
    public String getFigureType() {
        return figureType;
    }

    @SuppressWarnings("unused")
    public void setFigureType(String figureType) {
        this.figureType = figureType;
    }

    @SuppressWarnings("unused")
    public Figure getFigure() {
        return figure;
    }

    @SuppressWarnings("unused")
    public void setFigure(Figure figure) {
        this.figure = figure;
    }

    @SuppressWarnings("unused")
    public Game getGame() {
        return game;
    }

    @SuppressWarnings("unused")
    public void setGame(Game game) {
        this.game = game;
    }

    @SuppressWarnings("unused")
    public int getInGameCount() {
        return inGameCount;
    }

    @SuppressWarnings("unused")
    public void setInGameCount(int inGameCount) {
        this.inGameCount = inGameCount;
    }
}