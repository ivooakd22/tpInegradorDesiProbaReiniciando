package tuti.desi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tuti.desi.entity.Publicacion;
import tuti.desi.enums.EstadoPublicacion;

import java.util.List;
import java.util.Optional;
import tuti.desi.entity.Propiedad;

public interface PublicacionRepository extends JpaRepository<Publicacion, Long> {

    @Query("SELECT p FROM Publicacion p "
            + "WHERE (:estado IS NULL OR p.estado = :estado) "
            + "AND (:ciudad IS NULL OR LOWER(p.propiedad.ciudad) LIKE LOWER(CONCAT('%', :ciudad, '%'))) "
            + "AND (:propiedadId IS NULL OR p.propiedad.id = :propiedadId) "
            + "AND (:precioMin IS NULL OR p.precioMensual >= :precioMin) "
            + "AND (:precioMax IS NULL OR p.precioMensual <= :precioMax)")
    List<Publicacion> filtrar(
            @Param("estado") EstadoPublicacion estado,
            @Param("ciudad") String ciudad,
            @Param("propiedadId") Integer propiedadId,
            @Param("precioMin") Integer precioMin,
            @Param("precioMax") Integer precioMax
    );

    Optional<Publicacion> findByPropiedadAndEstado(Propiedad propiedad, EstadoPublicacion estado);
}
