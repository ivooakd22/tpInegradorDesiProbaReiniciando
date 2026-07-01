package tuti.desi.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import tuti.desi.entity.HistorialEstadoFactura;

public interface HistorialEstadoFacturaRepository extends JpaRepository<HistorialEstadoFactura, Long> {

    List<HistorialEstadoFactura> findByFacturaId(Long facturaId);
}
