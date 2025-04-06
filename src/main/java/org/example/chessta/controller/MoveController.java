package org.example.chessta.controller;


import org.example.chessta.dto.MoveDTO;
import org.example.chessta.model.gameModels.Move;
import org.example.chessta.service.MoveService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/moves")
public class MoveController {

    private final MoveService moveService;

    MoveController(MoveService moveService) {
        this.moveService = moveService;
    }

    // Lade einen Move anhand der ID
    @GetMapping("/{id}")
    public ResponseEntity<MoveDTO> getMoveById(@PathVariable int id) {
        Move move = moveService.getMoveById(id);

        if (move != null) {
            return ResponseEntity.ok(MoveDTO.fromEntity(move));
        }
        return ResponseEntity.notFound().build();
    }
}
