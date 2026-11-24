package tn.esprit.autoloc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.web.dto.ContratRechercheDTO;

import java.util.List;

public interface IContratRepository extends JpaRepository<Contrat, Long> {

    @Query("SELECT new tn.esprit.autoloc.web.dto.ContratRechercheDTO(" +
            "c.idContrat, c.dateSignature, c.montantTotal, " +
            "c.valide, r.idReservation) " +
            "FROM Contrat c JOIN c.reservation r " +
            "JOIN r.vehicule v WHERE v.idVehicule = :keyword")
    List<ContratRechercheDTO> findByReservationVehiculeIdVehicule(
            @Param("keyword") Long keyword);
}