
package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import tn.esprit.autoloc.web.dto.AffectationEquipementDTO;
import tn.esprit.autoloc.web.dto.AffectationEquipementResponseDTO;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import tn.esprit.autoloc.service.IVehiculeService;
import tn.esprit.autoloc.web.dto.VehiculeDTO;

import java.util.List;

@RestController
@RequestMapping("/vehicules")
@RequiredArgsConstructor
@Tag(
        name = "Véhicules",
        description = "Gestion des véhicules de location"
)
public class VehiculeController {

    private final IVehiculeService vehiculeService;

    // GET ALL
    @Operation(summary = "Afficher tous les véhicules")
    @ApiResponse(responseCode = "200", description = "Liste des véhicules récupérée")
    @GetMapping
    public List<VehiculeDTO> findAll() {
        return vehiculeService.findAll();
    }

    // GET BY ID
    @Operation(summary = "Rechercher un véhicule par son identifiant")
    @ApiResponse(responseCode = "200", description = "Véhicule trouvé")
    @ApiResponse(responseCode = "404", description = "Véhicule introuvable")
    @GetMapping("/{id}")
    public VehiculeDTO findById(
            @Parameter(description = "Identifiant du véhicule", required = true)
            @PathVariable Long id) {

        return vehiculeService.findById(id);
    }

    // POST
    @Operation(summary = "Ajouter un véhicule")
    @ApiResponse(responseCode = "201", description = "Véhicule créé")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VehiculeDTO create(
            @Valid @RequestBody VehiculeDTO vehiculeDTO) {

        return vehiculeService.create(vehiculeDTO);
    }

    // PUT
    @Operation(summary = "Modifier un véhicule")
    @ApiResponse(responseCode = "200", description = "Véhicule modifié")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @ApiResponse(responseCode = "404", description = "Véhicule introuvable")
    @PutMapping("/{id}")
    public VehiculeDTO update(
            @Parameter(description = "Identifiant du véhicule", required = true)
            @PathVariable Long id,
            @Valid @RequestBody VehiculeDTO vehiculeDTO) {

        return vehiculeService.update(id, vehiculeDTO);
    }

    // DELETE
    @Operation(summary = "Supprimer un véhicule")
    @ApiResponse(responseCode = "204", description = "Véhicule supprimé")
    @ApiResponse(responseCode = "404", description = "Véhicule introuvable")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(
            @Parameter(description = "Identifiant du véhicule", required = true)
            @PathVariable Long id) {

        vehiculeService.deleteById(id);
    }

    // AFFECTATION VEHICULE → AGENCE
    @Operation(summary = "Affecter un véhicule à une agence")
    @ApiResponse(responseCode = "200", description = "Véhicule affecté à l'agence")
    @ApiResponse(responseCode = "404", description = "Véhicule ou agence introuvable")
    @PutMapping("/{idVehicule}/agence/{idAgence}")
    public VehiculeDTO affecterVehiculeAgence(
            @PathVariable Long idVehicule,
            @PathVariable Long idAgence) {

        return vehiculeService.affecterVehiculeAgence(
                idVehicule,
                idAgence
        );
    }

    @Operation(
            summary = "Affecter un équipement à un véhicule"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Équipement affecté au véhicule"
    )
    @ApiResponse(
            responseCode = "400",
            description = "Identifiant de l'équipement invalide"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Véhicule ou équipement introuvable"
    )
    @PutMapping("/{idVehicule}/equipements")
    public AffectationEquipementResponseDTO affecterVehiculeEquipement(
            @PathVariable Long idVehicule,
            @Valid @RequestBody AffectationEquipementDTO dto) {

        return vehiculeService.affecterVehiculeEquipement(
                idVehicule,
                dto
        );
    }

}
