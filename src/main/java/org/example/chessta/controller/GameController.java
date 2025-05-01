package org.example.chessta.controller;

import org.example.chessta.dto.FigureDTO;
import org.example.chessta.dto.GameDTO;
import org.example.chessta.dto.MoveDTO;
import org.example.chessta.dto.MoveResponseDTO;
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
        List<FigureDTO> figures= gameService.getFigureDTOsInGame(game.getId());
        return ResponseEntity.ok(GameDTO.fromEntity(game, figures));
    }

    // Lade ein Spiel anhand der ID
    @GetMapping("/{id}")
    public ResponseEntity<GameDTO> loadGame(@PathVariable int id) {
        Game game = gameService.loadGame(id);
        if (game != null) {
            List<FigureDTO> figures = gameService.getFigureDTOsInGame(game.getId());
            return ResponseEntity.ok(GameDTO.fromEntity(game, figures));
        }
        return ResponseEntity.notFound().build();
    }

    // Lade alle Figuren eines Spiels
    @GetMapping("/{id}/figures")
    public ResponseEntity<List<FigureDTO>> getFiguresInGame(@PathVariable int id) {
        List<FigureDTO> figures = gameService.getFigureDTOsInGame(id);
        if (figures != null) {
            return ResponseEntity.ok(figures);
        }
        return ResponseEntity.notFound().build();
    }

    // Führe einen Zug aus
    @PostMapping("/{id}/move")
    public ResponseEntity<MoveResponseDTO> makeMove(
            @PathVariable int id,
            @RequestBody MoveDTO moveDTO
    ) {
        boolean moveSuccessful = gameService.executeMove(id, moveDTO);
        if (moveSuccessful) {
            List<FigureDTO> figures = gameService.getFigureDTOsInGame(id);

            MoveResponseDTO response = new MoveResponseDTO(
                    true,
                    "Move successfully executed.",
                    figures
            );
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new MoveResponseDTO(false, "Invalid move."));
        }
    }

    // Liste aller Spiele
    @GetMapping
    public ResponseEntity<List<Integer>> fetchAllGameIds() {
        List<Integer> gameListDTO = gameService.getAllGames().stream()
                .map(Game::getId)
                .toList();

        return ResponseEntity.ok(gameListDTO);
    }

    // Spiel löschen
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGame(@PathVariable int id) {
        boolean deleted = gameService.deleteGame(id);
        if (deleted) {
            return ResponseEntity.ok("Game with ID " + id + " successfully deleted.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}