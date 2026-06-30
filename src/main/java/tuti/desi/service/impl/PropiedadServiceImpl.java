//package tuti.desi.service.impl;
//
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import tuti.desi.dto.ListarPropiedadesRequestDTO;
//import tuti.desi.dto.PropiedadDTO;
//import tuti.desi.entity.Propiedad;
//import tuti.desi.entity.Propietario;
//import tuti.desi.repository.PropiedadRepository;
//import tuti.desi.repository.PropietarioRepository;
//import tuti.desi.service.PropiedadService;
//
//import java.util.List;
//
//@Service
//@Transactional
//public class PropiedadServiceImpl implements PropiedadService {
//
//    private final PropiedadRepository propiedadRepository;
//    private final PropietarioRepository propietarioRepository;
//
//    public PropiedadServiceImpl(
//        PropiedadRepository propiedadRepository,
//        PropietarioRepository propietarioRepository) {
//        this.propiedadRepository = propiedadRepository;
//        this.propietarioRepository = propietarioRepository;
//    }
//
//    @Override
//    public List<PropiedadDTO> findAll(ListarPropiedadesRequestDTO request) {
//        return propiedadRepository
//                .filtrar(request.getEstado(), null, null)
//                .stream()
//                .map(this::toDTO)
//                .toList();
//    }
//
//    @Override
//    public PropiedadDTO findById(Long id) {
//        return propiedadRepository.findById(id)
//                .map(this::toDTO)
//                .orElseThrow(() -> new RuntimeException("Propiedad no encontrada con id: " + id));
//    }
//
//    @Override
//    public void save(PropiedadDTO dto) {
//        validarDtoSaveEditar(dto);
//
//        Propiedad propiedad = (dto.getId() != null) 
//            ? propiedadRepository
//                .findById(dto.getId())
//                .orElseThrow(() -> new RuntimeException("Propiedad no encontrada con id: " + dto.getId()))
//            : new Propiedad();
//
//        Propietario propietario = propietarioRepository.findById(dto.getPropietarioId())
//                .orElseThrow(() -> new RuntimeException("Propietario no encontrado con id: " + dto.getPropietarioId()));
//
//        propiedad.setDireccion(dto.getDireccion());
//        propiedad.setBarrio(dto.getBarrio());
//        propiedad.setCiudad(dto.getCiudad());
//        propiedad.setTipo(dto.getTipo());
//        propiedad.setAmbientes(dto.getAmbientes());
//        propiedad.setMetrosCuadrados(dto.getMetrosCuadrados());
//        propiedad.setEstado(dto.getEstado());
//        propiedad.setPropietario(propietario);
//
//        propiedadRepository.save(propiedad);
//
//    }
//
//    @Override
//    public void delete(Long id) {
//        if (!propiedadRepository.existsById(id)) {
//            throw new RuntimeException("Propiedad no encontrada con id: " + id);
//        }
//        propiedadRepository.deleteById(id);
//    }
//
//    private void validarDtoSaveEditar(PropiedadDTO dto) {
//        if (dto.getDireccion() == null || dto.getDireccion().isBlank())
//            throw new IllegalArgumentException("La dirección es obligatoria.");
//        if (dto.getCiudad() == null || dto.getCiudad().isBlank())
//            throw new IllegalArgumentException("La ciudad es obligatoria.");
//        if (dto.getTipo() == null)
//            throw new IllegalArgumentException("El tipo de propiedad es obligatorio.");
//        if (dto.getMetrosCuadrados() == null || dto.getMetrosCuadrados() <= 0)
//            throw new IllegalArgumentException("Los metros cuadrados deben ser mayores a 0.");
//        if (dto.getAmbientes() == null || dto.getAmbientes() <= 0)
//            throw new IllegalArgumentException("Los ambientes deben ser mayores a 0.");
//        if (dto.getPropietarioId() == null)
//            throw new IllegalArgumentException("El propietario es obligatorio.");
//    }
//
//    private PropiedadDTO toDTO(Propiedad p) {
//        PropiedadDTO dto = new PropiedadDTO();
//        dto.setId(p.getId());
//        dto.setDireccion(p.getDireccion());
//        dto.setBarrio(p.getBarrio());
//        dto.setCiudad(p.getCiudad());
//        dto.setTipo(p.getTipo());
//        dto.setAmbientes(p.getAmbientes());
//        dto.setMetrosCuadrados(p.getMetrosCuadrados());
//        dto.setEstado(p.getEstado());
//        if (p.getPropietario() != null) {
//            dto.setPropietarioId(p.getPropietario().getId());
//        }
//        return dto;
//    }
//
//}
