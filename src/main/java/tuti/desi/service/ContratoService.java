package tuti.desi.service;

import tuti.desi.dto.ContratoDTO;
import tuti.desi.dto.ListarContratosRequestDTO;

import java.util.List;

public interface ContratoService {

    List<ContratoDTO> findAll(ListarContratosRequestDTO request);

    ContratoDTO findById(Long id);

    void save(ContratoDTO dto);

    void delete(Long id);
}
