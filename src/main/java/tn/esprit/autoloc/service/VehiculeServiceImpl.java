
package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.VehiculeMapper;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.web.dto.VehiculeDTO;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.repository.IEquipementRepository;
import tn.esprit.autoloc.web.dto.AffectationEquipementDTO;
import tn.esprit.autoloc.web.dto.AffectationEquipementResponseDTO;
import java.util.stream.Collectors;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class VehiculeServiceImpl implements IVehiculeService {

    private final IVehiculeRepository vehiculeRepository;
    private final IAgenceRepository agenceRepository;
    private final VehiculeMapper vehiculeMapper;
    private final IEquipementRepository equipementRepository;

    // Validation métier existante
    private void valider(Vehicule vehicule) {

        if (vehicule.getTarifJournalier() != null
                && vehicule.getTarifJournalier().compareTo(BigDecimal.ZERO) < 0) {

            throw new IllegalArgumentException(
                    "Le tarif journalier ne peut pas être négatif"
            );
        }
    }

    // CREATE
    @Override
    public VehiculeDTO create(VehiculeDTO dto) {

        if (dto.idVehicule() != null) {
            throw new IllegalArgumentException(
                    "L'identifiant doit être nul lors de la création"
            );
        }

        Vehicule vehicule = vehiculeMapper.toEntity(dto);

        valider(vehicule);

        // Associer une agence si son identifiant est fourni
        if (dto.idAgence() != null) {

            Agence agence = agenceRepository.findById(dto.idAgence())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Agence", dto.idAgence()
                            )
                    );

            vehicule.setAgence(agence);
        }

        Vehicule nouveauVehicule = vehiculeRepository.save(vehicule);

        return vehiculeMapper.toDTO(nouveauVehicule);
    }

    // GET BY ID
    @Override
    @Transactional(readOnly = true)
    public VehiculeDTO findById(Long id) {

        Vehicule vehicule = vehiculeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Vehicule", id)
                );

        return vehiculeMapper.toDTO(vehicule);
    }

    // GET ALL
    @Override
    @Transactional(readOnly = true)
    public List<VehiculeDTO> findAll() {

        return vehiculeRepository.findAll()
                .stream()
                .map(vehiculeMapper::toDTO)
                .toList();
    }

    // UPDATE
    @Override
    public VehiculeDTO update(Long id, VehiculeDTO dto) {

        Vehicule existant = vehiculeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Vehicule", id)
                );

        Vehicule donnees = vehiculeMapper.toEntity(dto);

        valider(donnees);

        existant.setImmatriculation(donnees.getImmatriculation());
        existant.setMarque(donnees.getMarque());
        existant.setModele(donnees.getModele());
        existant.setCategorie(donnees.getCategorie());
        existant.setTarifJournalier(donnees.getTarifJournalier());
        existant.setStatut(donnees.getStatut());

        // Mettre à jour l'agence si un identifiant est fourni
        if (dto.idAgence() != null) {

            Agence agence = agenceRepository.findById(dto.idAgence())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Agence", dto.idAgence()
                            )
                    );

            existant.setAgence(agence);
        }

        Vehicule vehiculeModifie = vehiculeRepository.save(existant);

        return vehiculeMapper.toDTO(vehiculeModifie);
    }

    // DELETE
    @Override
    public void deleteById(Long id) {

        if (!vehiculeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicule", id);
        }

        vehiculeRepository.deleteById(id);
    }

    // AFFECTATION VEHICULE → AGENCE
    @Override
    public VehiculeDTO affecterVehiculeAgence(
            Long idVehicule,
            Long idAgence) {

        Vehicule vehicule = vehiculeRepository.findById(idVehicule)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Véhicule", idVehicule
                        )
                );

        Agence agence = agenceRepository.findById(idAgence)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Agence", idAgence
                        )
                );

        vehicule.setAgence(agence);

        Vehicule vehiculeModifie = vehiculeRepository.save(vehicule);

        return vehiculeMapper.toDTO(vehiculeModifie);
    }

    @Override
    public AffectationEquipementResponseDTO affecterVehiculeEquipement(
            Long idVehicule,
            AffectationEquipementDTO dto) {

        // 1. Rechercher le véhicule
        Vehicule vehicule = vehiculeRepository.findById(idVehicule)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Vehicule", idVehicule
                        )
                );

        // 2. Rechercher l'équipement
        Equipement equipement = equipementRepository
                .findById(dto.idEquipement())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Equipement", dto.idEquipement()
                        )
                );

        // 3. Affecter l'équipement au véhicule
        vehicule.getEquipements().add(equipement);

        // 4. Enregistrer l'association ManyToMany
        Vehicule vehiculeModifie =
                vehiculeRepository.save(vehicule);

        // 5. Récupérer les identifiants des équipements
        java.util.Set<Long> idEquipements =
                vehiculeModifie.getEquipements()
                        .stream()
                        .map(Equipement::getIdEquipement)
                        .collect(Collectors.toSet());

        // 6. Retourner le DTO
        return new AffectationEquipementResponseDTO(
                vehiculeModifie.getIdVehicule(),
                idEquipements
        );
    }

}
