package tn.esprit.autoloc.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.web.dto.ClientDTO;

import java.util.stream.Collectors;

@Component
public class ClientMapper {

    // Entity → DTO
    public ClientDTO toDTO(Client client) {

        return new ClientDTO(
                client.getIdClient(),
                client.getNom(),
                client.getPrenom(),
                client.getEmail(),
                client.getTelephone(),
                client.getNumPermis(),
                client.getDateInscription(),

                client.getReservations().stream()
                        .map(reservation -> reservation.getIdReservation())
                        .collect(Collectors.toList())
        );
    }

    // DTO → Entity
    public Client toEntity(ClientDTO dto) {

        Client client = new Client();

        client.setNom(dto.nom());
        client.setPrenom(dto.prenom());
        client.setEmail(dto.email());
        client.setTelephone(dto.telephone());
        client.setNumPermis(dto.numPermis());
        client.setDateInscription(dto.dateInscription());

        return client;
    }
}