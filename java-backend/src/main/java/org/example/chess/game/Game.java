package org.example.chess.game;

import org.example.chess.figure.Figure;
import org.example.chess.figure.pieces.*;
import org.example.chess.player.Player;

public class Game {
    private final Player whitePlayer;
    private final Player blackPlayer;
    private final Figure[][] board;
    private Player currentPlayer;

    public Game(String nameWhite, String nameBlack) {
        this.whitePlayer = new Player(nameWhite, true);
        this.blackPlayer = new Player(nameBlack, false);
        this.board = new Figure[8][8];
        this.currentPlayer = whitePlayer;
        initializeBoard();
    }

    public void initializeBoard() {
        // Figuren für den weißen Spieler
        whitePlayer.addPiece(new Rook(1, 1, true));
        whitePlayer.addPiece(new Knight(2, 1, true));
        whitePlayer.addPiece(new Bishop(3, 1, true));
        whitePlayer.addPiece(new Queen(4, 1, true));
        whitePlayer.addPiece(new King(5, 1, true));
        whitePlayer.addPiece(new Bishop(6, 1, true));
        whitePlayer.addPiece(new Knight(7, 1, true));
        whitePlayer.addPiece(new Rook(8, 1, true));
        for (int i = 1; i <= 8; i++) {
            whitePlayer.addPiece(new Pawn(i, 2, true));
        }

        blackPlayer.addPiece(new Rook(1, 8, false));
        blackPlayer.addPiece(new Knight(2, 8, false));
        blackPlayer.addPiece(new Bishop(3, 8, false));
        blackPlayer.addPiece(new Queen(4, 8, false));
        blackPlayer.addPiece(new King(5, 8, false));
        blackPlayer.addPiece(new Bishop(6, 8, false));
        blackPlayer.addPiece(new Knight(7, 8, false));
        blackPlayer.addPiece(new Rook(8, 8, false));
        for (int i = 1; i <= 8; i++) {
            blackPlayer.addPiece(new Pawn(i, 7, false));
        }

        // Figuren dem Board zuweisen
        for (Figure piece : whitePlayer.getPieces()) {
            board[piece.getRow() - 1][piece.getColumn() - 1] = piece;
        }
        for (Figure piece : blackPlayer.getPieces()) {
            board[piece.getRow() - 1][piece.getColumn() - 1] = piece;
        }
    }

    public void switchTurn() {
        currentPlayer = (currentPlayer == whitePlayer) ? blackPlayer : whitePlayer;
    }

    // getter methoden

    public Player getWhitePlayer() {
        return whitePlayer;
    }
    public Player getBlackPlayer() {
        return blackPlayer;
    }
    public Figure[][] getBoard() {
        return board;
    }
}
