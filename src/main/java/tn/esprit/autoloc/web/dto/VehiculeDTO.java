package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record VehiculeDTO(

        Long idVehicule,

        @NotBlank(message = "L'immatriculation est obligatoire")
        @Size(min = 5, message = "L'immatriculation doit contenir au moins 5 caractères")
        String immatriculation,

        @NotBlank(message = "La marque est obligatoire")
        String marque,

        @NotBlank(message = "Le modèle est obligatoire")
        String modele,

        @NotNull(message = "La catégorie est obligatoire")
        String categorie,

        @NotNull(message = "Le tarif journalier est obligatoire")
        @Positive(message = "Le tarif journalier doit être supérieur à 0")
        BigDecimal tarifJournalier,

        @NotNull(message = "Le statut est obligatoire")
        String statut,

        @Positive(message = "L'identifiant de l'agence doit être positif")
        Long idAgence

) {}