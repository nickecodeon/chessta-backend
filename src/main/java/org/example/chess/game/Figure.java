package org.example.chess.game;

public abstract class Figure {
    private int id;
    private int row; // 1-8
    private int column; // 1-8 (a-h entspricht 1-8)
    private boolean isWhite;
    private boolean isCaptured;

    // Konstruktor für neue Figuren
    public Figure(int row, int column, boolean isWhite) {
        setRow(row);
        setColumn(column);
        setColor(isWhite);
        isCaptured = false;
    }

    // Konstruktor für geladene Figuren
    public Figure(int id, int row, int column, boolean isWhite, boolean isCaptured) {
        this.id = id;
        setRow(row);
        setColumn(column);
        setColor(isWhite);
        this.isCaptured = isCaptured;
    }

    // Methoden

    /**
     * Prüft, ob eine Bewegung innerhalb des Spielfelds bleibt.
     */
    public boolean moveInside(int row, int column) {
        return (row > 0 && row < 9) && (column > 0 && column < 9);
    }

    /**
     * Prüft, ob eine Bewegung gültig ist.
     * Diese Methode muss von den Unterklassen implementiert werden.
     */
    public abstract boolean isValidMove(int row, int column);

    /**
     * Führt eine Bewegung durch, falls sie gültig ist.
     */
    public boolean move(int row, int column) {
        if (moveInside(row, column) && isValidMove(row, column)) {
            setRow(row);
            setColumn(column);
            return true;
        } else {
            return false;
        }
    }

    // Setter und Getter

    public void setRow(int row) {
        if (row < 1 || row > 8) {
            throw new IllegalArgumentException("Row must be between 1 and 8.");
        }
        this.row = row;
    }
    public void setColumn(int column) {
        if (column < 1 || column > 8) {
            throw new IllegalArgumentException("Column must be between 1 and 8.");
        }
        this.column = column;
    }
    public void setColor(boolean white) {
        this.isWhite = white;
    }
    public void setCaptured() {
        this.isCaptured = true;
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
    public int getRow() {
        return row;
    }
    public int getColumn() {
        return column;
    }
    public boolean isWhite() {
        return isWhite;
    }
    public boolean isCaptured() {
        return isCaptured;
    }
}