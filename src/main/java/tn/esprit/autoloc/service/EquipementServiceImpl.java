
package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.EquipementMapper;
import tn.esprit.autoloc.repository.IEquipementRepository;
import tn.esprit.autoloc.web.dto.EquipementDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EquipementServiceImpl implements IEquipementService {

    private final IEquipementRepository equipementRepository;
    private final EquipementMapper equipementMapper;

    // VALIDATION METIER EXISTANTE
    private void valider(Equipement equipement) {

        if (equipement.getLibelle() == null
                || equipement.getLibelle().isBlank()) {

            throw new IllegalArgumentException(
                    "Le libellé est obligatoire"
            );
        }
    }

    // CREATE
    @Override
    public EquipementDTO create(EquipementDTO dto) {

        if (dto.idEquipement() != null) {
            throw new IllegalArgumentException(
                    "L'identifiant doit être nul"
            );
        }

        Equipement equipement = equipementMapper.toEntity(dto);

        valider(equipement);

        Equipement nouvelEquipement =
                equipementRepository.save(equipement);

        return equipementMapper.toDTO(nouvelEquipement);
    }

    // GET BY ID
    @Override
    @Transactional(readOnly = true)
    public EquipementDTO findById(Long id) {

        Equipement equipement = equipementRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Equipement", id)
                );

        return equipementMapper.toDTO(equipement);
    }

    // GET ALL
    @Override
    @Transactional(readOnly = true)
    public List<EquipementDTO> findAll() {

        return equipementRepository.findAll()
                .stream()
                .map(equipementMapper::toDTO)
                .toList();
    }

    // UPDATE
    @Override
    public EquipementDTO update(Long id, EquipementDTO dto) {

        Equipement existant = equipementRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Equipement", id)
                );

        existant.setLibelle(dto.libelle());

        valider(existant);

        Equipement equipementModifie =
                equipementRepository.save(existant);

        return equipementMapper.toDTO(equipementModifie);
    }

    // DELETE
    @Override
    public void deleteById(Long id) {

        if (!equipementRepository.existsById(id)) {
            throw new ResourceNotFoundException("Equipement", id);
        }

        equipementRepository.deleteById(id);
    }
}
