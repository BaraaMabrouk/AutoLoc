
package tn.esprit.autoloc.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import tn.esprit.autoloc.exception.ResourceNotFoundException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404 : Ressource introuvable
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(
            ResourceNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                        "statut", 404,
                        "message", ex.getMessage(),
                        "horodatage", LocalDateTime.now(
                                ZoneId.of("Africa/Tunis")
                        ).toString()
                )
        );
    }

    // 400 : Argument invalide
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequest(
            IllegalArgumentException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Map.of(
                        "statut", 400,
                        "message", ex.getMessage(),
                        "horodatage", LocalDateTime.now(
                                ZoneId.of("Africa/Tunis")
                        ).toString()
                )
        );
    }

    // 400 : Erreur de validation DTO (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(
            MethodArgumentNotValidException ex) {

        Map<String, String> erreurs = new LinkedHashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                erreurs.put(
                        error.getField(),
                        error.getDefaultMessage()
                )
        );

        ex.getBindingResult().getGlobalErrors().forEach(error ->
                erreurs.put(
                        error.getObjectName(),
                        error.getDefaultMessage()
                )
        );

        Map<String, Object> reponse = new LinkedHashMap<>();

        reponse.put("statut", 400);
        reponse.put("message", "Erreur de validation des données");
        reponse.put("erreurs", erreurs);
        reponse.put(
                "horodatage",
                LocalDateTime.now(ZoneId.of("Africa/Tunis")).toString()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(reponse);
    }
}
