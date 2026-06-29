package tuti.desi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tuti.desi.entity.Contrato;
import tuti.desi.enums.EstadoContrato;

import java.util.List;
import java.time.LocalDate; 

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Long> {

   
    List<Contrato> filtrar(Long propiedadId, Long inquilinoId, EstadoContrato estado, java.time.LocalDate fechaInicio);

    // Busc inquilino
    List<Contrato> findByInquilinoId(Long inquilinoId);

    // Buscpropiedad
    List<Contrato> findByPropiedadId(Long propiedadId);

    
    boolean existsByPropiedadIdAndEstado(Long propiedadId, EstadoContrato estado);
}

