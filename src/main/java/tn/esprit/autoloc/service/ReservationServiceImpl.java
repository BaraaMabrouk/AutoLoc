
package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.Vehicule;

import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.IMapper;

import tn.esprit.autoloc.repository.IClientRepository;
import tn.esprit.autoloc.repository.IReservationRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;

import tn.esprit.autoloc.web.dto.ReservationDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationServiceImpl implements IReservationService {

    private final IReservationRepository reservationRepository;
    private final IClientRepository clientRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final IMapper mapper;

    // VALIDATION METIER EXISTANTE
    private void valider(Reservation reservation) {

        if (reservation.getDateDebut() != null
                && reservation.getDateFin() != null
                && reservation.getDateFin()
                .isBefore(reservation.getDateDebut())) {

            throw new IllegalArgumentException(
                    "La date de fin ne peut pas précéder la date de début"
            );
        }
    }

    // CREATE
    @Override
    public ReservationDTO create(ReservationDTO dto) {

        if (dto.idReservation() != null) {
            throw new IllegalArgumentException(
                    "L'identifiant doit être nul"
            );
        }

        Reservation reservation = mapper.toEntity(dto);

        valider(reservation);

        // Association avec Client
        Client client = clientRepository.findById(dto.idClient())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Client", dto.idClient()
                        )
                );

        reservation.setClient(client);

        // Association avec Vehicule
        Vehicule vehicule = vehiculeRepository.findById(dto.idVehicule())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Vehicule", dto.idVehicule()
                        )
                );

        reservation.setVehicule(vehicule);

        Reservation nouvelleReservation =
                reservationRepository.save(reservation);

        return mapper.toDTO(nouvelleReservation);
    }

    // GET BY ID
    @Override
    @Transactional(readOnly = true)
    public ReservationDTO findById(Long id) {

        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Reservation", id)
                );

        return mapper.toDTO(reservation);
    }

    // GET ALL
    @Override
    @Transactional(readOnly = true)
    public List<ReservationDTO> findAll() {

        return reservationRepository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    // UPDATE
    @Override
    public ReservationDTO update(Long id, ReservationDTO dto) {

        Reservation existante = reservationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Reservation", id)
                );

        existante.setDateDebut(dto.dateDebut());
        existante.setDateFin(dto.dateFin());
        existante.setStatut(dto.statut());

        valider(existante);

        // Mise à jour du client
        Client client = clientRepository.findById(dto.idClient())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Client", dto.idClient()
                        )
                );

        existante.setClient(client);

        // Mise à jour du véhicule
        Vehicule vehicule = vehiculeRepository.findById(dto.idVehicule())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Vehicule", dto.idVehicule()
                        )
                );

        existante.setVehicule(vehicule);

        Reservation reservationModifiee =
                reservationRepository.save(existante);

        return mapper.toDTO(reservationModifiee);
    }

    // DELETE
    @Override
    public void deleteById(Long id) {

        if (!reservationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reservation", id);
        }

        reservationRepository.deleteById(id);
    }
}
