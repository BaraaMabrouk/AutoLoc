
package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import tn.esprit.autoloc.service.IClientService;
import tn.esprit.autoloc.web.dto.ClientDTO;

import java.util.List;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
@Tag(name = "Clients", description = "Gestion des clients")
public class ClientController {

    private final IClientService clientService;

    // GET ALL
    @Operation(summary = "Afficher tous les clients")
    @ApiResponse(responseCode = "200", description = "Liste des clients récupérée")
    @GetMapping
    public List<ClientDTO> findAll() {
        return clientService.findAll();
    }

    // GET BY ID
    @Operation(summary = "Rechercher un client par son identifiant")
    @ApiResponse(responseCode = "200", description = "Client trouvé")
    @ApiResponse(responseCode = "404", description = "Client introuvable")
    @GetMapping("/{id}")
    public ClientDTO findById(
            @Parameter(description = "Identifiant du client", required = true)
            @PathVariable Long id) {

        return clientService.findById(id);
    }

    // POST
    @Operation(summary = "Créer un client")
    @ApiResponse(responseCode = "201", description = "Client créé")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientDTO create(
            @Valid @RequestBody ClientDTO dto) {

        return clientService.create(dto);
    }

    // PUT
    @Operation(summary = "Modifier un client")
    @ApiResponse(responseCode = "200", description = "Client modifié")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @ApiResponse(responseCode = "404", description = "Client introuvable")
    @PutMapping("/{id}")
    public ClientDTO update(
            @Parameter(description = "Identifiant du client", required = true)
            @PathVariable Long id,
            @Valid @RequestBody ClientDTO dto) {

        return clientService.update(id, dto);
    }

    // DELETE
    @Operation(summary = "Supprimer un client")
    @ApiResponse(responseCode = "204", description = "Client supprimé")
    @ApiResponse(responseCode = "404", description = "Client introuvable")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(
            @Parameter(description = "Identifiant du client", required = true)
            @PathVariable Long id) {

        clientService.deleteById(id);
    }
}
