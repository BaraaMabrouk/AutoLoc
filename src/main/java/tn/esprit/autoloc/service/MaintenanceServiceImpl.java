
package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.IMapper;
import tn.esprit.autoloc.repository.IMaintenanceRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.web.dto.MaintenanceDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MaintenanceServiceImpl implements IMaintenanceService {

    private static final String MAINTENANCE = "Maintenance";
    private static final String VEHICULE = "Vehicule";

    private final IMaintenanceRepository maintenanceRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final IMapper mapper;

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

        Maintenance maintenance = mapper.toEntity(dto);

        valider(maintenance);

        if (dto.idVehicule() != null) {
            Vehicule vehicule = vehiculeRepository.findById(dto.idVehicule())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            VEHICULE, dto.idVehicule()));

            maintenance.setVehicule(vehicule);
        }

        return mapper.toDTO(
                maintenanceRepository.save(maintenance));
    }

    @Override
    @Transactional(readOnly = true)
    public MaintenanceDTO findById(Long id) {

        Maintenance maintenance = maintenanceRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(MAINTENANCE, id));

        return mapper.toDTO(maintenance);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaintenanceDTO> findAll() {

        return maintenanceRepository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public MaintenanceDTO update(Long id, MaintenanceDTO dto) {

        Maintenance existante = maintenanceRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(MAINTENANCE, id));

        existante.setDateDebut(dto.dateDebut());
        existante.setDateFin(dto.dateFin());
        existante.setDescription(dto.description());

        valider(existante);

        if (dto.idVehicule() != null) {
            Vehicule vehicule = vehiculeRepository.findById(dto.idVehicule())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            VEHICULE, dto.idVehicule()));

            existante.setVehicule(vehicule);
        }

        return mapper.toDTO(
                maintenanceRepository.save(existante));
    }

    @Override
    public void deleteById(Long id) {

        if (!maintenanceRepository.existsById(id)) {
            throw new ResourceNotFoundException(MAINTENANCE, id);
        }

        maintenanceRepository.deleteById(id);
    }

    @Override
    public MaintenanceDTO affecterMaintenanceVehicule(
            Long idMaintenance,
            Long idVehicule) {

        Maintenance maintenance = maintenanceRepository
                .findById(idMaintenance)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                MAINTENANCE, idMaintenance));

        Vehicule vehicule = vehiculeRepository
                .findById(idVehicule)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                VEHICULE, idVehicule));

        maintenance.setVehicule(vehicule);

        Maintenance maintenanceModifiee =
                maintenanceRepository.save(maintenance);

        return mapper.toDTO(maintenanceModifiee);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaintenanceDTO> findByVehiculeId(Long keyword) {

        return maintenanceRepository.findByVehiculeIdVehicule(keyword)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }
}
