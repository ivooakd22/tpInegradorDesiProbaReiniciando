package tuti.desi.service.impl;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tuti.desi.dto.ContratoDTO;
import tuti.desi.dto.ListarContratosRequestDTO;
import tuti.desi.entity.Contrato;
import tuti.desi.entity.Propiedad;
import tuti.desi.entity.Inquilino;
import tuti.desi.enums.EstadoContrato;
import tuti.desi.enums.EstadoPropiedad;
import tuti.desi.repository.ContratoRepository;
import tuti.desi.repository.PropiedadRepository;
import tuti.desi.repository.InquilinoRepository;
import tuti.desi.service.ContratoService;

import java.util.List;
import java.util.stream.Collectors;

@Service

@Transactional
public class ContratoServiceImpl implements ContratoService {

    @Autowired
    private ContratoRepository contratoRepository;

    @Autowired
    private PropiedadRepository propiedadRepository;

    @Autowired
    private InquilinoRepository inquilinoRepository;

    
    @Override
    public List<ContratoDTO> findAll(ListarContratosRequestDTO filtro) {
       
        if (filtro == null) {
            return contratoRepository.findByEliminadoFalse()
                    .stream().map(this::toDTO).toList();
        }

        
        EstadoContrato estadoEnum = null;
        if (filtro.getEstado() != null && !filtro.getEstado().isBlank()) {
            estadoEnum = EstadoContrato.valueOf(filtro.getEstado().toUpperCase());
        }

        
        return contratoRepository.filtrar(
                filtro.getPropiedadId(),
                filtro.getInquilinoId(),
                estadoEnum,
                filtro.getFechaInicio()
        ).stream().map(this::toDTO).toList();
    }

    @Override
    public ContratoDTO findById(Long id) {
        return contratoRepository.findById(id).map(this::toDTO).orElse(null);
    }

    @Override
    public List<ContratoDTO> findByInquilino(Long inquilinoId) {
        return contratoRepository.findByInquilinoId(inquilinoId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<ContratoDTO> findByPropiedad(Long propiedadId) {
        return contratoRepository.findByPropiedadId(propiedadId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public LocalDate calcularFechaFin(LocalDate fechaInicio, Integer duracionMeses) {
        if (fechaInicio == null || duracionMeses == null) {
            throw new IllegalArgumentException("Debe ingresar fecha de inicio y duración para calcular la fecha de fin.");
        }
        return fechaInicio.plusMonths(duracionMeses);
    }

    @Override
    public void save(ContratoDTO dto) {
        validarContrato(dto);

        Propiedad propiedad = propiedadRepository.findById(dto.getPropiedadId())
                .orElseThrow(() -> new RuntimeException("La propiedad buscada no existe"));
        Inquilino inquilino = inquilinoRepository.findById(dto.getInquilinoId())
                .orElseThrow(() -> new RuntimeException("El inquilino buscado no existe"));

        Contrato contrato;
        EstadoContrato nuevoEstado;

        if (dto.getId() != null) {
            contrato = contratoRepository.findById(dto.getId())
                    .orElseThrow(() -> new RuntimeException("No se encontró el contrato " + dto.getId()));

            EstadoContrato estadoAnterior = contrato.getEstado();
            nuevoEstado = dto.getEstado() != null ? EstadoContrato.valueOf(dto.getEstado().toUpperCase()) : contrato.getEstado();

            if ((estadoAnterior == EstadoContrato.FINALIZADO || estadoAnterior == EstadoContrato.RESCINDIDO)
                    && nuevoEstado == EstadoContrato.ACTIVO) {
                throw new IllegalArgumentException("No se permite activar un contrato que ya está FINALIZADO o RESCINDIDO.");
            }

            if (nuevoEstado == EstadoContrato.ACTIVO) {
                propiedad.setEstado(EstadoPropiedad.ALQUILADA);
            } else if (nuevoEstado == EstadoContrato.FINALIZADO || nuevoEstado == EstadoContrato.RESCINDIDO) {
                propiedad.setEstado(EstadoPropiedad.DISPONIBLE);
            }
            propiedadRepository.save(propiedad);

        } else {
            contrato = new Contrato();
            nuevoEstado = dto.getEstado() != null ? EstadoContrato.valueOf(dto.getEstado().toUpperCase()) : EstadoContrato.BORRADOR;
        }

        if (nuevoEstado == EstadoContrato.ACTIVO) {
            if (propiedad.getEstado() != null && !"DISPONIBLE".equalsIgnoreCase(propiedad.getEstado().toString())) {
                throw new IllegalArgumentException("No se puede crear un contrato si la propiedad no está disponible.");
            }
            boolean tieneOtroActivo = contratoRepository.existsByPropiedadIdAndEstado(propiedad.getId(), EstadoContrato.ACTIVO);
            if (tieneOtroActivo) {
                throw new IllegalArgumentException("Una propiedad no podrá tener más de un contrato activo.");
            }
            propiedad.setEstado(EstadoPropiedad.ALQUILADA);
            propiedadRepository.save(propiedad);
        }

        contrato.setFechaInicio(dto.getFechaInicio());
        if (dto.getFechaFin() == null) {
            throw new IllegalArgumentException("Debe ingresar la fecha de fin del contrato.");
        }
        contrato.setFechaFin(dto.getFechaFin());
        contrato.setMontoMensual(dto.getMontoMensual());
        contrato.setDiaPago(dto.getDiaPago());
        contrato.setEstado(nuevoEstado);
        contrato.setPropiedad(propiedad);
        contrato.setInquilino(inquilino);
        contrato.setDescripcion(dto.getDescripcion());
        contrato.setDuracionMeses(dto.getDuracionMeses());
        contrato.setEliminado(dto.isEliminado());

        contratoRepository.save(contrato);
    }

    @Override
    public void delete(Long id) {
        Contrato contrato = contratoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el contrato: " + id));
        if (contrato.getEstado() != EstadoContrato.BORRADOR) {
            throw new IllegalArgumentException("Solo se pueden borrar contratos en estado BORRADOR.");
        }
        contrato.setEliminado(true);
        contratoRepository.save(contrato);
    }

    
    private void validarContrato(ContratoDTO dto) {
        if (dto.getFechaInicio() == null || dto.getMontoMensual() == null
                || dto.getDiaPago() == null || dto.getPropiedadId() == null
                || dto.getInquilinoId() == null || dto.getDuracionMeses() == null) {
            throw new IllegalArgumentException("Es necesario ingresar todos los campos obligatorios.");
        }
        if (dto.getMontoMensual() <= 0) {
            throw new IllegalArgumentException("El importe mensual debe ser positivo.");
        }
        if (dto.getDuracionMeses() <= 0) {
            throw new IllegalArgumentException("La duración debe ser positiva.");
        }
        if (dto.getDiaPago() < 1 || dto.getDiaPago() > 31) {
            throw new IllegalArgumentException("El día de vencimiento debe estar entre 1 y 31.");
        }
    }

    private ContratoDTO toDTO(Contrato c) {
        ContratoDTO dto = new ContratoDTO();
        dto.setId(c.getId());
        dto.setFechaInicio(c.getFechaInicio());
        dto.setFechaFin(c.getFechaFin());
        dto.setMontoMensual(c.getMontoMensual());
        dto.setDiaPago(c.getDiaPago());
        if (c.getEstado() != null) {
            dto.setEstado(c.getEstado().name());
        }
        if (c.getPropiedad() != null) {
            dto.setPropiedadId(c.getPropiedad().getId());
            dto.setPropiedadDireccion(c.getPropiedad().getDireccion());
            if (c.getPropiedad().getPropietario() != null) {
                dto.setPropietarioNombre(c.getPropiedad().getPropietario().getNombre());
            }
            if (c.getInquilino() != null) {
                dto.setInquilinoId(c.getInquilino().getId());
                dto.setInquilinoNombre(c.getInquilino().getNombre());
            }
            dto.setEliminado(c.isEliminado());
            return dto;

        }
        return null;
    }
}
