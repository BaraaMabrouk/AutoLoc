package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import tn.esprit.autoloc.domain.RoleEmploye;

public record EmployeDTO(

        Long idEmploye,

        @NotBlank(message = "Le nom est obligatoire")
        @Size(min = 2, message = "Le nom doit contenir au moins 2 caractères")
        String nom,

        @NotBlank(message = "Le prénom est obligatoire")
        @Size(min = 2, message = "Le prénom doit contenir au moins 2 caractères")
        String prenom,

        @NotNull(message = "Le rôle est obligatoire")
        RoleEmploye role,

        @NotNull(message = "L'identifiant de l'agence est obligatoire")
        @Positive(message = "L'identifiant de l'agence doit être positif")
        Long idAgence

) {}