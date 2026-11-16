package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

public record AgenceDTO(

        Long idAgence,

        @NotBlank(message = "Le nom de l'agence est obligatoire")
        @Size(min = 5, message = "Le nom doit contenir au moins 5 caractères")
        String nom,

        @NotBlank(message = "La ville est obligatoire")
        String ville,

        @NotBlank(message = "L'adresse est obligatoire")
        String adresse,

        @NotBlank(message = "Le téléphone est obligatoire")
        String telephone,

        List<Long> idVehicules,
        List<Long> idEmployes

) {}