package tn.esprit.autoloc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.web.dto.VehiculeDTO;

import java.util.List;

public interface IVehiculeRepository extends JpaRepository<Vehicule, Long> {

    @Query("SELECT new tn.esprit.autoloc.web.dto.VehiculeDTO(" +
            "v.idVehicule, v.immatriculation, v.marque, v.modele, " +
            "CAST(v.categorie AS String), v.tarifJournalier, " +
            "CAST(v.statut AS String), a.idAgence) " +
            "FROM Vehicule v " +
            "JOIN v.reservations r " +
            "JOIN r.contrat c " +
            "LEFT JOIN v.agence a " +
            "WHERE c.idContrat = :keyword")
    List<VehiculeDTO> findByReservationsContratIdContrat(
            @Param("keyword") Long keyword);

    @Query("SELECT new tn.esprit.autoloc.web.dto.VehiculeDTO(" +
            "v.idVehicule, v.immatriculation, v.marque, v.modele, " +
            "CAST(v.categorie AS String), v.tarifJournalier, " +
            "CAST(v.statut AS String), a.idAgence) " +
            "FROM Vehicule v " +
            "JOIN v.maintenances m " +
            "LEFT JOIN v.agence a " +
            "WHERE m.idMaintenance = :keyword")
    List<VehiculeDTO> findByMaintenancesIdMaintenance(
            @Param("keyword") Long keyword);
}