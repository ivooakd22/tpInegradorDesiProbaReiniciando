package tuti.desi.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tuti.desi.dto.ContratoDTO;
import tuti.desi.dto.ListarContratosRequestDTO;
import tuti.desi.service.ContratoService;

import java.util.List;

@Service
@Transactional
public class ContratoServiceImpl implements ContratoService {

    public ContratoServiceImpl() {}

    @Override
    public List<ContratoDTO> findAll(ListarContratosRequestDTO request) {
        // TODO: usar ContratoSpecification.filtrar(request.getEstado()) con contratoRepository
        return null;
    }

    @Override
    public ContratoDTO findById(Long id) {
        // TODO: implementar
        return null;
    }

    @Override
    public void save(ContratoDTO dto) {
        // TODO: validar campos obligatorios (fechaInicio, fechaFin, montoMensual, diaPago, propiedadId, inquilinoId)
        // TODO: si dto.getId() != null → actualizar, si es null → crear con estado BORRADOR
    }

    @Override
    public void delete(Long id) {
        // TODO: implementar
    }
}
