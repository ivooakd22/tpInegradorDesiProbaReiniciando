package tuti.desi.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import tuti.desi.enums.EstadoContrato;
 
@Entity
public class HistorialEstadoContrato {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    
    @ManyToOne
    @JoinColumn(name = "contrato_id", nullable = false)
    private Contrato contrato;
 
    @Enumerated(EnumType.STRING)
    private EstadoContrato estado;
 
    private LocalDate fechaCambio;
 
    
    public Long getId() {
        return id;
    }
 
    public void setId(Long id) {
        this.id = id;
    }
 
    public Contrato getContrato() {
        return contrato;
    }
 
    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }
 
    public EstadoContrato getEstado() {
        return estado;
    }
 
    public void setEstado(EstadoContrato estado) {
        this.estado = estado;
    }
 
    public LocalDate getFechaCambio() {
        return fechaCambio;
    }
 
    public void setFechaCambio(LocalDate fechaCambio) {
        this.fechaCambio = fechaCambio;
    }
}