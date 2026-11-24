package tn.esprit.autoloc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.web.dto.MaintenanceDTO;

import java.util.List;

public interface IMaintenanceRepository extends JpaRepository<Maintenance, Long> {

    @Query("SELECT new tn.esprit.autoloc.web.dto.MaintenanceDTO(m.idMaintenance, m.dateDebut, m.dateFin, m.description, m.vehicule.idVehicule) FROM Maintenance m WHERE m.vehicule.idVehicule = :keyword")
    List<MaintenanceDTO> findByVehiculeIdVehicule(@Param("keyword") Long keyword);

}