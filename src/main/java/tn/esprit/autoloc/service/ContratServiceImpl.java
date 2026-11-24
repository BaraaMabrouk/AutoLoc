
package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.IMapper;
import tn.esprit.autoloc.repository.IContratRepository;
import tn.esprit.autoloc.repository.IReservationRepository;
import tn.esprit.autoloc.web.dto.ContratDTO;
import tn.esprit.autoloc.web.dto.ContratRechercheDTO;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ContratServiceImpl implements IContratService {

    private final IContratRepository contratRepository;
    private final IReservationRepository reservationRepository;
    private final IMapper mapper;

    // VALIDATION METIER EXISTANTE
    private void valider(Contrat contrat) {

        if (contrat.getMontantTotal() != null
                && contrat.getMontantTotal()
                .compareTo(BigDecimal.ZERO) < 0) {

            throw new IllegalArgumentException(
                    "Le montant total ne peut pas être négatif"
            );
        }
    }

    // CREATE
    @Override
    public ContratDTO create(ContratDTO dto) {

        if (dto.idContrat() != null) {
            throw new IllegalArgumentException(
                    "L'identifiant doit être nul"
            );
        }

        Contrat contrat = mapper.toEntity(dto);

        valider(contrat);

        // Association facultative avec Reservation
        if (dto.idReservation() != null) {

            Reservation reservation = reservationRepository
                    .findById(dto.idReservation())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Reservation", dto.idReservation()
                            )
                    );

            contrat.setReservation(reservation);
        }

        Contrat nouveauContrat = contratRepository.save(contrat);

        return mapper.toDTO(nouveauContrat);
    }

    // GET BY ID
    @Override
    @Transactional(readOnly = true)
    public ContratDTO findById(Long id) {

        Contrat contrat = contratRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Contrat", id)
                );

        return mapper.toDTO(contrat);
    }

    // GET ALL
    @Override
    @Transactional(readOnly = true)
    public List<ContratDTO> findAll() {

        return contratRepository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    // UPDATE
    @Override
    public ContratDTO update(Long id, ContratDTO dto) {

        Contrat existant = contratRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Contrat", id)
                );

        existant.setDateSignature(dto.dateSignature());
        existant.setMontantTotal(dto.montantTotal());
        existant.setValide(dto.valide());

        valider(existant);

        // Mise à jour de la réservation si elle est fournie
        if (dto.idReservation() != null) {

            Reservation reservation = reservationRepository
                    .findById(dto.idReservation())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Reservation", dto.idReservation()
                            )
                    );

            existant.setReservation(reservation);
        }

        Contrat contratModifie = contratRepository.save(existant);

        return mapper.toDTO(contratModifie);
    }

    // DELETE
    @Override
    public void deleteById(Long id) {

        if (!contratRepository.existsById(id)) {
            throw new ResourceNotFoundException("Contrat", id);
        }

        contratRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContratRechercheDTO> findByVehiculeId(Long keyword) {

        return contratRepository.findByReservationVehiculeIdVehicule(keyword);
    }

}
