package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record EquipementDTO(

        Long idEquipement,

        @NotBlank(message = "Le libellé est obligatoire")
        @Size(min = 5, message = "Le libellé doit contenir au moins 5 caractères")
        String libelle,

        Set<Long> idVehicules

) {}