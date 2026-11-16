package tn.esprit.autoloc.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.domain.CategorieVehicule;
import tn.esprit.autoloc.domain.StatutVehicule;
import tn.esprit.autoloc.web.dto.VehiculeDTO;

@Component
public class VehiculeMapper {

    public VehiculeDTO toDTO(Vehicule vehicule) {
        return new VehiculeDTO(
                vehicule.getIdVehicule(),
                vehicule.getImmatriculation(),
                vehicule.getMarque(),
                vehicule.getModele(),
                vehicule.getCategorie() != null
                        ? vehicule.getCategorie().name()
                        : null,
                vehicule.getTarifJournalier(),
                vehicule.getStatut() != null
                        ? vehicule.getStatut().name()
                        : null,
                vehicule.getAgence() != null
                        ? vehicule.getAgence().getIdAgence()
                        : null
        );
    }

    public Vehicule toEntity(VehiculeDTO dto) {
        Vehicule vehicule = new Vehicule();

        vehicule.setImmatriculation(dto.immatriculation());
        vehicule.setMarque(dto.marque());
        vehicule.setModele(dto.modele());

        if (dto.categorie() != null) {
            vehicule.setCategorie(
                    CategorieVehicule.valueOf(dto.categorie())
            );
        }

        vehicule.setTarifJournalier(dto.tarifJournalier());

        if (dto.statut() != null) {
            vehicule.setStatut(
                    StatutVehicule.valueOf(dto.statut())
            );
        }

        return vehicule;
    }
}