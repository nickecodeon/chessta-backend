package org.example.chessta.model.gameModels;

import jakarta.persistence.*;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public void setWhite(boolean isWhite) {
        this.isWhite = isWhite;
    }
}