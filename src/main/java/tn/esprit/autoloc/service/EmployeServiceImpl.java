
package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.IMapper;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.repository.IEmployeRepository;
import tn.esprit.autoloc.web.dto.EmployeDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeServiceImpl implements IEmployeService {

    private final IEmployeRepository employeRepository;
    private final IAgenceRepository agenceRepository;
    private final IMapper mapper;

    // VALIDATION METIER EXISTANTE
    private void valider(Employe employe) {

        if (employe.getNom() == null
                || employe.getNom().isBlank()) {

            throw new IllegalArgumentException(
                    "Le nom est obligatoire"
            );
        }
    }

    // CREATE
    @Override
    public EmployeDTO create(EmployeDTO dto) {

        if (dto.idEmploye() != null) {
            throw new IllegalArgumentException(
                    "L'identifiant doit être nul"
            );
        }

        Employe employe = mapper.toEntity(dto);

        valider(employe);

        // Association Employe -> Agence
        Agence agence = agenceRepository.findById(dto.idAgence())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Agence", dto.idAgence()
                        )
                );

        employe.setAgence(agence);

        Employe nouvelEmploye = employeRepository.save(employe);

        return mapper.toDTO(nouvelEmploye);
    }

    // GET BY ID
    @Override
    @Transactional(readOnly = true)
    public EmployeDTO findById(Long id) {

        Employe employe = employeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employe", id)
                );

        return mapper.toDTO(employe);
    }

    // GET ALL
    @Override
    @Transactional(readOnly = true)
    public List<EmployeDTO> findAll() {

        return employeRepository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    // UPDATE
    @Override
    public EmployeDTO update(Long id, EmployeDTO dto) {

        Employe existant = employeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employe", id)
                );

        existant.setNom(dto.nom());
        existant.setPrenom(dto.prenom());
        existant.setRole(dto.role());

        valider(existant);

        // Mise à jour de l'agence
        Agence agence = agenceRepository.findById(dto.idAgence())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Agence", dto.idAgence()
                        )
                );

        existant.setAgence(agence);

        Employe employeModifie = employeRepository.save(existant);

        return mapper.toDTO(employeModifie);
    }

    // DELETE
    @Override
    public void deleteById(Long id) {

        if (!employeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employe", id);
        }

        employeRepository.deleteById(id);
    }
}
