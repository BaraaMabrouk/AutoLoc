package tn.esprit.autoloc.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.web.dto.ReservationDTO;

@Component
public class ReservationMapper {

    // Entity → DTO
    public ReservationDTO toDTO(Reservation reservation) {

        return new ReservationDTO(
                reservation.getIdReservation(),
                reservation.getDateDebut(),
                reservation.getDateFin(),
                reservation.getStatut(),

                reservation.getClient() != null
                        ? reservation.getClient().getIdClient()
                        : null,

                reservation.getVehicule() != null
                        ? reservation.getVehicule().getIdVehicule()
                        : null,

                reservation.getContrat() != null
                        ? reservation.getContrat().getIdContrat()
                        : null
        );
    }

    // DTO → Entity
    public Reservation toEntity(ReservationDTO dto) {

        Reservation reservation = new Reservation();

        reservation.setDateDebut(dto.dateDebut());
        reservation.setDateFin(dto.dateFin());
        reservation.setStatut(dto.statut());

        return reservation;
    }
}