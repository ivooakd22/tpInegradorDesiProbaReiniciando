package tuti.desi.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;
import tuti.desi.enums.EstadoVisita;

@Entity
@Table(name = "visitas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Visita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "publicacion_id", nullable = false)
    private Publicacion publicacion;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaPublicacion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoVisita estado;

    @Column(nullable = false)
    private Boolean eliminada = false;
}
