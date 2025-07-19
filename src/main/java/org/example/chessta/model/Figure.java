package org.example.chessta.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Figure {

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private int board_row; // 0-7

    @Column(nullable = false)
    private int board_column; // 0-7 (a-h entspricht 0-7)

    @Column(nullable = false)
    private boolean isWhite;

    @Column(nullable = false)
    private boolean isCaptured;

    @Column(nullable = false)
    private FigureType type;

    // --- Konstruktoren ---

    public Figure() {}

    // Konstruktor für neue Figuren
    public Figure(int board_row, int board_column, boolean isWhite, FigureType type) {
        setBoard_row(board_row);
        setBoard_column(board_column);
        setColor(isWhite);
        isCaptured = false;
        this.type = type;
    }

    // --- Getter & Setter ---

    public void setBoard_row(int row) {
        if (row < 0 || row > 7) {
            throw new IllegalArgumentException("Row must be between 0 and 7.");
        }
        this.board_row = row;
    }

    public void setBoard_column(int column) {
        if (column < 0 || column > 7) {
            throw new IllegalArgumentException("Column must be between 0 and 7.");
        }
        this.board_column = column;
    }

    public void setColor(boolean white) {
        this.isWhite = white;
    }

    public void setCaptured(boolean captured) {
        this.isCaptured = captured;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public int getBoard_row() {
        return board_row;
    }

    public int getBoard_column() {
        return board_column;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public boolean isCaptured() {
        return isCaptured;
    }

    public FigureType getType() {
        return type;
    }

    @SuppressWarnings("unused")
    public void setType(FigureType type) {
        this.type = type;
    }
}