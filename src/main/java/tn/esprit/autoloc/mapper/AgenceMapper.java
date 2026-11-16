package tn.esprit.autoloc.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.web.dto.AgenceDTO;

import java.util.stream.Collectors;

@Component
public class AgenceMapper {

    // Entity → DTO
    public AgenceDTO toDTO(Agence agence) {

        return new AgenceDTO(
                agence.getIdAgence(),
                agence.getNom(),
                agence.getVille(),
                agence.getAdresse(),
                agence.getTelephone(),

                agence.getVehicules().stream()
                        .map(vehicule -> vehicule.getIdVehicule())
                        .collect(Collectors.toList()),

                agence.getEmployes().stream()
                        .map(employe -> employe.getIdEmploye())
                        .collect(Collectors.toList())
        );
    }

    // DTO → Entity
    public Agence toEntity(AgenceDTO dto) {

        Agence agence = new Agence();

        agence.setNom(dto.nom());
        agence.setVille(dto.ville());
        agence.setAdresse(dto.adresse());
        agence.setTelephone(dto.telephone());

        return agence;
    }
}