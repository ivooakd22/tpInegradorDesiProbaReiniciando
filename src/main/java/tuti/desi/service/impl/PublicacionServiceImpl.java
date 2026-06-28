package tuti.desi.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tuti.desi.dto.ListarPublicacionesRequestDTO;
import tuti.desi.dto.PropiedadDTO;
import tuti.desi.dto.PublicacionDTO;
import tuti.desi.entity.Propiedad;
import tuti.desi.entity.Publicacion;
import tuti.desi.enums.EstadoPublicacion;
import tuti.desi.repository.PropiedadRepository;
import tuti.desi.repository.PublicacionRepository;
import tuti.desi.service.PublicacionService;

@Service
@Transactional
public class PublicacionServiceImpl implements PublicacionService {

    private final PropiedadRepository propiedadRepository;
    private final PublicacionRepository publicacionRepository;

    public PublicacionServiceImpl(PropiedadRepository propiedadRepository,
            PublicacionRepository publicacionRepository) {
        this.propiedadRepository = propiedadRepository;
        this.publicacionRepository = publicacionRepository;
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

        // Si viene con id, buscamos la publicación existente, si no, creamos una nueva
        Publicacion publicacion = (dto.getId() != null)
                ? publicacionRepository.findById(dto.getId())
                        .orElseThrow(() -> new RuntimeException("Publicación no encontrada con id: " + dto.getId()))
                : new Publicacion();

        // Validamos que la propiedad exista
        Propiedad propiedad = propiedadRepository.findById(dto.getPropiedad().getId())
                .orElseThrow(() -> new RuntimeException("Propiedad no encontrada con id: " + dto.getPropiedad().getId()));

        // Reglas de negocio del Epic 2
        if (!propiedad.isDisponible()) {
            throw new IllegalStateException("Solo se pueden publicar propiedades disponibles.");
        }

        // No puede haber más de una publicación activa para la misma propiedad
        publicacionRepository.findByPropiedadAndEstado(propiedad, EstadoPublicacion.ACTIVA)
                .ifPresent(p -> {
                    if (dto.getId() == null || !p.getId().equals(dto.getId())) {
                        throw new IllegalStateException("Ya existe una publicación activa para esta propiedad.");
                    }
                });

        // Seteamos los datos
        publicacion.setPropiedad(propiedad);
        publicacion.setPrecioMensual(dto.getPrecioMensual());
        publicacion.setCondiciones(dto.getCondiciones());
        publicacion.setDescripcion(dto.getDescripcion());
        publicacion.setFechaPublicacion(dto.getFechaPublicacion());
        publicacion.setEstado(dto.getEstado() != null ? dto.getEstado() : EstadoPublicacion.ACTIVA);

        publicacionRepository.save(publicacion);
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
        publicacionRepository.deleteById(id);
    }

    private PublicacionDTO toDTO(Publicacion entity) {
        PublicacionDTO dto = new PublicacionDTO();
        dto.setId(entity.getId());
        dto.setPrecioMensual(entity.getPrecioMensual());
        dto.setCondiciones(entity.getCondiciones());
        dto.setDescripcion(entity.getDescripcion());
        dto.setFechaPublicacion(entity.getFechaPublicacion());
        dto.setEstado(entity.getEstado());
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
