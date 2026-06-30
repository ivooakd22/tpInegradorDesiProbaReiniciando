package tuti.desi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tuti.desi.enums.EstadoPropiedad;

import java.time.LocalDateTime;

@Entity
@Table(name = "estados_propiedades_logs")
@Getter
@Setter
@NoArgsConstructor
public class EstadoPropiedadLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "propiedad_id", nullable = false)
    private Propiedad propiedad;

    @Column(nullable = false)
    private LocalDateTime fechaCambio;

    private String responsable;

    @Enumerated(EnumType.STRING)
    private EstadoPropiedad estadoAnterior;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPropiedad estadoNuevo;
}
