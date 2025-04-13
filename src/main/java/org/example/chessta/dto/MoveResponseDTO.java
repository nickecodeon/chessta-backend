package org.example.chessta.dto;

import java.util.List;

public class MoveResponseDTO {
    private boolean success;
    private String message;
    private List<FigureDTO> updatedFigures;

    // --- Konstruktoren

    public MoveResponseDTO() {
    }

    public MoveResponseDTO(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public MoveResponseDTO(boolean success, String message, List<FigureDTO> updatedFigures) {
        this.success = success;
        this.message = message;
        this.updatedFigures = updatedFigures;
    }

    // --- Getter & Setter ---

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<FigureDTO> getUpdatedFigures() {
        return updatedFigures;
    }

    public void setUpdatedFigures(List<FigureDTO> updatedFigures) {
        this.updatedFigures = updatedFigures;
    }
}
