package tuti.desi.service;

import java.time.LocalDate;
import java.util.List;
import tuti.desi.dto.ContratoDTO;
import tuti.desi.dto.ListarContratosRequestDTO;
import tuti.desi.entity.Contrato;

public interface ContratoService {

     List<ContratoDTO> findAll(ListarContratosRequestDTO filtro);
    ContratoDTO findById(Long id);
    List<ContratoDTO> findByInquilino(Long inquilinoId);
    List<ContratoDTO> findByPropiedad(Long propiedadId);
    LocalDate calcularFechaFin(LocalDate fechaInicio, Integer duracionMeses);
    void save(ContratoDTO dto);
    void delete(Long id);  
}


