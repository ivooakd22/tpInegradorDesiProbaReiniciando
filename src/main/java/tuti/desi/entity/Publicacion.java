package tuti.desi.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;
import tuti.desi.enums.EstadoPublicacion;

@Entity
@Table(name = "publicaciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Publicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "propiedad_id", nullable = false)
    private Propiedad propiedad;

    @Column(name = "precio_mensual", nullable = false)
    private Integer precioMensual;

    @Column(nullable = false, length = 200)
    private String condiciones;

    @Column(length = 100, nullable = false)
    private String descripcion;

    @Column(name = "fecha_publicacion", nullable = false)
    private LocalDate fechaPublicacion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoPublicacion estado;

    @Column(nullable = false)
    private Boolean eliminada = false;
}
