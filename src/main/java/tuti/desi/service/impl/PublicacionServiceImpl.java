package tuti.desi.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tuti.desi.dto.ListarPublicacionesRequestDTO;
import tuti.desi.dto.PropiedadDTO;
import tuti.desi.dto.PublicacionDTO;
import tuti.desi.entity.HistorialEstadoPublicacion;
import tuti.desi.entity.Propiedad;
import tuti.desi.entity.Publicacion;
import tuti.desi.enums.EstadoPublicacion;
import tuti.desi.repository.HistorialEstadoPublicacionRepository;
import tuti.desi.repository.PropiedadRepository;
import tuti.desi.repository.PublicacionRepository;
import tuti.desi.service.PublicacionService;

@Service
@Transactional
public class PublicacionServiceImpl implements PublicacionService {

    private final PropiedadRepository propiedadRepository;
    private final PublicacionRepository publicacionRepository;
    private final HistorialEstadoPublicacionRepository historialRepository;

    public PublicacionServiceImpl(PropiedadRepository propiedadRepository,
            PublicacionRepository publicacionRepository, HistorialEstadoPublicacionRepository historialRepository) {
        this.propiedadRepository = propiedadRepository;
        this.publicacionRepository = publicacionRepository;
        this.historialRepository = historialRepository;
    }

    @Override
    public List<PublicacionDTO> findAll(ListarPublicacionesRequestDTO request) {
        return publicacionRepository
                .filtrar(request.getEstado(), request.getCiudad(), request.getPropiedadId(), request.getPrecioMin(), request.getPrecioMax())
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public PublicacionDTO findById(Long id) {
        return publicacionRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new IllegalArgumentException("Publicación no encontrada con id: " + id));
    }

    @Override
    public void save(PublicacionDTO dto) {
        validarDtoSaveEditar(dto);

        Publicacion publicacion = (dto.getId() != null)
                ? publicacionRepository.findById(dto.getId())
                        .orElseThrow(() -> new RuntimeException("Publicación no encontrada con id: " + dto.getId()))
                : new Publicacion();

        Propiedad propiedad = propiedadRepository.findById(dto.getPropiedad().getId())
                .orElseThrow(() -> new RuntimeException("Propiedad no encontrada con id: " + dto.getPropiedad().getId()));

        if (dto.getEstado() == EstadoPublicacion.ACTIVA) {
            if (!propiedad.isDisponible()) {
                throw new IllegalStateException(
                        "Solo se pueden publicar propiedades disponibles.");
            }

            publicacionRepository.findByPropiedadAndEstadoAndEliminadaFalse(propiedad, EstadoPublicacion.ACTIVA)
                    .ifPresent(p -> {
                        if (dto.getId() == null || !p.getId().equals(dto.getId())) {
                            throw new IllegalStateException(
                                    "Ya existe una publicación activa para esta propiedad.");
                        }
                    });
        }

        if (dto.getId() != null
                && publicacion.getEstado() == EstadoPublicacion.FINALIZADA
                && !publicacion.getCondiciones().equals(dto.getCondiciones())) {

            throw new IllegalStateException(
                    "No se pueden modificar las condiciones de una publicación finalizada.");
        }

        EstadoPublicacion estadoAnterior = publicacion.getEstado();

        EstadoPublicacion nuevoEstado = dto.getEstado() != null
                ? dto.getEstado()
                : EstadoPublicacion.ACTIVA;

        publicacion.setPropiedad(propiedad);
        publicacion.setPrecioMensual(dto.getPrecioMensual());
        publicacion.setCondiciones(dto.getCondiciones());
        publicacion.setDescripcion(dto.getDescripcion());
        publicacion.setFechaPublicacion(dto.getFechaPublicacion());
        publicacion.setEstado(nuevoEstado);
        publicacion.setEliminada(dto.getEliminada());

        publicacionRepository.save(publicacion);
        if (dto.getId() == null || estadoAnterior != nuevoEstado) {
            HistorialEstadoPublicacion historial
                    = new HistorialEstadoPublicacion();

            historial.setPublicacion(publicacion);
            historial.setEstado(nuevoEstado);
            historial.setFechaHora(LocalDateTime.now());

            historialRepository.save(historial);
        }
    }

    private void validarDtoSaveEditar(PublicacionDTO dto) {
        if (dto.getPropiedad().getId() == null) {
            throw new IllegalArgumentException("La propiedad es obligatoria.");
        }
        if (dto.getPrecioMensual() == null || dto.getPrecioMensual() <= 0) {
            throw new IllegalArgumentException("El precio mensual debe ser mayor a 0.");
        }
        if (dto.getCondiciones() == null || dto.getCondiciones().isBlank()) {
            throw new IllegalArgumentException("Las condiciones son obligatorias.");
        }
        if (dto.getDescripcion() == null || dto.getDescripcion().isBlank()) {
            throw new IllegalArgumentException("La descripción es obligatoria.");
        }
        if (dto.getFechaPublicacion() == null) {
            throw new IllegalArgumentException("La fecha de publicación es obligatoria.");
        }
        if (dto.getEstado() == null) {
            throw new IllegalArgumentException("El estado de la publicación es obligatorio.");
        }
    }

    @Override
    public void delete(Long id) {
        Publicacion publicacion = publicacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publicación no encontrada con id: " + id));

        if (publicacion.getEstado() != EstadoPublicacion.ACTIVA) {
            throw new IllegalStateException("Solo se pueden eliminar publicaciones en estado ACTIVA.");
        }
        publicacion.setEliminada(true);
        publicacionRepository.save(publicacion);
    }

    private PublicacionDTO toDTO(Publicacion entity) {
        PublicacionDTO dto = new PublicacionDTO();
        dto.setId(entity.getId());
        dto.setPrecioMensual(entity.getPrecioMensual());
        dto.setCondiciones(entity.getCondiciones());
        dto.setDescripcion(entity.getDescripcion());
        dto.setFechaPublicacion(entity.getFechaPublicacion());
        dto.setEstado(entity.getEstado());
        dto.setEliminada(entity.getEliminada());
        if (entity.getPropiedad() != null) {
            Propiedad propiedad = entity.getPropiedad();
            PropiedadDTO propiedadDTO = new PropiedadDTO();
            propiedadDTO.setId(propiedad.getId());
            propiedadDTO.setDireccion(propiedad.getDireccion());
            propiedadDTO.setBarrio(propiedad.getBarrio());
            propiedadDTO.setCiudad(propiedad.getCiudad());
            propiedadDTO.setTipo(propiedad.getTipo());
            propiedadDTO.setEstado(propiedad.getEstado());
            propiedadDTO.setMetrosCuadrados(propiedad.getMetrosCuadrados());
            propiedadDTO.setAmbientes(propiedad.getAmbientes());
            dto.setPropiedad(propiedadDTO);
        }
        return dto;
    }
}
