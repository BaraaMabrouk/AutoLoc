package tn.esprit.autoloc.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.web.dto.MaintenanceDTO;

@Component
public class MaintenanceMapper {

    public MaintenanceDTO toDTO(Maintenance maintenance) {
        return new MaintenanceDTO(
                maintenance.getIdMaintenance(),
                maintenance.getDateDebut(),
                maintenance.getDateFin(),
                maintenance.getDescription(),
                maintenance.getVehicule() != null
                        ? maintenance.getVehicule().getIdVehicule()
                        : null
        );
    }

    public Maintenance toEntity(MaintenanceDTO dto) {
        Maintenance maintenance = new Maintenance();

        maintenance.setDateDebut(dto.dateDebut());
        maintenance.setDateFin(dto.dateFin());
        maintenance.setDescription(dto.description());

        return maintenance;
    }
}