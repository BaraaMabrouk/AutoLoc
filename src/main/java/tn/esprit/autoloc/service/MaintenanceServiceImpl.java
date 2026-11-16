package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.MaintenanceMapper;
import tn.esprit.autoloc.repository.IMaintenanceRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.web.dto.MaintenanceDTO;
import tn.esprit.autoloc.web.dto.AffectationMaintenanceDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MaintenanceServiceImpl implements IMaintenanceService {

    private final IMaintenanceRepository maintenanceRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final MaintenanceMapper maintenanceMapper;

    private void valider(Maintenance maintenance) {
        if (maintenance.getDateDebut() != null
                && maintenance.getDateFin() != null
                && maintenance.getDateFin().isBefore(maintenance.getDateDebut())) {

            throw new IllegalArgumentException(
                    "La date de fin ne peut pas précéder la date de début");
        }
    }

    @Override
    public MaintenanceDTO create(MaintenanceDTO dto) {

        if (dto.idMaintenance() != null) {
            throw new IllegalArgumentException(
                    "L'identifiant doit être nul");
        }

        Maintenance maintenance = maintenanceMapper.toEntity(dto);

        valider(maintenance);

        if (dto.idVehicule() != null) {
            Vehicule vehicule = vehiculeRepository.findById(dto.idVehicule())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Vehicule", dto.idVehicule()));

            maintenance.setVehicule(vehicule);
        }

        return maintenanceMapper.toDTO(
                maintenanceRepository.save(maintenance));
    }

    @Override
    @Transactional(readOnly = true)
    public MaintenanceDTO findById(Long id) {

        Maintenance maintenance = maintenanceRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Maintenance", id));

        return maintenanceMapper.toDTO(maintenance);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaintenanceDTO> findAll() {

        return maintenanceRepository.findAll()
                .stream()
                .map(maintenanceMapper::toDTO)
                .toList();
    }

    @Override
    public MaintenanceDTO update(Long id, MaintenanceDTO dto) {

        Maintenance existante = maintenanceRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Maintenance", id));

        existante.setDateDebut(dto.dateDebut());
        existante.setDateFin(dto.dateFin());
        existante.setDescription(dto.description());

        valider(existante);

        if (dto.idVehicule() != null) {
            Vehicule vehicule = vehiculeRepository.findById(dto.idVehicule())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Vehicule", dto.idVehicule()));

            existante.setVehicule(vehicule);
        }

        return maintenanceMapper.toDTO(
                maintenanceRepository.save(existante));
    }

    @Override
    public void deleteById(Long id) {

        if (!maintenanceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Maintenance", id);
        }

        maintenanceRepository.deleteById(id);
    }

    @Override
    public MaintenanceDTO affecterMaintenanceVehicule(
            Long idMaintenance,
            AffectationMaintenanceDTO dto) {

        // 1. Rechercher la maintenance
        Maintenance maintenance = maintenanceRepository
                .findById(idMaintenance)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Maintenance", idMaintenance
                        )
                );

        // 2. Rechercher le véhicule
        Vehicule vehicule = vehiculeRepository
                .findById(dto.idVehicule())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Vehicule", dto.idVehicule()
                        )
                );

        // 3. Affecter le véhicule à la maintenance
        maintenance.setVehicule(vehicule);

        // 4. Enregistrer l'affectation
        Maintenance maintenanceModifiee =
                maintenanceRepository.save(maintenance);

        // 5. Retourner un DTO
        return maintenanceMapper.toDTO(maintenanceModifiee);
    }

}