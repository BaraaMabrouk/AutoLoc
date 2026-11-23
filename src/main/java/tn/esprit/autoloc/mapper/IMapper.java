
package tn.esprit.autoloc.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import tn.esprit.autoloc.domain.*;
import tn.esprit.autoloc.web.dto.*;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface IMapper {

    // ========== AGENCE ==========

    @Mapping(target = "idVehicules", source = "vehicules")
    @Mapping(target = "idEmployes", source = "employes")
    AgenceDTO toDTO(Agence entity);

    @Mapping(target = "vehicules", ignore = true)
    @Mapping(target = "employes", ignore = true)
    Agence toEntity(AgenceDTO dto);


    // ========== VEHICULE ==========

    @Mapping(target = "idAgence", source = "agence.idAgence")
    VehiculeDTO toDTO(Vehicule entity);

    @Mapping(target = "agence", ignore = true)
    @Mapping(target = "equipements", ignore = true)
    @Mapping(target = "reservations", ignore = true)
    @Mapping(target = "maintenances", ignore = true)
    Vehicule toEntity(VehiculeDTO dto);


    // ========== EMPLOYE ==========

    @Mapping(target = "idAgence", source = "agence.idAgence")
    EmployeDTO toDTO(Employe entity);

    @Mapping(target = "agence", ignore = true)
    Employe toEntity(EmployeDTO dto);


    // ========== CLIENT ==========

    @Mapping(target = "idReservations", source = "reservations")
    ClientDTO toDTO(Client entity);

    @Mapping(target = "reservations", ignore = true)
    Client toEntity(ClientDTO dto);


    // ========== EQUIPEMENT ==========

    @Mapping(target = "idVehicules", source = "vehicules")
    EquipementDTO toDTO(Equipement entity);

    @Mapping(target = "vehicules", ignore = true)
    Equipement toEntity(EquipementDTO dto);


    // ========== MAINTENANCE ==========

    @Mapping(target = "idVehicule", source = "vehicule.idVehicule")
    MaintenanceDTO toDTO(Maintenance entity);

    @Mapping(target = "vehicule", ignore = true)
    Maintenance toEntity(MaintenanceDTO dto);


    // ========== RESERVATION ==========

    @Mapping(target = "idClient", source = "client.idClient")
    @Mapping(target = "idVehicule", source = "vehicule.idVehicule")
    @Mapping(target = "idContrat", source = "contrat.idContrat")
    ReservationDTO toDTO(Reservation entity);

    @Mapping(target = "client", ignore = true)
    @Mapping(target = "vehicule", ignore = true)
    @Mapping(target = "contrat", ignore = true)
    Reservation toEntity(ReservationDTO dto);


    // ========== PAIEMENT ==========

    @Mapping(target = "idContrat", source = "contrat.idContrat")
    PaiementDTO toDTO(Paiement entity);

    @Mapping(target = "contrat", ignore = true)
    Paiement toEntity(PaiementDTO dto);


    // ========== CONTRAT ==========

    @Mapping(target = "idReservation", source = "reservation.idReservation")
    @Mapping(target = "idPaiements", source = "paiements")
    ContratDTO toDTO(Contrat entity);

    @Mapping(target = "reservation", ignore = true)
    @Mapping(target = "paiements", ignore = true)
    Contrat toEntity(ContratDTO dto);


    // ========== CONVERSION DES IDENTIFIANTS ==========

    default Long vehiculeToId(Vehicule vehicule) {
        return vehicule == null ? null : vehicule.getIdVehicule();
    }

    default Long employeToId(Employe employe) {
        return employe == null ? null : employe.getIdEmploye();
    }

    default Long reservationToId(Reservation reservation) {
        return reservation == null ? null : reservation.getIdReservation();
    }

    default Long paiementToId(Paiement paiement) {
        return paiement == null ? null : paiement.getIdPaiement();
    }
}
