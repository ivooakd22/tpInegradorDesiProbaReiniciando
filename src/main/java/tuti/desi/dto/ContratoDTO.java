package tuti.desi.dto;

import lombok.*;
import tuti.desi.enums.EstadoContrato;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContratoDTO {

    private Long id;

    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    private Double montoMensual;

    private Integer diaPago;

    private EstadoContrato estado;

    private Long propiedadId;

    private Long inquilinoId;
}
