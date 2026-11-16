package tn.esprit.autoloc.mapper;

import org.springframework.stereotype.Component;

import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.web.dto.PaiementDTO;

@Component
public class PaiementMapper {

    // Entity → DTO
    public PaiementDTO toDTO(Paiement paiement) {

        return new PaiementDTO(
                paiement.getIdPaiement(),
                paiement.getMontant(),
                paiement.getDatePaiement(),
                paiement.getModePaiement(),

                paiement.getContrat() != null
                        ? paiement.getContrat().getIdContrat()
                        : null
        );
    }

    // DTO → Entity
    public Paiement toEntity(PaiementDTO dto) {

        Paiement paiement = new Paiement();

        paiement.setMontant(dto.montant());
        paiement.setDatePaiement(dto.datePaiement());
        paiement.setModePaiement(dto.modePaiement());

        return paiement;
    }
}