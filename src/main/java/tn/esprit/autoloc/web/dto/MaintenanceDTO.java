
package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record MaintenanceDTO(

        Long idMaintenance,

        @NotNull(message = "La date de début est obligatoire")
        LocalDate dateDebut,

        @NotNull(message = "La date de fin est obligatoire")
        LocalDate dateFin,

        @NotBlank(message = "La description est obligatoire")
        String description,

        @NotNull(
                groups = Affectation.class,
                message = "L'identifiant du véhicule est obligatoire"
        )
        @Positive(
                message = "L'identifiant du véhicule doit être positif",
                groups = {jakarta.validation.groups.Default.class, Affectation.class}
        )
        Long idVehicule

) {
    public interface Affectation {}

    @AssertTrue(message = "La date de fin doit être postérieure ou égale à la date de début")
    public boolean isDatesValides() {
        return dateDebut == null
                || dateFin == null
                || !dateFin.isBefore(dateDebut);
    }
}
