package tuti.desi.dto;

import lombok.*;
import tuti.desi.enums.EstadoPropiedad;
import tuti.desi.enums.TipoPropiedad;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PropiedadDTO {

    private Long id;

    private String direccion;

    private String barrio;

    private String ciudad;

    private TipoPropiedad tipo;

    private Integer ambientes;

    private Integer metrosCuadrados;

    private EstadoPropiedad estado;

    private Long propietarioId;
}
