package org.example.chessta.repository;

import org.example.chessta.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PlayerRepository extends JpaRepository<Player, UUID> {
    // Zusätzliche benutzerdefinierte Abfragen, falls erforderlich
}