
package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import tn.esprit.autoloc.service.IPaiementService;
import tn.esprit.autoloc.web.dto.PaiementDTO;

import java.util.List;

@RestController
@RequestMapping("/paiements")
@RequiredArgsConstructor
@Tag(
        name = "Paiements",
        description = "Consultation des paiements des contrats"
)
public class PaiementController {

    private final IPaiementService paiementService;

    // GET ALL
    @Operation(summary = "Afficher tous les paiements")
    @ApiResponse(
            responseCode = "200",
            description = "Liste des paiements récupérée"
    )
    @GetMapping
    public List<PaiementDTO> findAll() {

        return paiementService.findAll();
    }

    // GET BY ID
    @Operation(summary = "Rechercher un paiement par son identifiant")
    @ApiResponse(responseCode = "200", description = "Paiement trouvé")
    @ApiResponse(responseCode = "404", description = "Paiement introuvable")
    @GetMapping("/{id}")
    public PaiementDTO findById(
            @Parameter(
                    description = "Identifiant du paiement",
                    required = true
            )
            @PathVariable Long id) {

        return paiementService.findById(id);
    }
}
