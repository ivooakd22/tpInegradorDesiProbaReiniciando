package tuti.desi.service;

import tuti.desi.dto.ListarPropiedadesRequestDTO;
import tuti.desi.dto.PropiedadDTO;

import java.util.List;

public interface PropiedadService {

    List<PropiedadDTO> findAll(ListarPropiedadesRequestDTO request);

    PropiedadDTO findById(Long id);

    void save(PropiedadDTO dto);

    void delete(Long id);
}
