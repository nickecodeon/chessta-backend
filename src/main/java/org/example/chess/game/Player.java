package org.example.chess.game;

import org.example.chess.figure.King;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private int id;
    private String name;
    private boolean isWhite;
    private List<Figure> pieces;
    private List<Figure> lostPieces = new ArrayList<>();

    public Player(String name, boolean isWhite) {
        this.name = name;
        this.isWhite = isWhite;
        this.pieces = new ArrayList<>();
    }

    // Konstruktor für geladene Spieler
    public Player(int id, String name, boolean isWhite, List<Figure> pieces) {
        this.id = id;
        this.name = name;
        this.isWhite = isWhite;
        this.pieces = pieces;
    }

    public void addPiece(Figure piece) {
        pieces.add(piece);
    }

    public boolean removePiece(Figure piece) {
        if (pieces.contains(piece)) {
            lostPieces.add(piece);
            pieces.remove(piece);
            return true;
        }
        return false;
    }

    public boolean hasKing() {
        return pieces.stream().anyMatch(figure -> figure instanceof King);
    }

    // setter und getter

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public boolean isWhite() {
        return isWhite;
    }
    public List<Figure> getPieces() {
        return pieces;
    }
    public List<Figure> getLostPieces() {
        return lostPieces;
    }

    public void setId(int id) { this.id = id; }
    public void setName(String name) {
        this.name = name;
    }
    public void setColor(boolean isWhite) {
        this.isWhite = isWhite;
    }
    public void setPieces(List<Figure> pieces) {
        this.pieces = pieces;
    }
    public void setLostPieces(List<Figure> lostPieces) {
        this.lostPieces = lostPieces;
    }
}
