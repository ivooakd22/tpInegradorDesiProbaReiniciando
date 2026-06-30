package tuti.desi.service;

import tuti.desi.dto.FacturaDTO;
import tuti.desi.dto.ListarFacturasRequestDTO;

import java.util.List;

public interface FacturaService {

    List<FacturaDTO> findAll(ListarFacturasRequestDTO request);

    FacturaDTO findById(Long id);

    void save(FacturaDTO dto);

    void delete(Long id);
}
