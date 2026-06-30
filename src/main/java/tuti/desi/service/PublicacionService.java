package tuti.desi.service;

import tuti.desi.dto.ListarPublicacionesRequestDTO;
import tuti.desi.dto.PublicacionDTO;
import java.util.List;

public interface PublicacionService {

    List<PublicacionDTO> findAll(ListarPublicacionesRequestDTO request);

    PublicacionDTO findById(Long id);

    void save(PublicacionDTO dto);

    void delete(Long id);
}
