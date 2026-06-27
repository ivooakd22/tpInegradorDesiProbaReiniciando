
package tuti.desi.dto;

import lombok.Getter;
import lombok.Setter;
import tuti.desi.enums.EstadoPropiedad;

@Getter
@Setter
public class ListarPropiedadesRequestDTO {
    private EstadoPropiedad estado;

    public EstadoPropiedad getEstado() {
        return estado;
    }

    public void setEstado(EstadoPropiedad estado) {
        this.estado = estado;
    }
}
