package tn.esprit.autoloc.mapper;

import org.springframework.stereotype.Component;

import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.web.dto.ContratDTO;

import java.util.stream.Collectors;

@Component
public class ContratMapper {

    // Entity → DTO
    public ContratDTO toDTO(Contrat contrat) {

        return new ContratDTO(
                contrat.getIdContrat(),
                contrat.getDateSignature(),
                contrat.getMontantTotal(),
                contrat.getValide(),

                contrat.getPaiements().stream()
                        .map(Paiement::getIdPaiement)
                        .collect(Collectors.toList()),

                contrat.getReservation() != null
                        ? contrat.getReservation().getIdReservation()
                        : null
        );
    }

    // DTO → Entity
    public Contrat toEntity(ContratDTO dto) {

        Contrat contrat = new Contrat();

        contrat.setDateSignature(dto.dateSignature());
        contrat.setMontantTotal(dto.montantTotal());
        contrat.setValide(dto.valide());

        return contrat;
    }
}