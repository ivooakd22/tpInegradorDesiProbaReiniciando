package tuti.desi.dto;

import lombok.*;
import tuti.desi.enums.EstadoContrato;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListarContratosRequestDTO {

    private EstadoContrato estado;
}
