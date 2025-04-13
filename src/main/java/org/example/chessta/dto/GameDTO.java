package org.example.chessta.dto;

import org.example.chessta.model.gameModels.Game;

import java.util.List;

public class GameDTO {
    private int id;
    private String whitePlayer;
    private String blackPlayer;
    private String currentPlayer;
    private List<FigureDTO> figures;

    public GameDTO(int id, String whitePlayer, String blackPlayer, String currentPlayer, List<FigureDTO> figures) {
        this.id = id;
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        this.currentPlayer = currentPlayer;
        this.figures = figures;
    }

    public static GameDTO fromEntity(Game game, List<FigureDTO> figures) {
        return new GameDTO(
                game.getId(),
                game.getWhitePlayer().getName(),
                game.getBlackPlayer().getName(),
                game.getCurrentPlayer().getName(),
                figures
        );
    }

    // Getter & Setter
    public int getId() {
        return id;
    }

    public String getWhitePlayer() {
        return whitePlayer;
    }

    public String getBlackPlayer() {
        return blackPlayer;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public List<FigureDTO> getFigures() {
        return figures;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setWhitePlayer(String whitePlayer) {
        this.whitePlayer = whitePlayer;
    }

    public void setBlackPlayer(String blackPlayer) {
        this.blackPlayer = blackPlayer;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setFigures(List<FigureDTO> figures) {
        this.figures = figures;
    }
}