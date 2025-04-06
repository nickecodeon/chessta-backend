package org.example.chessta.controller;

import org.example.chessta.dto.FigureDTO;
import org.example.chessta.dto.GameDTO;
import org.example.chessta.model.gameModels.Figure;
import org.example.chessta.model.gameModels.Game;
import org.example.chessta.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    // Erstelle ein neues Spiel
    @PostMapping("/new")
    public ResponseEntity<GameDTO> createNewGame(@RequestParam String whitePlayerName, @RequestParam String blackPlayerName) {
        Game game = gameService.createNewGame(whitePlayerName, blackPlayerName);
        return ResponseEntity.ok(GameDTO.fromEntity(game));
    }

    // Lade ein Spiel anhand der ID
    @GetMapping("/{id}")
    public ResponseEntity<GameDTO> loadGame(@PathVariable int id) {
        Game game = gameService.loadGame(id);
        if (game != null) {
            return ResponseEntity.ok(GameDTO.fromEntity(game));
        }
        return ResponseEntity.notFound().build();
    }

    // Lade alle Figuren eines Spiels
    @GetMapping("/{id}/figures")
    public ResponseEntity<List<FigureDTO>> getFigures(@PathVariable int id) {
        List<Figure> figures = gameService.getFiguresInGame(id);
        if (figures != null) {
            return ResponseEntity.ok(FigureDTO.fromEntity(figures));
        }
        return ResponseEntity.notFound().build();
    }

    // Führe einen Zug aus
    @PostMapping("/{id}/move")
    public ResponseEntity<String> makeMove(
            @PathVariable int id,
            @RequestParam int fromRow,
            @RequestParam int fromCol,
            @RequestParam int toRow,
            @RequestParam int toCol
    ) {
        boolean moveSuccessful = gameService.executeMove(id, fromRow, fromCol, toRow, toCol);
        if (moveSuccessful) {
            return ResponseEntity.ok("Zug erfolgreich ausgeführt!");
        } else {
            return ResponseEntity.badRequest().body("Ungültiger Zug.");
        }
    }

    // Liste aller Spiele
    @GetMapping
    public ResponseEntity<List<Game>> getAllGames() {
        List<Game> games = gameService.getAllGames();
        return ResponseEntity.ok(games);
    }

    // Spiel löschen
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGame(@PathVariable int id) {
        boolean deleted = gameService.deleteGame(id);
        if (deleted) {
            return ResponseEntity.ok("Spiel erfolgreich gelöscht.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}