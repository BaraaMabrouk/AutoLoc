package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import tn.esprit.autoloc.domain.StatutReservation;

import java.time.LocalDate;

public record ReservationDTO(

        Long idReservation,

        @NotNull(message = "La date de début est obligatoire")
        LocalDate dateDebut,

        @NotNull(message = "La date de fin est obligatoire")
        LocalDate dateFin,

        @NotNull(message = "Le statut est obligatoire")
        StatutReservation statut,

        @NotNull(message = "L'identifiant du client est obligatoire")
        @Positive(message = "L'identifiant du client doit être positif")
        Long idClient,

        @NotNull(message = "L'identifiant du véhicule est obligatoire")
        @Positive(message = "L'identifiant du véhicule doit être positif")
        Long idVehicule,

        Long idContrat

) {
    @AssertTrue(message = "La date de fin doit être postérieure ou égale à la date de début")
    public boolean isDatesValides() {
        return dateDebut == null
                || dateFin == null
                || !dateFin.isBefore(dateDebut);
    }
}