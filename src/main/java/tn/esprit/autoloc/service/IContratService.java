
package tn.esprit.autoloc.service;

import tn.esprit.autoloc.web.dto.ContratDTO;
import tn.esprit.autoloc.web.dto.ContratRechercheDTO;

import java.util.List;

public interface IContratService {

    List<ContratRechercheDTO> findByVehiculeId(Long keyword);

    ContratDTO create(ContratDTO dto);

    ContratDTO findById(Long id);

    List<ContratDTO> findAll();

    ContratDTO update(Long id, ContratDTO dto);

    void deleteById(Long id);
}
