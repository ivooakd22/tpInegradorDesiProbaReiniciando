package tuti.desi.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tuti.desi.entity.Contrato;
import tuti.desi.enums.EstadoContrato;

import java.util.List;
import java.time.LocalDate; // Agregamos este import para la fecha

public interface ContratoRepository extends JpaRepository<Contrato, Long> {

    @Query("SELECT c FROM Contrato c WHERE " +
           "(:propiedadId IS NULL OR c.propiedad.id = :propiedadId) AND " +
           "(:inquilinoId IS NULL OR c.inquilino.id = :inquilinoId) AND " +
           "(:estado IS NULL OR c.estado = :estado) AND " +
           "(:fechaInicio IS NULL OR c.fechaInicio = :fechaInicio)")
    List<Contrato> filtrar(
            @Param("propiedadId") Long propiedadId,
            @Param("inquilinoId") Long inquilinoId,
            @Param("estado") EstadoContrato estado,
            @Param("fechaInicio") LocalDate fechaInicio
    );
    
    boolean existsByPropiedadIdAndEstado(Long propiedadId, EstadoContrato estado);
}