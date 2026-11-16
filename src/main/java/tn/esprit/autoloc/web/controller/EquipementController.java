
package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import tn.esprit.autoloc.service.IEquipementService;
import tn.esprit.autoloc.web.dto.EquipementDTO;

import java.util.List;

@RestController
@RequestMapping("/equipements")
@RequiredArgsConstructor
@Tag(
        name = "Equipements",
        description = "Gestion des équipements des véhicules"
)
public class EquipementController {

    private final IEquipementService equipementService;

    // GET ALL
    @Operation(summary = "Afficher tous les équipements")
    @ApiResponse(responseCode = "200", description = "Liste des équipements récupérée")
    @GetMapping
    public List<EquipementDTO> findAll() {
        return equipementService.findAll();
    }

    // GET BY ID
    @Operation(summary = "Rechercher un équipement par son identifiant")
    @ApiResponse(responseCode = "200", description = "Équipement trouvé")
    @ApiResponse(responseCode = "404", description = "Équipement introuvable")
    @GetMapping("/{id}")
    public EquipementDTO findById(
            @Parameter(description = "Identifiant de l'équipement", required = true)
            @PathVariable Long id) {

        return equipementService.findById(id);
    }

    // POST
    @Operation(summary = "Ajouter un équipement")
    @ApiResponse(responseCode = "201", description = "Équipement créé")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EquipementDTO create(
            @Valid @RequestBody EquipementDTO dto) {

        return equipementService.create(dto);
    }

    // PUT
    @Operation(summary = "Modifier un équipement")
    @ApiResponse(responseCode = "200", description = "Équipement modifié")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @ApiResponse(responseCode = "404", description = "Équipement introuvable")
    @PutMapping("/{id}")
    public EquipementDTO update(
            @Parameter(description = "Identifiant de l'équipement", required = true)
            @PathVariable Long id,
            @Valid @RequestBody EquipementDTO dto) {

        return equipementService.update(id, dto);
    }

    // DELETE
    @Operation(summary = "Supprimer un équipement")
    @ApiResponse(responseCode = "204", description = "Équipement supprimé")
    @ApiResponse(responseCode = "404", description = "Équipement introuvable")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(
            @Parameter(description = "Identifiant de l'équipement", required = true)
            @PathVariable Long id) {

        equipementService.deleteById(id);
    }
}
