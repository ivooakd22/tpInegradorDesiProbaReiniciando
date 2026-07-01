package tuti.desi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tuti.desi.enums.EstadoIncidente;

import java.time.LocalDateTime;

@Entity
@Table(name = "historial_incidentes")
@Getter
@Setter
@NoArgsConstructor
public class HistorialIncidentes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "incidente_id", nullable = false)
    private Incidente incidente;

    @Column(nullable = false)
    private LocalDateTime fechaHora;

    @Enumerated(EnumType.STRING)
    private EstadoIncidente estadoIncidente;
}
