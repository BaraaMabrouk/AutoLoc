package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.PastOrPresent;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record ContratDTO(

        Long idContrat,

        @NotNull(message = "La date de signature est obligatoire")
        @PastOrPresent(message = "La date de signature ne peut pas être dans le futur")
        LocalDate dateSignature,

        @NotNull(message = "Le montant total est obligatoire")
        @PositiveOrZero(message = "Le montant total doit être positif ou nul")
        BigDecimal montantTotal,

        @NotNull(message = "Le champ valide est obligatoire")
        Boolean valide,

        List<Long> idPaiements,

        Long idReservation

) {}