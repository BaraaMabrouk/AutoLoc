
package tn.esprit.autoloc.service;

import tn.esprit.autoloc.web.dto.MaintenanceDTO;
import java.util.List;

public interface IMaintenanceService {

    List<MaintenanceDTO> findByVehiculeId(Long keyword);

    MaintenanceDTO create(MaintenanceDTO dto);

    MaintenanceDTO findById(Long id);

    List<MaintenanceDTO> findAll();

    MaintenanceDTO update(Long id, MaintenanceDTO dto);

    void deleteById(Long id);

    // Affecter une maintenance à un véhicule
    MaintenanceDTO affecterMaintenanceVehicule(
            Long idMaintenance,
            Long idVehicule
    );
}
