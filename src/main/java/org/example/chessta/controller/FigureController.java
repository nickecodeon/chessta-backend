package org.example.chessta.controller;

import org.example.chessta.dto.FigureDTO;
import org.example.chessta.model.Figure;
import org.example.chessta.service.FigureService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/figures")
public class FigureController {
    private final FigureService figureService;

    public FigureController(FigureService figureService) {
        this.figureService = figureService;
    }

    // Lade eine Figur anhand der ID
    @GetMapping("/{id}")
    public ResponseEntity<FigureDTO> getFigureById(@PathVariable UUID id) {
        Figure figure = figureService.getFigureById(id);

        if (figure != null) {
            return ResponseEntity.ok(FigureDTO.fromEntity(figure));
        }
        return ResponseEntity.notFound().build();
    }
}
