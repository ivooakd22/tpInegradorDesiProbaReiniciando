package tuti.desi.entity;


import jakarta.persistence.*;
import lombok.*;
import tuti.desi.enums.EstadoPropiedad;
import tuti.desi.enums.TipoPropiedad;

@Entity
@Table(name = "propiedades")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Propiedad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String direccion;

    @Column(length = 100)
    private String barrio;

    @Column(nullable = false, length = 100)
    private String ciudad;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoPropiedad tipo;

    @Column(nullable = false)
    private Integer ambientes;

    @Column(nullable = false)
    private Integer metrosCuadrados;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoPropiedad estado;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "propietario_id", nullable = false)
    private Propietario propietario;
}
