package org.example.chess.figure;

public abstract class Figure {
    private int row; // 1-8
    private int column; // a-h aber hier 1-8
    boolean isWhite;

    public Figure(int column, int row, boolean isWhite) {
        setColumn(column);
        setRow(row);
        setColor(isWhite);
    }

    // methoden

    public boolean moveInside(int x, int y) {
        return (x > 0 && x < 9) && (y > 0 && y < 9);
    }

    public abstract boolean isValidMove(int x, int y);

    public boolean move(int x, int y) {
        if (moveInside(x, y) && isValidMove(x, y)) {
            setColumn(x);
            setRow(y);

            return true;
        } else {
            return false;
        }
    }

    // setter und getter

    public void setRow(int row) {
        if (row < 1 || row > 8) {
            throw new IllegalArgumentException("Invalid row or column");
        }
        this.row = row;
    }
    public void setColumn(int column) {
        if (column < 1 || column > 8) {
            throw new IllegalArgumentException("Invalid column or row");
        }
        this.column = column;
    }
    public void setColor(boolean white) { this.isWhite = white; }

    public int getRow() { return row; }
    public int getColumn() { return column; }
    public boolean isWhite() { return isWhite; }
}
