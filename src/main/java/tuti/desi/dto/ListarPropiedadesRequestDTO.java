package tuti.desi.dto;

import lombok.*;
import tuti.desi.enums.EstadoPropiedad;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListarPropiedadesRequestDTO {

    private EstadoPropiedad estado;
}
