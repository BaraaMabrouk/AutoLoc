
package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import tn.esprit.autoloc.service.IEmployeService;
import tn.esprit.autoloc.web.dto.EmployeDTO;

import java.util.List;

@RestController
@RequestMapping("/employes")
@RequiredArgsConstructor
@Tag(
        name = "Employés",
        description = "Gestion des employés des agences"
)
public class EmployeController {

    private final IEmployeService employeService;

    // GET ALL
    @Operation(summary = "Afficher tous les employés")
    @ApiResponse(
            responseCode = "200",
            description = "Liste des employés récupérée"
    )
    @GetMapping
    public List<EmployeDTO> findAll() {

        return employeService.findAll();
    }

    // GET BY ID
    @Operation(summary = "Rechercher un employé par son identifiant")
    @ApiResponse(responseCode = "200", description = "Employé trouvé")
    @ApiResponse(responseCode = "404", description = "Employé introuvable")
    @GetMapping("/{id}")
    public EmployeDTO findById(
            @Parameter(
                    description = "Identifiant de l'employé",
                    required = true
            )
            @PathVariable Long id) {

        return employeService.findById(id);
    }

    // POST
    @Operation(summary = "Créer un nouvel employé")
    @ApiResponse(responseCode = "201", description = "Employé créé")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeDTO create(
            @Valid @RequestBody EmployeDTO dto) {

        return employeService.create(dto);
    }

    // PUT
    @Operation(summary = "Modifier un employé")
    @ApiResponse(responseCode = "200", description = "Employé modifié")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @ApiResponse(responseCode = "404", description = "Employé introuvable")
    @PutMapping("/{id}")
    public EmployeDTO update(
            @Parameter(
                    description = "Identifiant de l'employé",
                    required = true
            )
            @PathVariable Long id,
            @Valid @RequestBody EmployeDTO dto) {

        return employeService.update(id, dto);
    }

    // DELETE
    @Operation(summary = "Supprimer un employé")
    @ApiResponse(responseCode = "204", description = "Employé supprimé")
    @ApiResponse(responseCode = "404", description = "Employé introuvable")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(
            @Parameter(
                    description = "Identifiant de l'employé",
                    required = true
            )
            @PathVariable Long id) {

        employeService.deleteById(id);
    }
}
