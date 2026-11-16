
package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.ClientMapper;
import tn.esprit.autoloc.repository.IClientRepository;
import tn.esprit.autoloc.web.dto.ClientDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientServiceImpl implements IClientService {

    private final IClientRepository clientRepository;
    private final ClientMapper clientMapper;

    // VALIDATION METIER EXISTANTE
    private void valider(Client client) {

        if (client.getNom() == null || client.getNom().isBlank()) {
            throw new IllegalArgumentException("Le nom est obligatoire");
        }

        if (client.getEmail() == null || client.getEmail().isBlank()) {
            throw new IllegalArgumentException("L'email est obligatoire");
        }
    }

    // CREATE
    @Override
    public ClientDTO create(ClientDTO dto) {

        if (dto.idClient() != null) {
            throw new IllegalArgumentException(
                    "L'identifiant doit être nul"
            );
        }

        Client client = clientMapper.toEntity(dto);

        valider(client);

        Client nouveauClient = clientRepository.save(client);

        return clientMapper.toDTO(nouveauClient);
    }

    // GET BY ID
    @Override
    @Transactional(readOnly = true)
    public ClientDTO findById(Long id) {

        Client client = clientRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Client", id)
                );

        return clientMapper.toDTO(client);
    }

    // GET ALL
    @Override
    @Transactional(readOnly = true)
    public List<ClientDTO> findAll() {

        return clientRepository.findAll()
                .stream()
                .map(clientMapper::toDTO)
                .toList();
    }

    // UPDATE
    @Override
    public ClientDTO update(Long id, ClientDTO dto) {

        Client existant = clientRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Client", id)
                );

        existant.setNom(dto.nom());
        existant.setPrenom(dto.prenom());
        existant.setEmail(dto.email());
        existant.setTelephone(dto.telephone());
        existant.setNumPermis(dto.numPermis());
        existant.setDateInscription(dto.dateInscription());

        valider(existant);

        Client clientModifie = clientRepository.save(existant);

        return clientMapper.toDTO(clientModifie);
    }

    // DELETE
    @Override
    public void deleteById(Long id) {

        if (!clientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Client", id);
        }

        clientRepository.deleteById(id);
    }
}
