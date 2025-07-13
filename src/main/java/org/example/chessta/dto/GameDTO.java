package org.example.chessta.dto;

import org.example.chessta.model.Game;

import java.util.List;
import java.util.UUID;

public class GameDTO {
    private UUID id;
    private String whitePlayer;
    private String blackPlayer;
    private String currentPlayer;
    private List<FigureDTO> figures;
    private int moveCount;
    private boolean finished;

    public GameDTO(UUID id, String whitePlayer, String blackPlayer, String currentPlayer, List<FigureDTO> figures, int moveCount, boolean finished) {
        this.id = id;
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        this.currentPlayer = currentPlayer;
        this.figures = figures;
        this.moveCount = moveCount;
        this.finished = finished;
    }

    public static GameDTO fromEntity(Game game, List<FigureDTO> figures) {
        return new GameDTO(
                game.getId(),
                game.getWhitePlayer().getName(),
                game.getBlackPlayer().getName(),
                game.getCurrentPlayer().getName(),
                figures,
                game.getMoveCount(),
                game.getFinished()
        );
    }

    // Getter & Setter

    @SuppressWarnings("unused")
    public UUID getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public String getWhitePlayer() {
        return whitePlayer;
    }

    @SuppressWarnings("unused")
    public String getBlackPlayer() {
        return blackPlayer;
    }

    @SuppressWarnings("unused")
    public String getCurrentPlayer() {
        return currentPlayer;
    }

    @SuppressWarnings("unused")
    public List<FigureDTO> getFigures() {
        return figures;
    }

    @SuppressWarnings("unused")
    public int getMoveCount() {
        return moveCount;
    }

    @SuppressWarnings("unused")
    public void setId(UUID id) {
        this.id = id;
    }

    @SuppressWarnings("unused")
    public void setWhitePlayer(String whitePlayer) {
        this.whitePlayer = whitePlayer;
    }

    @SuppressWarnings("unused")
    public void setBlackPlayer(String blackPlayer) {
        this.blackPlayer = blackPlayer;
    }

    @SuppressWarnings("unused")
    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    @SuppressWarnings("unused")
    public void setFigures(List<FigureDTO> figures) {
        this.figures = figures;
    }

    @SuppressWarnings("unused")
    public void setMoveCount(int moveCount) {
        this.moveCount = moveCount;
    }

    @SuppressWarnings("unused")
    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    @SuppressWarnings("unused")
    public boolean getFinished() {
        return finished;
    }
}