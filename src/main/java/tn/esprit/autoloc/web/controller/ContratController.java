
package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import tn.esprit.autoloc.service.IContratService;
import tn.esprit.autoloc.web.dto.ContratDTO;
import tn.esprit.autoloc.web.dto.ContratRechercheDTO;

import java.util.List;

@RestController
@RequestMapping("/contrats")
@RequiredArgsConstructor
@Tag(
        name = "Contrats",
        description = "Gestion des contrats de location"
)
public class ContratController {

    private final IContratService contratService;

    // GET ALL
    @Operation(summary = "Afficher tous les contrats")
    @ApiResponse(
            responseCode = "200",
            description = "Liste des contrats récupérée"
    )
    @GetMapping
    public List<ContratDTO> findAll() {

        return contratService.findAll();
    }

    // GET BY ID
    @Operation(summary = "Rechercher un contrat par son identifiant")
    @ApiResponse(responseCode = "200", description = "Contrat trouvé")
    @ApiResponse(responseCode = "404", description = "Contrat introuvable")
    @GetMapping("/{id}")
    public ContratDTO findById(
            @Parameter(
                    description = "Identifiant du contrat",
                    required = true
            )
            @PathVariable Long id) {

        return contratService.findById(id);
    }

    // POST
    @Operation(summary = "Créer un nouveau contrat")
    @ApiResponse(responseCode = "201", description = "Contrat créé")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContratDTO create(
            @Valid @RequestBody ContratDTO dto) {

        return contratService.create(dto);
    }

    // PUT
    @Operation(summary = "Modifier un contrat")
    @ApiResponse(responseCode = "200", description = "Contrat modifié")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @ApiResponse(responseCode = "404", description = "Contrat introuvable")
    @PutMapping("/{id}")
    public ContratDTO update(
            @Parameter(
                    description = "Identifiant du contrat",
                    required = true
            )
            @PathVariable Long id,
            @Valid @RequestBody ContratDTO dto) {

        return contratService.update(id, dto);
    }

    // DELETE
    @Operation(summary = "Supprimer un contrat")
    @ApiResponse(responseCode = "204", description = "Contrat supprimé")
    @ApiResponse(responseCode = "404", description = "Contrat introuvable")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(
            @Parameter(
                    description = "Identifiant du contrat",
                    required = true
            )
            @PathVariable Long id) {

        contratService.deleteById(id);
    }


    @GetMapping("/vehicule/{keyword}")
    public List<ContratRechercheDTO> getContratsByVehicule(
            @PathVariable Long keyword) {

        return contratService.findByVehiculeId(keyword);
    }

}
