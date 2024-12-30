package org.example.chess.player;

import org.example.chess.figure.Figure;
import org.example.chess.figure.pieces.King;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private boolean isWhite;
    private List<Figure> pieces;

    public Player(String name, boolean isWhite) {
        setName(name);
        setColor(isWhite);
        this.pieces = new ArrayList<>();
    }

    public void addPiece(Figure piece) {
        pieces.add(piece);
    }
    public void removePiece(Figure piece) {
        pieces.remove(piece);
    }

    public boolean hasKing() {
        return pieces.stream().anyMatch(figure -> figure instanceof King);
    }

    // setter und getter

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public boolean isWhite() {
        return isWhite;
    }
    public void setColor(boolean isWhite) {
        this.isWhite = isWhite;
    }
    public List<Figure> getPieces() {
        return pieces;
    }
    public void setPieces(List<Figure> pieces) {
        this.pieces = pieces;
    }
}
