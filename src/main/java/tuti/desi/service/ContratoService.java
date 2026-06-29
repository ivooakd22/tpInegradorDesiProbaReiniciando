package tuti.desi.service;

import tuti.desi.dto.ContratoDTO;
import tuti.desi.dto.ListarContratosRequestDTO;

import java.time.LocalDate;
import java.util.List;

public interface ContratoService {

    List<ContratoDTO> findAll(ListarContratosRequestDTO request);

    ContratoDTO findById(Long id);

    void save(ContratoDTO dto);

    void delete(Long id);

    // Métodos adicionales útiles
    List<ContratoDTO> findByInquilino(Long inquilinoId);

    List<ContratoDTO> findByPropiedad(Long propiedadId);

    LocalDate calcularFechaFin(LocalDate fechaInicio, Integer duracionMeses);
}

