
package tn.esprit.autoloc.service;

import tn.esprit.autoloc.web.dto.ReservationDTO;

import java.util.List;

public interface IReservationService {

    ReservationDTO create(ReservationDTO dto);

    ReservationDTO findById(Long id);

    List<ReservationDTO> findAll();

    ReservationDTO update(Long id, ReservationDTO dto);

    void deleteById(Long id);
}
