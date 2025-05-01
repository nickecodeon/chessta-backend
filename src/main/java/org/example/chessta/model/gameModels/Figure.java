package org.example.chessta.model.gameModels;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class Figure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int board_row; // 0-7

    @Column(nullable = false)
    private int board_column; // 0-7 (a-h entspricht 0-7)

    @Column(nullable = false)
    private boolean isWhite;

    @Column(nullable = false)
    private boolean isCaptured;

    // --- Konstruktoren ---

    protected Figure() {}

    // Konstruktor für neue Figuren
    public Figure(int board_row, int board_column, boolean isWhite) {
        setBoard_row(board_row);
        setBoard_column(board_column);
        setColor(isWhite);
        isCaptured = false;
    }

    // --- Methoden ---

    public abstract boolean isValidMove(int row, int column);

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

    public void unsetCaptured() {
        this.isCaptured = false;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
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
}