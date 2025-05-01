package org.example.chessta.service;

import org.example.chessta.model.gameModels.Figure;
import org.example.chessta.model.gameModels.Game;
import org.example.chessta.model.gameModels.Move;
import org.example.chessta.repository.MoveRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoveService {
    private final MoveRepository moveRepository;

    public MoveService(MoveRepository moveRepository) {
        this.moveRepository = moveRepository;
    }

    public void createMove(Figure figure, int toRow, int toCol, boolean isCapture, Game game) {
        Move move = new Move(figure.getBoard_row(), figure.getBoard_column(), toRow, toCol, isCapture, figure.getClass().getSimpleName(), figure, game);
        moveRepository.save(move);
    }

    public Move getMoveById(int moveId) {
        return moveRepository.findById(moveId)
                .orElseThrow(() -> new RuntimeException("Move with ID: " + moveId + " not found."));
    }

    public List<Move> getAllMovesForGame(int gameId) {
        return moveRepository.findByGameId(gameId);
    }

    public void deleteMovesForGame(int gameId) {
        List<Move> moves = moveRepository.findByGameId(gameId);
        moveRepository.deleteAll(moves);
    }
}
