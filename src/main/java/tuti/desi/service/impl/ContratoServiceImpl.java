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
import java.util.Optional;

@Service

@Transactional
public class ContratoServiceImpl implements ContratoService {

    @Autowired
    private ContratoRepository contratoRepository;

    @Autowired
    private PropiedadRepository propiedadRepository;
    @Autowired
    private InquilinoRepository inquilinoRepository;

    public ContratoServiceImpl() {
    }

    @Override
    public List<ContratoDTO> findAll(ListarContratosRequestDTO request) {
        
        EstadoContrato estadoEnum = null;
        if(request.getEstado() != null && !request.getEstado().isEmpty()){
            estadoEnum = EstadoContrato.valueOf(request.getEstado().toUpperCase());
        }
        List<Contrato> contratos = contratoRepository.filtrar(
                request.getPropiedadId(),
                request.getInquilinoId(),
                estadoEnum,
                request.getFechaInicio());

        return contratos.stream().map(c -> {
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
            }
            if (c.getInquilino() != null) {
                dto.setInquilinoId(c.getInquilino().getId());
            }
            return dto;
        }).collect(java.util.stream.Collectors.toList());
    }

    @Override
    public ContratoDTO findById(Long id) {
        Optional<Contrato> contratoOpt = contratoRepository.findById(id);
        if (contratoOpt.isPresent()) {
            Contrato c = contratoOpt.get();
            //salida
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
            }
            if (c.getInquilino() != null) {
                dto.setInquilinoId(c.getInquilino().getId());
            }
            return dto;
        }
        return null;
    }

    @Override
    public void save(ContratoDTO dto) {
    // ver si el id ya existe
    if (dto.getId() != null) {
        // vemos el contrato actual antes de los cambios
        Contrato contratoActual = contratoRepository.findById(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el contrato a modificar."));
        
        EstadoContrato estadoAnterior = contratoActual.getEstado();
        EstadoContrato estadoNuevo = EstadoContrato.valueOf(dto.getEstado());
        
        // no se puede volver a Activo desde finalizado o rescindido
        if ((estadoAnterior == EstadoContrato.FINALIZADO || estadoAnterior == EstadoContrato.RESCINDIDO) 
                && estadoNuevo == EstadoContrato.ACTIVO) {
            throw new IllegalArgumentException("Regla de negocio violada: No se permite activar un contrato que ya está FINALIZADO o RESCINDIDO.");
        }
        
        // actualiza propiedad según estado del contrato
        if (estadoNuevo == EstadoContrato.ACTIVO || estadoNuevo == EstadoContrato.FINALIZADO || estadoNuevo == EstadoContrato.RESCINDIDO) {
            // Buscamos la propiedad asociada al contrato
            var propiedad = propiedadRepository.findById(dto.getPropiedadId())
                    .orElseThrow(() -> new IllegalArgumentException("No se encontró la propiedad asociada al contrato."));
        
            if (estadoNuevo == EstadoContrato.ACTIVO) {
                propiedad.setEstado(EstadoPropiedad.ALQUILADA);
            } else {
                // Si está Finalizado o Rescindido, queda disponible
                propiedad.setEstado(EstadoPropiedad.DISPONIBLE);
            }
            // Guardamos el cambio en la propiedad
            propiedadRepository.save(propiedad);
        }
    }
        
//validar
        if (dto.getFechaInicio() == null || dto.getMontoMensual() == null
                || dto.getDiaPago() == null || dto.getPropiedadId() == null
                || dto.getInquilinoId() == null || dto.getDuracionMeses() == null) {
            throw new IllegalArgumentException("Es necesario ingresar todos los campos obligatorios para registrar el contrato");
        }
        //validar num
        if (dto.getMontoMensual() <= 0) {
            throw new IllegalArgumentException("El importe mensual debe ser un número positivo.");
        }
        if (dto.getDuracionMeses() <= 0) {
            throw new IllegalArgumentException("La duración del contrato debe ser un número positivo.");
        }
        if (dto.getDiaPago() < 1 || dto.getDiaPago() > 31) {
            throw new IllegalArgumentException("El día de vencimiemto debe ser un número entre 1 y 31");
        }

        //busc propiedad
        Propiedad propiedad = propiedadRepository.findById(dto.getPropiedadId())
                .orElseThrow(() -> new RuntimeException("La propiedad buscada no existe"));

        //buscar inqui y asoc
        Inquilino inquilino = inquilinoRepository.findById(dto.getInquilinoId())
                .orElseThrow(() -> new RuntimeException("l inquilino que se busca no existe"));

        //buscar contrato
        Contrato contrato;
        EstadoContrato nuevoEstado;

        if (dto.getId() != null) {
            contrato = contratoRepository.findById(dto.getId())
                    .orElseThrow(() -> new RuntimeException("No se encontró el contrato " + dto.getId()));

            nuevoEstado = dto.getEstado() != null ? EstadoContrato.valueOf(dto.getEstado().toUpperCase()) : contrato.getEstado();

        } else {
            contrato = new Contrato();
            nuevoEstado = dto.getEstado() != null ? EstadoContrato.valueOf(dto.getEstado().toUpperCase()) : EstadoContrato.BORRADOR;
        }

        //estado activo
        if (nuevoEstado == EstadoContrato.ACTIVO) {
            if (propiedad.getEstado() != null && !"DISPONIBLE".equalsIgnoreCase(propiedad.getEstado().toString())) {
                throw new IllegalArgumentException("No se puede crear un contrato si le propiedad no esta disponible");
            }
            //solo 1 contrato
            boolean tieneOtroActivo = contratoRepository.existsByPropiedadIdAndEstado(propiedad.getId(), EstadoContrato.ACTIVO);
            if (tieneOtroActivo) {
                throw new IllegalArgumentException("Una propiedad no podrá tener más de un contrato activo.");
            }
            propiedadRepository.save(propiedad);
        }

        //mapear datos dto a la entidad
        contrato.setFechaInicio(dto.getFechaInicio());

        if (dto.getEstado() != null) {
            contrato.setFechaFin(dto.getFechaFin());
        } else {
            contrato.setFechaFin(dto.getFechaInicio().plusMonths(dto.getDuracionMeses()));
        }

        contrato.setMontoMensual(dto.getMontoMensual());
        contrato.setDiaPago(dto.getDiaPago());
        contrato.setEstado(nuevoEstado);
        contrato.setPropiedad(propiedad);
        contrato.setInquilino(inquilino);

        //guardar
        contratoRepository.save(contrato);
    }

    @Override
    public void delete(Long id) {
        //busco el contrato en la base
        Contrato contrato = contratoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el contrato: " + id));
        //ver que solo esta en borrador
        if (contrato.getEstado() != EstadoContrato.BORRADOR) {
            throw new IllegalArgumentException("No se puede eliminar el contrato actual. Solo se pueden borrar contratos en estado Borrador");
        }
        contratoRepository.delete(contrato);
    }
}
