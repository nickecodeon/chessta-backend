package org.example.chessta.repository;

import org.example.chessta.model.gameModels.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {
    // Zusätzliche benutzerdefinierte Abfragen, falls erforderlich
}