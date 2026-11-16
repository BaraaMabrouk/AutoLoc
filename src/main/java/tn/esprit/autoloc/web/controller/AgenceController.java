
package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tn.esprit.autoloc.service.IAgenceService;
import tn.esprit.autoloc.web.dto.AgenceDTO;

import java.util.List;

@RestController
@RequestMapping("/agences")
@RequiredArgsConstructor
@Tag(
        name = "Agences",
        description = "Gestion des agences de location"
)
public class AgenceController {

    private final IAgenceService agenceService;

    @Operation(summary = "Afficher toutes les agences")
    @ApiResponse(
            responseCode = "200",
            description = "Liste des agences récupérée"
    )
    @GetMapping
    public List<AgenceDTO> findAll() {
        return agenceService.findAll();
    }

    @Operation(summary = "Rechercher une agence par son identifiant")
    @ApiResponse(responseCode = "200", description = "Agence trouvée")
    @ApiResponse(responseCode = "404", description = "Agence introuvable")
    @GetMapping("/{id}")
    public AgenceDTO findById(
            @Parameter(
                    description = "Identifiant de l'agence",
                    required = true
            )
            @PathVariable Long id) {

        return agenceService.findById(id);
    }

    @Operation(summary = "Créer une nouvelle agence")
    @ApiResponse(responseCode = "201", description = "Agence créée")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PostMapping
    public ResponseEntity<AgenceDTO> create(
            @Valid @RequestBody AgenceDTO agenceDTO) {

        AgenceDTO nouvelleAgence = agenceService.create(agenceDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(nouvelleAgence);
    }

    @Operation(summary = "Modifier une agence")
    @ApiResponse(responseCode = "200", description = "Agence modifiée")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @ApiResponse(responseCode = "404", description = "Agence introuvable")
    @PutMapping("/{id}")
    public AgenceDTO update(
            @Parameter(
                    description = "Identifiant de l'agence",
                    required = true
            )
            @PathVariable Long id,
            @Valid @RequestBody AgenceDTO agenceDTO) {

        return agenceService.update(id, agenceDTO);
    }

    @Operation(summary = "Supprimer une agence")
    @ApiResponse(responseCode = "204", description = "Agence supprimée")
    @ApiResponse(responseCode = "404", description = "Agence introuvable")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @Parameter(
                    description = "Identifiant de l'agence",
                    required = true
            )
            @PathVariable Long id) {

        agenceService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
