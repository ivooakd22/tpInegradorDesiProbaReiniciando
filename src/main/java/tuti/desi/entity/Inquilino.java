
package tuti.desi.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "inquilinos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Inquilino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String apellido;

    @Column(nullable = false, unique = true, length = 20)
    private String dni;

    @Column(length = 20)
    private String telefono;

    @Column(length = 150)
    private String email;
}