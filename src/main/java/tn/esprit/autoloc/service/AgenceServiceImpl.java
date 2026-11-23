
package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.IMapper;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.web.dto.AgenceDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AgenceServiceImpl implements IAgenceService {

    private final IAgenceRepository agenceRepository;
    private final IMapper mapper;

    @Override
    public AgenceDTO create(AgenceDTO dto) {

        if (dto.idAgence() != null) {
            throw new IllegalArgumentException(
                    "L'identifiant doit être nul lors de la création"
            );
        }

        Agence agence = mapper.toEntity(dto);

        Agence nouvelleAgence = agenceRepository.save(agence);

        return mapper.toDTO(nouvelleAgence);
    }

    @Override
    @Transactional(readOnly = true)
    public AgenceDTO findById(Long id) {

        Agence agence = agenceRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Agence", id)
                );

        return mapper.toDTO(agence);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgenceDTO> findAll() {

        return agenceRepository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public AgenceDTO update(Long id, AgenceDTO dto) {

        Agence existante = agenceRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Agence", id)
                );

        existante.setNom(dto.nom());
        existante.setVille(dto.ville());
        existante.setAdresse(dto.adresse());
        existante.setTelephone(dto.telephone());

        Agence agenceModifiee = agenceRepository.save(existante);

        return mapper.toDTO(agenceModifiee);
    }

    @Override
    public void deleteById(Long id) {

        if (!agenceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Agence", id);
        }

        agenceRepository.deleteById(id);
    }
}
