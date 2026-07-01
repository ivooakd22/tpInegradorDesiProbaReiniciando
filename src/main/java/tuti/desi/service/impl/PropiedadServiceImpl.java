package tuti.desi.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tuti.desi.dto.ListarPropiedadesRequestDTO;
import tuti.desi.dto.PropiedadDTO;
import tuti.desi.entity.Propiedad;
import tuti.desi.entity.Propietario;
import tuti.desi.enums.EstadoPropiedad;
import tuti.desi.enums.EstadoContrato;
import tuti.desi.enums.EstadoPublicacion;
import tuti.desi.repository.ContratoRepository;
import tuti.desi.repository.PropiedadRepository;
import tuti.desi.repository.PropietarioRepository;
import tuti.desi.repository.PublicacionRepository;
import tuti.desi.service.EstadoPropiedadLogService;
import tuti.desi.service.PropiedadService;

import java.util.List;

@Service
@Transactional
public class PropiedadServiceImpl implements PropiedadService {

    private final PropiedadRepository propiedadRepository;
    private final PropietarioRepository propietarioRepository;
    private final ContratoRepository contratoRepository;
    private final PublicacionRepository publicacionRepository;
    private final EstadoPropiedadLogService logService;

    public PropiedadServiceImpl(
            PropiedadRepository propiedadRepository,
            PropietarioRepository propietarioRepository,
            ContratoRepository contratoRepository,
            PublicacionRepository publicacionRepository,
            EstadoPropiedadLogService logService) {
        this.propiedadRepository = propiedadRepository;
        this.propietarioRepository = propietarioRepository;
        this.contratoRepository = contratoRepository;
        this.publicacionRepository = publicacionRepository;
        this.logService = logService;
    }

    @Override
    public List<PropiedadDTO> findAll(ListarPropiedadesRequestDTO request) {
        String ciudad = (request.getCiudad() != null && !request.getCiudad().isBlank()) ? request.getCiudad() : null;
        String direccion = (request.getDireccion() != null && !request.getDireccion().isBlank()) ? request.getDireccion() : null;
        return propiedadRepository
                .filtrar(request.getEstado(), request.getTipo(), ciudad, direccion)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public PropiedadDTO findById(Long id) {
        return propiedadRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Propiedad no encontrada con id: " + id));
    }

    @Override
    public void save(PropiedadDTO dto) {
        
        // Valido datos requeridos. 
        validarDtoSaveEditar(dto);

        // Valido unicidad dirección + ciudad.
        validarDuplicidadDireccionCiudad(dto);

        // Obtengo propiedad.
        Propiedad propiedad = (dto.getId() != null)
                ? propiedadRepository
                    .findById(dto.getId())
                    .orElseThrow(() -> new RuntimeException("Propiedad no encontrada con id: " + dto.getId()))
                : new Propiedad();

        // Obtengo propietario.        
        Propietario propietario = propietarioRepository.findById(dto.getPropietarioId())
                .orElseThrow(() -> new RuntimeException("Propietario no encontrado con id: " + dto.getPropietarioId()));

        // Valido cambios de estado. 
        EstadoPropiedad estadoAnterior = propiedad.getEstado();
        EstadoPropiedad estadoNuevo = (dto.getEstado() != null) ? dto.getEstado() : EstadoPropiedad.DISPONIBLE;
        boolean hasCambioEstado = (estadoAnterior != null) ? (estadoAnterior != estadoNuevo) : false;

        // Si hay cambio de estado, valido que la propiedad no tenga contrato activo. 
        if(hasCambioEstado) validarCambioEstado(dto);

        // Mapeo DTO > Entity.
        propiedad.setDireccion(dto.getDireccion());
        propiedad.setBarrio(dto.getBarrio());
        propiedad.setCiudad(dto.getCiudad());
        propiedad.setDescripcion(dto.getDescripcion());
        propiedad.setTipo(dto.getTipo());
        propiedad.setAmbientes(dto.getAmbientes());
        propiedad.setMetrosCuadrados(dto.getMetrosCuadrados());
        propiedad.setEstado(estadoNuevo);
        propiedad.setPropietario(propietario);

        // Guado Entity
        Propiedad saved = propiedadRepository.save(propiedad);

        // Si hay cambio de estado guardo log
        if (hasCambioEstado) {
            logService.registrar(saved, estadoAnterior, estadoNuevo);
        }
    }

    @Override
    public void delete(Long id) {
        Propiedad propiedad = propiedadRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Propiedad no encontrada con id: " + id));

        if (contratoRepository.existsByPropiedadIdAndEstadoAndEliminadoFalse(id, EstadoContrato.ACTIVO)) {
            throw new IllegalStateException("No se puede eliminar: la propiedad tiene contratos activos.");
        }
        if (publicacionRepository.existsByPropiedadIdAndEstadoAndEliminadaFalse(id, EstadoPublicacion.ACTIVA)) {
            throw new IllegalStateException("No se puede eliminar: la propiedad tiene publicaciones activas.");
        }

        //propiedad.setActivo(false);
        propiedadRepository.save(propiedad);
    }

    private void validarDtoSaveEditar(PropiedadDTO dto) {
        if (dto.getDireccion() == null || dto.getDireccion().isBlank())
            throw new IllegalArgumentException("La dirección es obligatoria.");
        if (dto.getCiudad() == null || dto.getCiudad().isBlank())
            throw new IllegalArgumentException("La ciudad es obligatoria.");
        if (dto.getDescripcion() == null || dto.getDescripcion().isBlank())
            throw new IllegalArgumentException("La descripción es obligatoria.");
        if (dto.getTipo() == null)
            throw new IllegalArgumentException("El tipo de propiedad es obligatorio.");
        if (dto.getMetrosCuadrados() == null || dto.getMetrosCuadrados() <= 0)
            throw new IllegalArgumentException("Los metros cuadrados deben ser mayores a 0.");
        if (dto.getAmbientes() == null || dto.getAmbientes() <= 0)
            throw new IllegalArgumentException("Los ambientes deben ser mayores a 0.");
        if (dto.getPropietarioId() == null)
            throw new IllegalArgumentException("El propietario es obligatorio.");
    }

    private void validarDuplicidadDireccionCiudad(PropiedadDTO dto) {
        Long excludeId = (dto.getId() != null) ? dto.getId() : 0L;
        if (propiedadRepository.existsByDireccionIgnoreCaseAndCiudadIgnoreCaseAndActivoTrueAndIdNot(
                dto.getDireccion(), dto.getCiudad(), excludeId)) {
            throw new IllegalArgumentException(
                    "Ya existe una propiedad activa con esa dirección en esa ciudad.");
        }
    }

    private void validarCambioEstado(PropiedadDTO dto) {
        boolean tieneContratoActivo = contratoRepository
            .existsByPropiedadIdAndEstadoAndEliminadoFalse(dto.getId(), EstadoContrato.ACTIVO);
        if(tieneContratoActivo) throw new IllegalStateException(
            "No se puede cambiar el estado: la propiedad tiene un contrato activo.");
    }

    private PropiedadDTO toDTO(Propiedad p) {
        PropiedadDTO dto = new PropiedadDTO();
        dto.setId(p.getId());
        dto.setDireccion(p.getDireccion());
        dto.setBarrio(p.getBarrio());
        dto.setCiudad(p.getCiudad());
        dto.setDescripcion(p.getDescripcion());
        dto.setTipo(p.getTipo());
        dto.setAmbientes(p.getAmbientes());
        dto.setMetrosCuadrados(p.getMetrosCuadrados());
        dto.setEstado(p.getEstado());
        if (p.getPropietario() != null) {
            dto.setPropietarioId(p.getPropietario().getId());
            dto.setPropietarioNombre(p.getPropietario().getNombre() + " " + p.getPropietario().getApellido());
        }
        return dto;
    }

}
