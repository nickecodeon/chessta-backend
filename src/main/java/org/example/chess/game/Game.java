package org.example.chess.game;

import org.example.chess.database.GameRepository;
import org.example.chess.figure.*;

public class Game {
    private int id;
    private Player whitePlayer;
    private Player blackPlayer;
    private Figure[][] board;
    private Player currentPlayer;

    public Game(String whitePlayerName, String blackPlayerName) {
        this.whitePlayer = new Player(whitePlayerName, true);
        this.blackPlayer = new Player(blackPlayerName, false);
        this.currentPlayer = this.whitePlayer;
        this.board = new Figure[8][8];
        initializeBoard();
    }

    // Konstruktor für geladene Spiele
    public Game(int id, Player whitePlayer, Player blackPlayer, Player currentPlayer, Figure[][] board) {
        this.id = id;
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        this.currentPlayer = currentPlayer;
        this.board = board;
    }

    public void initializeBoard() {
        // Figuren für den weißen Spieler
        whitePlayer.addPiece(new Rook(1, 1, true));
        whitePlayer.addPiece(new Knight(1, 2, true));
        whitePlayer.addPiece(new Bishop(1, 3, true));
        whitePlayer.addPiece(new Queen(1, 4, true));
        whitePlayer.addPiece(new King(1, 5, true));
        whitePlayer.addPiece(new Bishop(1, 6, true));
        whitePlayer.addPiece(new Knight(1, 7, true));
        whitePlayer.addPiece(new Rook(1, 8, true));
        for (int col = 1; col <= 8; col++) {
            whitePlayer.addPiece(new Pawn(2, col, true));
        }

        blackPlayer.addPiece(new Rook(8, 1, false));
        blackPlayer.addPiece(new Knight(8, 2, false));
        blackPlayer.addPiece(new Bishop(8, 3, false));
        blackPlayer.addPiece(new Queen(8, 4, false));
        blackPlayer.addPiece(new King(8, 5, false));
        blackPlayer.addPiece(new Bishop(8, 6, false));
        blackPlayer.addPiece(new Knight(8, 7, false));
        blackPlayer.addPiece(new Rook(8, 8, false));
        for (int col = 1; col <= 8; col++) {
            blackPlayer.addPiece(new Pawn(7, col, false));
        }

        // Figuren dem Board zuweisen
        for (Figure piece : whitePlayer.getPieces()) {
            board[piece.getRow() - 1][piece.getColumn() - 1] = piece;
        }
        for (Figure piece : blackPlayer.getPieces()) {
            board[piece.getRow() - 1][piece.getColumn() - 1] = piece;
        }
    }

    public boolean moveFigure(int fromRow, int fromCol, int toRow, int toCol) {
        if (!validateMove(fromRow, fromCol, toRow, toCol)) {
            return false;
        }

        executeMove(fromRow, fromCol, toRow, toCol);
        return true;
    }

    public boolean validateMove(int fromRow, int fromCol, int toRow, int toCol) {
        Figure figure = getFigureAtPosition(fromRow, fromCol);
        if (figure == null || figure.isWhite() != currentPlayer.isWhite()) {
            System.out.println("Invalid Player");
            return false;
        }

        if (!figure.isValidMove(toRow, toCol)) {
            System.out.println("Invalid Move");
            return false;
        }

        // Prüfen, ob die Zielposition blockiert ist (eigene Figur)
        Figure targetFigure = getFigureAtPosition(toRow, toCol);
        if (targetFigure != null && targetFigure.isWhite() == currentPlayer.isWhite()) {
            System.out.println("Eigene Figur blockiert");
            return false; // Eigene Figur blockiert den Zug
        }

        return true;
    }

    private void executeMove(int fromRow, int fromCol, int toRow, int toCol) {
        Figure figure = getFigureAtPosition(fromRow, fromCol);
        Figure targetFigure = getFigureAtPosition(toRow, toCol);

        // Entferne geschlagene Figur
        if (targetFigure != null) {
            getOpponentPlayer().removePiece(targetFigure);
        }

        if (figure.move(toRow, toCol)) {
            board[toRow - 1][toCol - 1] = figure;
            board[fromRow - 1][fromCol - 1] = null;
        }

        switchTurn();
    }

    public void switchTurn() {
        setCurrentPlayer(this.currentPlayer == this.whitePlayer ? this.blackPlayer : this.whitePlayer);
    }

    public boolean isPositionOccupied(int row, int col) {
        return board[row - 1][col - 1] != null;
    }

    public boolean isEnemyAtPosition(int row, int col, boolean isWhitePlayer) {
        Figure figure = board[row - 1][col - 1];
        return figure != null && figure.isWhite() != isWhitePlayer;
    }

    public void saveToDatabase() {
        GameRepository.saveGame(this);
    }

    // getter methoden

    public int getId() {
        return id;
    }
    public Player getWhitePlayer() {
        return whitePlayer;
    }
    public Player getBlackPlayer() {
        return blackPlayer;
    }
    public Figure[][] getBoard() {
        return board;
    }
    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    public Figure getFigureAtPosition(int row, int col) {
        return board[row - 1][col - 1];
    }
    public Player getOpponentPlayer() {
        return (currentPlayer == whitePlayer) ? blackPlayer : whitePlayer;
    }
    public int getWhitePlayerId() {
        return this.whitePlayer.getId(); }
    public int getBlackPlayerId() {
        return this.blackPlayer.getId(); }

    public void setId(int id) {
        this.id = id; }
    public void setWhitePlayer(Player whitePlayer) {
        this.whitePlayer = whitePlayer; }
    public void setBlackPlayer(Player blackPlayer) {
        this.blackPlayer = blackPlayer; }
    public void setBoard(Figure[][] board) {
        this.board = board; }
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
}
