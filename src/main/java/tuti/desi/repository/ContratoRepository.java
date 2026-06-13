package tuti.desi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tuti.desi.entity.Contrato;
import tuti.desi.enums.EstadoContrato;

import java.util.List;

public interface ContratoRepository extends JpaRepository<Contrato, Long> {

    @Query("SELECT c FROM Contrato c WHERE (:estado IS NULL OR c.estado = :estado)")
    List<Contrato> filtrar(@Param("estado") EstadoContrato estado);
}
