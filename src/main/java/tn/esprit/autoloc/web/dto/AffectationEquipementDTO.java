
package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AffectationEquipementDTO(

        @NotNull(message = "L'identifiant de l'équipement est obligatoire")
        @Positive(message = "L'identifiant de l'équipement doit être positif")
        Long idEquipement

) {
}
