
package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import tn.esprit.autoloc.service.IReservationService;
import tn.esprit.autoloc.web.dto.ReservationDTO;

import java.util.List;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
@Tag(
        name = "Réservations",
        description = "Gestion des réservations de véhicules"
)
public class ReservationController {

    private final IReservationService reservationService;

    // GET ALL
    @Operation(summary = "Afficher toutes les réservations")
    @ApiResponse(
            responseCode = "200",
            description = "Liste des réservations récupérée"
    )
    @GetMapping
    public List<ReservationDTO> findAll() {

        return reservationService.findAll();
    }

    // GET BY ID
    @Operation(summary = "Rechercher une réservation par son identifiant")
    @ApiResponse(responseCode = "200", description = "Réservation trouvée")
    @ApiResponse(responseCode = "404", description = "Réservation introuvable")
    @GetMapping("/{id}")
    public ReservationDTO findById(
            @Parameter(
                    description = "Identifiant de la réservation",
                    required = true
            )
            @PathVariable Long id) {

        return reservationService.findById(id);
    }

    // POST
    @Operation(summary = "Créer une réservation")
    @ApiResponse(responseCode = "201", description = "Réservation créée")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationDTO create(
            @Valid @RequestBody ReservationDTO dto) {

        return reservationService.create(dto);
    }

    // PUT
    @Operation(summary = "Modifier une réservation")
    @ApiResponse(responseCode = "200", description = "Réservation modifiée")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @ApiResponse(responseCode = "404", description = "Réservation introuvable")
    @PutMapping("/{id}")
    public ReservationDTO update(
            @Parameter(
                    description = "Identifiant de la réservation",
                    required = true
            )
            @PathVariable Long id,
            @Valid @RequestBody ReservationDTO dto) {

        return reservationService.update(id, dto);
    }

    // DELETE
    @Operation(summary = "Supprimer une réservation")
    @ApiResponse(responseCode = "204", description = "Réservation supprimée")
    @ApiResponse(responseCode = "404", description = "Réservation introuvable")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(
            @Parameter(
                    description = "Identifiant de la réservation",
                    required = true
            )
            @PathVariable Long id) {

        reservationService.deleteById(id);
    }
}
