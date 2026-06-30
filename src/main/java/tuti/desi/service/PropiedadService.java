package tuti.desi.service;

import tuti.desi.dto.ListarPropiedadesRequestDTO;
import tuti.desi.dto.PropiedadDTO;

import java.util.List;

import org.springframework.stereotype.Service;

public interface PropiedadService {

    List<PropiedadDTO> findAll(ListarPropiedadesRequestDTO request);

    PropiedadDTO findById(Long id);

    void save(PropiedadDTO dto);

    void delete(Long id);
}
