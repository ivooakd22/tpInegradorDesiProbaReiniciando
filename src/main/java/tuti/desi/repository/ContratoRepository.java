package tuti.desi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tuti.desi.entity.Contrato;
import tuti.desi.enums.EstadoContrato;

import java.util.List;
import java.time.LocalDate; 
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Long> {

    
    List<Contrato> findByEliminadoFalse();

    
    boolean existsByPropiedadIdAndEstado(Long propiedadId, EstadoContrato estado);

    
    @Query("SELECT c FROM Contrato c WHERE " +
           "(:propiedadId IS NULL OR c.propiedad.id = :propiedadId) AND " +
           "(:inquilinoId IS NULL OR c.inquilino.id = :inquilinoId) AND " +
           "(:estado IS NULL OR c.estado = :estado) AND " +
           "(:fechaInicio IS NULL OR c.fechaInicio = :fechaInicio) AND " +
           "c.eliminado = false")
    List<Contrato> filtrar(@Param("propiedadId") Long propiedadId,
                           @Param("inquilinoId") Long inquilinoId,
                           @Param("estado") EstadoContrato estado,
                           @Param("fechaInicio") LocalDate fechaInicio);

   
    List<Contrato> findByInquilinoId(Long inquilinoId);

  
    List<Contrato> findByPropiedadId(Long propiedadId);

    
    boolean existsByPropiedadIdAndEstadoAndEliminadoFalse(Long propiedadId, EstadoContrato estado);
    
    
}

