package org.example.chessta.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Player {

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean isWhite;

    // --- Konstruktoren ---

    protected Player() {}

    // Konstruktor für neue Spieler
    public Player(String name, boolean isWhite) {
        this.name = name;
        this.isWhite = isWhite;
    }

    // --- Getter & Setter ---

    @SuppressWarnings("unused")
    public UUID getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public void setId(UUID id) {
        this.id = id;
    }

    @SuppressWarnings("unused")
    public String getName() {
        return name;
    }

    @SuppressWarnings("unused")
    public void setName(String name) {
        this.name = name;
    }

    @SuppressWarnings("unused")
    public boolean isWhite() {
        return isWhite;
    }

    @SuppressWarnings("unused")
    public void setWhite(boolean isWhite) {
        this.isWhite = isWhite;
    }
}