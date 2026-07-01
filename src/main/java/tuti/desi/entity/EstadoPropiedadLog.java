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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Propiedad getPropiedad() {
		return propiedad;
	}

	public void setPropiedad(Propiedad propiedad) {
		this.propiedad = propiedad;
	}

	public LocalDateTime getFechaCambio() {
		return fechaCambio;
	}

	public void setFechaCambio(LocalDateTime fechaCambio) {
		this.fechaCambio = fechaCambio;
	}

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public EstadoPropiedad getEstadoAnterior() {
		return estadoAnterior;
	}

	public void setEstadoAnterior(EstadoPropiedad estadoAnterior) {
		this.estadoAnterior = estadoAnterior;
	}

	public EstadoPropiedad getEstadoNuevo() {
		return estadoNuevo;
	}

	public void setEstadoNuevo(EstadoPropiedad estadoNuevo) {
		this.estadoNuevo = estadoNuevo;
	}
    
    
    
}
