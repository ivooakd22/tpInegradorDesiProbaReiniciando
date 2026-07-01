package tuti.desi.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import tuti.desi.entity.HistorialEstadoContrato;

public interface HistorialEstadoContratoRepository extends JpaRepository<HistorialEstadoContrato, Long> {

        List<HistorialEstadoContrato> findByContratoId(Long contratoId);
}
