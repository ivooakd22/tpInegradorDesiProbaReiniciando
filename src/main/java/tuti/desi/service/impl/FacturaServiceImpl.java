package tuti.desi.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tuti.desi.dto.ListarFacturasRequestDTO;
import tuti.desi.dto.PropiedadDTO;
import tuti.desi.dto.ContratoDTO;
import tuti.desi.dto.FacturaDTO;
import tuti.desi.entity.HistorialEstadoFactura;
import tuti.desi.entity.Contrato;
import tuti.desi.entity.Factura;
import tuti.desi.enums.EstadoFactura;
import tuti.desi.repository.HistorialEstadoFacturaRepository;
import tuti.desi.repository.ContratoRepository;
import tuti.desi.repository.FacturaRepository;
import tuti.desi.service.FacturaService;

@Service
@Transactional
public class FacturaServiceImpl implements FacturaService {

    private final ContratoRepository contratoRepository;
    private final FacturaRepository facturaRepository;
    private final HistorialEstadoFacturaRepository historialRepository;

    public FacturaServiceImpl(ContratoRepository contratoRepository, FacturaRepository facturaRepository, HistorialEstadoFacturaRepository historialRepository) {
        this.contratoRepository = contratoRepository;
        this.facturaRepository = facturaRepository;
        this.historialRepository = historialRepository;
    }

    @Override
    public List<FacturaDTO> findAll(ListarFacturasRequestDTO request) {
        return facturaRepository
                .filtrar(request.getEstado(), request.getContratoId(), request.getInquilinoId(), request.getPropiedadId(), request.getFechaVencDesde(), request.getFechaVencHasta())
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public FacturaDTO findById(Long id) {
        return facturaRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new IllegalArgumentException("Factura no encontrada con id: " + id));
    }

    @Override
    public void save(FacturaDTO dto) {
        validarDTO(dto);

        Factura factura = (dto.getId() != null)
                ? facturaRepository.findById(dto.getId())
                        .orElseThrow(() -> new RuntimeException("Factura no encontrada con id: " + dto.getId()))
                : new Factura();

        Contrato contrato = contratoRepository.findById(dto.getContrato().getId())
                .orElseThrow(() -> new RuntimeException("Contrato no encontrado con id: " + dto.getContrato().getId()));

        if (dto.getEstado() == EstadoFactura.PENDIENTE) {
            if (!contrato.isActivo()) {
                throw new IllegalStateException(
                        "Solo se pueden facturar un contrato activo.");
            }

            facturaRepository.findByContratoAndEstadoAndEliminadaFalse(contrato, EstadoFactura.PENDIENTE)
                    .ifPresent(p -> {
                        if (dto.getId() == null || !p.getId().equals(dto.getId())) {
                            throw new IllegalStateException(
                                    "Ya existe una publicación activa para esta propiedad.");
                        }
                    });
        }

        if (dto.getId() != null
                && factura.getEstado() != EstadoFactura.PENDIENTE
                && !factura.getConceptoFacturado().equals(dto.getConcepto())) {

            throw new IllegalStateException(
                    "No se pueden modificar las condiciones de una factura que no esta pendiente.");
        }

        EstadoFactura estadoAnterior = factura.getEstado();

        EstadoFactura nuevoEstado = dto.getEstado() != null
                ? dto.getEstado()
                : EstadoFactura.PENDIENTE;

        factura.setContrato(contrato);
        factura.setFechaEmision(dto.getFechaEmision());
        factura.setFechaVencimiento(dto.getFechaVencimiento());
        factura.setImporte(dto.getImporte());
        factura.setEstado(nuevoEstado);        
        factura.setEliminada(dto.getEliminada());
        factura.setFechaPago(dto.getFechaPago());
        factura.setMedioPago(dto.getMedioPago());
        factura.setImportePagado(dto.getImportePagado());
        factura.setInteres(dto.getInteres());
        factura.setConceptoFacturado(dto.getConcepto());
        

        facturaRepository.save(factura);
        if (dto.getId() == null || estadoAnterior != nuevoEstado) {
            HistorialEstadoFactura historial
                    = new HistorialEstadoFactura();

            historial.setPublicacion(factura);
            historial.setEstado(nuevoEstado);
            historial.setFechaHora(LocalDateTime.now());

            historialRepository.save(historial);
        }
    }

    private void validarDTO(FacturaDTO dto) {
        if (dto.getContrato().getId() == null) {
            throw new IllegalArgumentException("El contrato es obligatorio.");
        }
        if (dto.getFechaEmision() == null || !(tieneFormatoFechaCorrecto(dto.getFechaEmision().toString(), "yyyy-mm-dd")) )
        {
            throw new IllegalArgumentException("La fecha es nula o no tiene el formato valido yyyy-mm-dd");
        }
        if (dto.getEstado() == null) {
            throw new IllegalArgumentException("El estado de la publicación es obligatorio.");
        }
    }

    @Override
    public void delete(Long id) {
        Factura factura = facturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publicación no encontrada con id: " + id));

        if (factura.getEstado() != EstadoFactura.PENDIENTE) {
            throw new IllegalStateException("Solo se pueden eliminar factura en estado Pendiente.");
        }
        factura.setEliminada(true);
        facturaRepository.save(factura);
    }

    private FacturaDTO toDTO(Factura entity) {
    	FacturaDTO dto = new FacturaDTO();
        dto.setId(entity.getId());
        dto.setFechaEmision(entity.getFechaEmision());
        dto.setFechaVencimiento(entity.getFechaVencimiento());
        dto.setImporte(entity.getImporte());
        dto.setEstado(entity.getEstado());
        dto.setEliminada(entity.getEliminada());
        dto.setFechaPago(entity.getFechaPago());
        dto.setMedioPago(entity.getMedioPago());
        dto.setImportePagado(entity.getImportePagado());
        dto.setInteres(entity.getInteres());
        dto.setConcepto(entity.getConceptoFacturado());
        if (entity.getContrato() != null) {
            Contrato contrato = entity.getContrato();
            ContratoDTO contratoDTO = new ContratoDTO();
            contratoDTO.setId(contrato.getId());
            contratoDTO.setFechaInicio(contrato.getFechaInicio());
            contratoDTO.setFechaFin(contrato.getFechaFin());
            contratoDTO.setDiaPago(contrato.getDiaPago());
            contratoDTO.setEstado(contrato.getEstado());
            if (contrato.getInquilino() != null)
            	contratoDTO.setInquilinoId(contrato.getInquilino().getId());
            if (contrato.getPropiedad() != null)
            	contratoDTO.setPropiedadId(contrato.getPropiedad().getId());
            dto.setContrato(contratoDTO);
        }
        return dto;
    }
    
    public static boolean tieneFormatoFechaCorrecto(String dateStr, String formatPattern) {
        // Build a strict formatter using the specified pattern
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatPattern)
                .withResolverStyle(ResolverStyle.STRICT);

        try {
            // Attempt to parse the date string
            LocalDate.parse(dateStr, formatter);
            return true; 
        } catch (DateTimeParseException e) {
            return false; 
        }
    }
}
