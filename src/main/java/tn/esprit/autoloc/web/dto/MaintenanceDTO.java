package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.AssertTrue;

import java.time.LocalDate;

public record MaintenanceDTO(

        Long idMaintenance,

        @NotNull(message = "La date de début est obligatoire")
        LocalDate dateDebut,

        @NotNull(message = "La date de fin est obligatoire")
        LocalDate dateFin,

        @NotBlank(message = "La description est obligatoire")
        String description,

        @Positive(message = "L'identifiant du véhicule doit être positif")
        Long idVehicule

) {
    @AssertTrue(message = "La date de fin doit être postérieure ou égale à la date de début")
    public boolean isDatesValides() {
        return dateDebut == null
                || dateFin == null
                || !dateFin.isBefore(dateDebut);
    }
}