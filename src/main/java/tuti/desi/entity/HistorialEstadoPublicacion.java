package tuti.desi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import tuti.desi.enums.EstadoPublicacion;

@Entity
@Table(name = "historial_estados_publicacion")
@NoArgsConstructor
@AllArgsConstructor
public class HistorialEstadoPublicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Publicacion publicacion;

    @Enumerated(EnumType.STRING)
    private EstadoPublicacion estado;

    private LocalDateTime fechaHora;
    
    public HistorialEstadoPublicacion(Long id, Publicacion publicacion, EstadoPublicacion estado, LocalDateTime fechaHora) {
        this.id = id;
        this.publicacion = publicacion;
        this.estado = estado;
        this.fechaHora = fechaHora;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Publicacion getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(Publicacion publicacion) {
        this.publicacion = publicacion;
    }

    public EstadoPublicacion getEstado() {
        return estado;
    }

    public void setEstado(EstadoPublicacion estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechHorao() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }    
}
