package tn.esprit.autoloc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.autoloc.domain.Contrat;

import java.util.List;

public interface IContratRepository extends JpaRepository<Contrat, Long> {

    @Query("SELECT c FROM Contrat c WHERE c.reservation.vehicule.idVehicule = :keyword")
    List<Contrat> findByReservationVehiculeIdVehicule(@Param("keyword") Long keyword);

}