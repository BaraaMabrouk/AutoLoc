
package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.IMapper;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.repository.IEquipementRepository;
import tn.esprit.autoloc.web.dto.VehiculeDTO;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class VehiculeServiceImpl implements IVehiculeService {

    private static final String VEHICULE = "Vehicule";
    private static final String AGENCE = "Agence";

    private final IVehiculeRepository vehiculeRepository;
    private final IAgenceRepository agenceRepository;
    private final IMapper mapper;
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

        Vehicule vehicule = mapper.toEntity(dto);

        valider(vehicule);

        if (dto.idAgence() != null) {

            Agence agence = agenceRepository.findById(dto.idAgence())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    AGENCE, dto.idAgence()
                            )
                    );

            vehicule.setAgence(agence);
        }

        Vehicule nouveauVehicule = vehiculeRepository.save(vehicule);

        return mapper.toDTO(nouveauVehicule);
    }

    // GET BY ID
    @Override
    @Transactional(readOnly = true)
    public VehiculeDTO findById(Long id) {

        Vehicule vehicule = vehiculeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(VEHICULE, id)
                );

        return mapper.toDTO(vehicule);
    }

    // GET ALL
    @Override
    @Transactional(readOnly = true)
    public List<VehiculeDTO> findAll() {

        return vehiculeRepository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    // UPDATE
    @Override
    public VehiculeDTO update(Long id, VehiculeDTO dto) {

        Vehicule existant = vehiculeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(VEHICULE, id)
                );

        Vehicule donnees = mapper.toEntity(dto);

        valider(donnees);

        existant.setImmatriculation(donnees.getImmatriculation());
        existant.setMarque(donnees.getMarque());
        existant.setModele(donnees.getModele());
        existant.setCategorie(donnees.getCategorie());
        existant.setTarifJournalier(donnees.getTarifJournalier());
        existant.setStatut(donnees.getStatut());

        if (dto.idAgence() != null) {

            Agence agence = agenceRepository.findById(dto.idAgence())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    AGENCE, dto.idAgence()
                            )
                    );

            existant.setAgence(agence);
        }

        Vehicule vehiculeModifie = vehiculeRepository.save(existant);

        return mapper.toDTO(vehiculeModifie);
    }

    // DELETE
    @Override
    public void deleteById(Long id) {

        if (!vehiculeRepository.existsById(id)) {
            throw new ResourceNotFoundException(VEHICULE, id);
        }

        vehiculeRepository.deleteById(id);
    }

    // AFFECTATION VEHICULE -> AGENCE
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
                                AGENCE, idAgence
                        )
                );

        vehicule.setAgence(agence);

        Vehicule vehiculeModifie = vehiculeRepository.save(vehicule);

        return mapper.toDTO(vehiculeModifie);
    }

    // AFFECTATION VEHICULE -> EQUIPEMENT
    @Override
    public VehiculeDTO affecterVehiculeEquipement(
            Long idVehicule,
            Long idEquipement) {

        Vehicule vehicule = vehiculeRepository.findById(idVehicule)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                VEHICULE, idVehicule
                        )
                );

        Equipement equipement = equipementRepository
                .findById(idEquipement)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Equipement", idEquipement
                        )
                );

        vehicule.getEquipements().add(equipement);

        Vehicule vehiculeModifie =
                vehiculeRepository.save(vehicule);

        return mapper.toDTO(vehiculeModifie);
    }

    // ATELIER 7 : VEHICULES PAR ID CONTRAT
    @Override
    @Transactional(readOnly = true)
    public List<VehiculeDTO> findByContratId(Long keyword) {

        return vehiculeRepository
                .findByReservationsContratIdContrat(keyword);
    }

    // ATELIER 7 : VEHICULES PAR ID MAINTENANCE
    @Override
    @Transactional(readOnly = true)
    public List<VehiculeDTO> findByMaintenanceId(Long keyword) {

        return vehiculeRepository
                .findByMaintenancesIdMaintenance(keyword);
    }
}
