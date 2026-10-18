
package tn.esprit.autoloc.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.service.IAgenceService;

import java.util.List;

@RestController
@RequestMapping("/api/agences")
@RequiredArgsConstructor
public class AgenceController {

    private final IAgenceService agenceService;

    // 1. CREATE - Ajouter une agence
    @PostMapping
    public ResponseEntity<Agence> create(@RequestBody Agence agence) {
        Agence nouvelleAgence = agenceService.create(agence);
        return ResponseEntity.status(HttpStatus.CREATED).body(nouvelleAgence);
    }

    // 2. GET ALL - Afficher toutes les agences
    @GetMapping
    public List<Agence> findAll() {
        return agenceService.findAll();
    }

    // 3. GET BY ID - Afficher une agence par ID
    @GetMapping("/{id}")
    public Agence findById(@PathVariable Long id) {
        return agenceService.findById(id);
    }

    // 4. UPDATE - Modifier une agence
    @PutMapping("/{id}")
    public Agence update(@PathVariable Long id, @RequestBody Agence agence) {
        return agenceService.update(id, agence);
    }

    // 5. DELETE - Supprimer une agence
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        agenceService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
