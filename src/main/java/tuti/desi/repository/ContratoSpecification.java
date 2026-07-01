
package tuti.desi.repository;
import org.springframework.data.jpa.domain.Specification;
import tuti.desi.entity.Contrato;
import tuti.desi.enums.EstadoContrato;

public class ContratoSpecification {
  public static Specification<Contrato> conEstado(EstadoContrato estado) {
        return (root, query, criteriaBuilder) -> {
            if (estado == null) {
                return criteriaBuilder.conjunction(); // Equivale a un "WHERE 1=1" (no filtra nada)
            }
            return criteriaBuilder.equal(root.get("estado"), estado);
        };
    }  
}
