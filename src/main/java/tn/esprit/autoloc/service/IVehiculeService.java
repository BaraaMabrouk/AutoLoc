
package tn.esprit.autoloc.service;

import tn.esprit.autoloc.web.dto.VehiculeDTO;
import tn.esprit.autoloc.web.dto.AffectationEquipementDTO;
import tn.esprit.autoloc.web.dto.AffectationEquipementResponseDTO;

import java.util.List;

public interface IVehiculeService {

    VehiculeDTO create(VehiculeDTO dto);

    VehiculeDTO findById(Long id);

    List<VehiculeDTO> findAll();

    VehiculeDTO update(Long id, VehiculeDTO dto);

    void deleteById(Long id);

    // Affectation existante : Vehicule -> Agence
    VehiculeDTO affecterVehiculeAgence(
            Long idVehicule,
            Long idAgence
    );

    // Nouvelle affectation : Vehicule -> Equipement
    AffectationEquipementResponseDTO affecterVehiculeEquipement(
            Long idVehicule,
            AffectationEquipementDTO dto
    );
}
