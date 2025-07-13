package org.example.chessta.service;

import org.example.chessta.model.Figure;
import org.example.chessta.model.Game;
import org.example.chessta.model.Move;
import org.example.chessta.repository.MoveRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MoveService {
    private final MoveRepository moveRepository;

    public MoveService(MoveRepository moveRepository) {
        this.moveRepository = moveRepository;
    }

    public void createMove(Figure figure, int toRow, int toCol, boolean isCapture, Game game, int inGameCount) {
        Move move = new Move(figure.getBoard_row(), figure.getBoard_column(),
                toRow, toCol, isCapture, figure.getClass().getSimpleName(),
                figure, game, inGameCount
        );
        moveRepository.save(move);
    }

    public Move getMoveById(UUID moveId) {
        return moveRepository.findById(moveId)
                .orElseThrow(() -> new RuntimeException("Move with ID: " + moveId + " not found."));
    }

    public void deleteMovesForGame(UUID gameId) {
        List<Move> moves = moveRepository.findByGameId(gameId);
        moveRepository.deleteAll(moves);
    }
}
