package tuti.desi.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.*;
import tuti.desi.enums.CategoriaIncidente;
import tuti.desi.enums.EstadoIncidente;

@Entity
@Table(name = "incidentes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Incidente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contrato_id", nullable = false)
    private Contrato contrato;

    @Column(length = 100, nullable = false)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CategoriaIncidente categoriaIncidente;

    @Column(name = "fecha_alta", nullable = false)
    private LocalDateTime fechaAlta;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "propiedad_id", nullable = false)
    private Propiedad propiedad;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoIncidente estado;
    
    @Column(nullable = false)
    private Boolean eliminado = false;
    
    @Column(name = "fecha_resolucion", nullable = false)
    private LocalDateTime fechaResolucion;

    @Column(name = "costo_resolucion", nullable = false)
    private BigDecimal costoResolucion;

    @Column(name = "observaciones_resolucion", nullable = false, length = 200)
    private String observacionesResolucion;

    @Column(name = "responsable_tecnico", nullable = false, length = 200)
    private String responsableTecnico;
}
