
package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.PaiementMapper;
import tn.esprit.autoloc.repository.IPaiementRepository;
import tn.esprit.autoloc.web.dto.PaiementDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaiementServiceImpl implements IPaiementService {

    private final IPaiementRepository paiementRepository;
    private final PaiementMapper paiementMapper;

    // GET BY ID
    @Override
    public PaiementDTO findById(Long id) {

        Paiement paiement = paiementRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Paiement", id)
                );

        return paiementMapper.toDTO(paiement);
    }

    // GET ALL
    @Override
    public List<PaiementDTO> findAll() {

        return paiementRepository.findAll()
                .stream()
                .map(paiementMapper::toDTO)
                .toList();
    }
}
