
package tn.esprit.autoloc.web.dto;

import java.util.Set;

public record AffectationEquipementResponseDTO(

        Long idVehicule,

        Set<Long> idEquipements

) {
}
