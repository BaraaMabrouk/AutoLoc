package tn.esprit.autoloc.web.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ContratRechercheDTO(

        Long idContrat,
        LocalDate dateSignature,
        BigDecimal montantTotal,
        Boolean valide,
        Long idReservation

) {}