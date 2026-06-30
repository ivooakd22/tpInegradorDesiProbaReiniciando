package tuti.desi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tuti.desi.entity.Propiedad;
import tuti.desi.enums.EstadoPropiedad;
import tuti.desi.enums.TipoPropiedad;

import java.util.List;

public interface PropiedadRepository extends JpaRepository<Propiedad, Long> {

    @Query("SELECT p FROM Propiedad p WHERE p.activo = true AND (:estado IS NULL OR p.estado = :estado) AND (:tipo IS NULL OR p.tipo = :tipo) AND (:ciudad IS NULL OR LOWER(p.ciudad) LIKE LOWER(CONCAT('%', :ciudad, '%')))")
    List<Propiedad> filtrar(
            @Param("estado") EstadoPropiedad estado,
            @Param("tipo") TipoPropiedad tipo,
            @Param("ciudad") String ciudad
    );
}
