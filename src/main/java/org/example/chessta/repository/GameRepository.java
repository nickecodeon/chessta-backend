package org.example.chessta.repository;

import org.example.chessta.model.gameModels.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {
    // Zusätzliche benutzerdefinierte Abfragen, falls erforderlich
}