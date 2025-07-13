package org.example.chessta.repository;

import org.example.chessta.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GameRepository extends JpaRepository<Game, UUID> {
    // Zusätzliche benutzerdefinierte Abfragen, falls erforderlich
}