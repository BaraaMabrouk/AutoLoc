
package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import tn.esprit.autoloc.service.IMaintenanceService;
import tn.esprit.autoloc.web.dto.MaintenanceDTO;

import java.util.List;

@RestController
@RequestMapping("/maintenances")
@RequiredArgsConstructor
@Validated
@Tag(name = "Maintenances", description = "Gestion des opérations de maintenance")
public class MaintenanceController {

    private final IMaintenanceService maintenanceService;

    @Operation(summary = "Afficher toutes les maintenances")
    @ApiResponse(responseCode = "200", description = "Liste des maintenances récupérée")
    @GetMapping
    public List<MaintenanceDTO> findAll() {
        return maintenanceService.findAll();
    }

    @Operation(summary = "Rechercher une maintenance par son identifiant")
    @ApiResponse(responseCode = "200", description = "Maintenance trouvée")
    @ApiResponse(responseCode = "404", description = "Maintenance introuvable")
    @GetMapping("/{id}")
    public MaintenanceDTO findById(
            @Parameter(description = "Identifiant de la maintenance", required = true)
            @PathVariable Long id) {

        return maintenanceService.findById(id);
    }

    @Operation(summary = "Créer une maintenance")
    @ApiResponse(responseCode = "201", description = "Maintenance créée")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MaintenanceDTO create(
            @Valid @RequestBody MaintenanceDTO dto) {

        return maintenanceService.create(dto);
    }

    @Operation(summary = "Modifier une maintenance")
    @ApiResponse(responseCode = "200", description = "Maintenance modifiée")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @ApiResponse(responseCode = "404", description = "Maintenance introuvable")
    @PutMapping("/{id}")
    public MaintenanceDTO update(
            @Parameter(description = "Identifiant de la maintenance", required = true)
            @PathVariable Long id,
            @Valid @RequestBody MaintenanceDTO dto) {

        return maintenanceService.update(id, dto);
    }

    @Operation(summary = "Supprimer une maintenance")
    @ApiResponse(responseCode = "204", description = "Maintenance supprimée")
    @ApiResponse(responseCode = "404", description = "Maintenance introuvable")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(
            @Parameter(description = "Identifiant de la maintenance", required = true)
            @PathVariable Long id) {

        maintenanceService.deleteById(id);
    }


    @Operation(summary = "Affecter une maintenance à un véhicule")
    @ApiResponse(responseCode = "200", description = "Maintenance affectée au véhicule")
    @ApiResponse(responseCode = "400", description = "Identifiant invalide")
    @ApiResponse(responseCode = "404", description = "Maintenance ou véhicule introuvable")
    @PostMapping("/{idMaintenance}/vehicule/{idVehicule}")
    public MaintenanceDTO affecterMaintenanceVehicule(
            @PathVariable @Positive(message = "L'identifiant de la maintenance doit être positif")
            Long idMaintenance,
            @PathVariable @Positive(message = "L'identifiant du véhicule doit être positif")
            Long idVehicule) {

        return maintenanceService.affecterMaintenanceVehicule(
                idMaintenance,
                idVehicule
        );
    }


    @GetMapping("/vehicule/{keyword}")
    public List<MaintenanceDTO> getMaintenancesByVehicule(
            @PathVariable Long keyword) {

        return maintenanceService.findByVehiculeId(keyword);
    }


}
