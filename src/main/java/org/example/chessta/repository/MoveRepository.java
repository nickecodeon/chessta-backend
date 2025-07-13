package org.example.chessta.repository;

import org.example.chessta.model.Move;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MoveRepository extends JpaRepository<Move, UUID> {
    List<Move> findByGameId(UUID gameId);
}
