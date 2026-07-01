package tuti.desi.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import tuti.desi.entity.HistorialEstadoPublicacion;

public interface HistorialEstadoPublicacionRepository extends JpaRepository<HistorialEstadoPublicacion, Long> {

    List<HistorialEstadoPublicacion> findByPublicacionId(Long publicacionId);
}
