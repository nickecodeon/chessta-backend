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
    private int board_row; // 1-8

    @Column(nullable = false)
    private int board_column; // 1-8 (a-h entspricht 1-8)

    @Column(nullable = false)
    private boolean isWhite;

    @Column(nullable = false)
    private boolean isCaptured;

    // Standardkonstruktor
    protected Figure() {
    }

    // Konstruktor für neue Figuren
    public Figure(int board_row, int board_column, boolean isWhite) {
        setBoard_row(board_row);
        setBoard_column(board_column);
        setColor(isWhite);
        isCaptured = false;
    }

    // Konstruktor für geladene Figuren
    public Figure(int id, int board_row, int board_column, boolean isWhite, boolean isCaptured) {
        this.id = id;
        setBoard_row(board_row);
        setBoard_column(board_column);
        setColor(isWhite);
        this.isCaptured = isCaptured;
    }

    // Methoden

    /**
     * Prüft, ob eine Bewegung gültig ist.
     * Diese Methode muss von den Unterklassen implementiert werden.
     */
    public abstract boolean isValidMove(int row, int column);

    // Setter und Getter

    public void setBoard_row(int row) {
        if (row < 1 || row > 8) {
            throw new IllegalArgumentException("Row must be between 1 and 8.");
        }
        this.board_row = row;
    }
    public void setBoard_column(int column) {
        if (column < 1 || column > 8) {
            throw new IllegalArgumentException("Column must be between 1 and 8.");
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