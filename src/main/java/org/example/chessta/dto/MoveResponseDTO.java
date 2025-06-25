package org.example.chessta.dto;

import java.util.List;

public class MoveResponseDTO {
    private boolean success;
    private String message;
    private List<FigureDTO> updatedFigures;

    // --- Konstruktoren

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

    @SuppressWarnings("unused")
    public boolean isSuccess() {
        return success;
    }

    @SuppressWarnings("unused")
    public void setSuccess(boolean success) {
        this.success = success;
    }

    @SuppressWarnings("unused")
    public String getMessage() {
        return message;
    }

    @SuppressWarnings("unused")
    public void setMessage(String message) {
        this.message = message;
    }

    @SuppressWarnings("unused")
    public List<FigureDTO> getUpdatedFigures() {
        return updatedFigures;
    }

    @SuppressWarnings("unused")
    public void setUpdatedFigures(List<FigureDTO> updatedFigures) {
        this.updatedFigures = updatedFigures;
    }
}
