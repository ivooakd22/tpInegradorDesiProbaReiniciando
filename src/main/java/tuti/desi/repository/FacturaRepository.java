package tuti.desi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tuti.desi.entity.Contrato;
import tuti.desi.entity.Factura;
import tuti.desi.enums.EstadoFactura;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FacturaRepository extends JpaRepository<Factura, Long> {

    @Query("SELECT f FROM Factura f "
            + "WHERE f.eliminada = false "
            + "AND (:estado IS NULL OR f.estado = :estado) "            
            + "AND (:contratoId IS NULL OR f.contrato.id = :contratoId) "
            + "AND (:propiedadId IS NULL OR f.contrato.propiedad.id = :propiedadId)"
            + "AND (:inquilinoId IS NULL OR f.contrato.inquilino.id = :inquilinoId)"
            + "AND (:fechaVencDesde IS NULL OR f.fechaVencimiento >= :fechaVencDesde) "
            + "AND (:fechaVencHasta IS NULL OR f.fechaVencimiento <= :fechaVencHasta)")
    List<Factura> filtrar(
            @Param("estado") EstadoFactura estado,
            @Param("contratoId") Integer contratoId,
            @Param("propiedadId") Integer propiedadId,
            @Param("inquilinoId") Integer inquilinoId,
            @Param("fechaVencDesde") LocalDate fechaVencDesde,
            @Param("fechaVencHasta") LocalDate fechaVencHasta
    );

    Optional<Factura> findByContratoAndEstadoAndEliminadaFalse(Contrato contrato, EstadoFactura estado);
}