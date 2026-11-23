
package tn.esprit.autoloc.service;

import tn.esprit.autoloc.web.dto.VehiculeDTO;
import java.util.List;

public interface IVehiculeService {

    List<VehiculeDTO> findByContratId(Long keyword);

    List<VehiculeDTO> findByMaintenanceId(Long keyword);

    VehiculeDTO create(VehiculeDTO dto);

    VehiculeDTO findById(Long id);

    List<VehiculeDTO> findAll();

    VehiculeDTO update(Long id, VehiculeDTO dto);

    void deleteById(Long id);

    // Affectation Véhicule -> Equipement
    VehiculeDTO affecterVehiculeEquipement(
            Long idVehicule,
            Long idEquipement
    );











    // Affectation Véhicule -> Agence
    VehiculeDTO affecterVehiculeAgence(
            Long idVehicule,
            Long idAgence
    );
}
