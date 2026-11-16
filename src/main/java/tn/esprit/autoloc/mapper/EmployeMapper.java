package tn.esprit.autoloc.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.web.dto.EmployeDTO;

@Component
public class EmployeMapper {

    // Entity → DTO
    public EmployeDTO toDTO(Employe employe) {

        return new EmployeDTO(
                employe.getIdEmploye(),
                employe.getNom(),
                employe.getPrenom(),
                employe.getRole(),
                employe.getAgence() != null
                        ? employe.getAgence().getIdAgence()
                        : null
        );
    }

    // DTO → Entity
    public Employe toEntity(EmployeDTO dto) {

        Employe employe = new Employe();

        employe.setNom(dto.nom());
        employe.setPrenom(dto.prenom());
        employe.setRole(dto.role());

        return employe;
    }
}