package org.example.chessta.repository;

import org.example.chessta.model.game.Figure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FigureRepository extends JpaRepository<Figure, Integer> {
    // Zusätzliche benutzerdefinierte Abfragen, falls erforderlich
}