package tn.esprit.autoloc.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.web.dto.EquipementDTO;

import java.util.stream.Collectors;

@Component
public class EquipementMapper {

    public EquipementDTO toDTO(Equipement equipement) {
        return new EquipementDTO(
                equipement.getIdEquipement(),
                equipement.getLibelle(),
                equipement.getVehicules().stream()
                        .map(vehicule -> vehicule.getIdVehicule())
                        .collect(Collectors.toSet())
        );
    }

    public Equipement toEntity(EquipementDTO dto) {
        Equipement equipement = new Equipement();

        equipement.setLibelle(dto.libelle());

        return equipement;
    }
}