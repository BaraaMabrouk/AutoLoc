package tn.esprit.autoloc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.domain.Contrat;

import java.util.List;

public interface IContratRepository extends JpaRepository<Contrat, Long> {
    List<Contrat> findByReservationVehiculeIdVehicule(Long keyword);
}