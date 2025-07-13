package org.example.chessta.repository;

import org.example.chessta.model.Figure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FigureRepository extends JpaRepository<Figure, UUID> {
    // Zusätzliche benutzerdefinierte Abfragen, falls erforderlich
}