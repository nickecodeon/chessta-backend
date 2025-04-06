package org.example.chessta.repository;

import org.example.chessta.model.gameModels.Move;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoveRepository extends JpaRepository<Move, Integer> {
    List<Move> findByGameId(int gameId);
}
