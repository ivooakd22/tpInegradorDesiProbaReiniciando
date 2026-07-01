package tuti.desi.dto;

import java.time.LocalDate;
import tuti.desi.enums.EstadoContrato;

public class ContratoDTO {

    private Long id;

    private Long propiedadId;
    private String propiedadDireccion;
    private String propietarioNombre;

    private Long inquilinoId;
    private String inquilinoNombre;

    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    private Integer duracionMeses;
    private Double montoMensual;
    private Integer diaPago;

    private String descripcion;
    private  EstadoContrato estado;

   
    

    private boolean eliminado;

  
    public ContratoDTO() {
    }

    
    public ContratoDTO(Long id, Long propiedadId, String propiedadDireccion, String propietarioNombre,
                       Long inquilinoId, String inquilinoNombre, LocalDate fechaInicio, LocalDate fechaFin,
                       Integer duracionMeses, Double montoMensual, Integer diaPago,
                       String descripcion, EstadoContrato estado, boolean eliminado) {
        this.id = id;
        this.propiedadId = propiedadId;
        this.propiedadDireccion = propiedadDireccion;
        this.propietarioNombre = propietarioNombre;
        this.inquilinoId = inquilinoId;
        this.inquilinoNombre = inquilinoNombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.duracionMeses = duracionMeses;
        this.montoMensual = montoMensual;
        this.diaPago = diaPago;
        this.descripcion = descripcion;
        this.estado = estado;
        this.eliminado = eliminado;
    }

    public EstadoContrato getEstado() {
        return estado;
    }

    public void setEstado(EstadoContrato estado) {
        this.estado = estado;
    }

   
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPropiedadId() { return propiedadId; }
    public void setPropiedadId(Long propiedadId) { this.propiedadId = propiedadId; }

    public String getPropiedadDireccion() { return propiedadDireccion; }
    public void setPropiedadDireccion(String propiedadDireccion) { this.propiedadDireccion = propiedadDireccion; }

    public String getPropietarioNombre() { return propietarioNombre; }
    public void setPropietarioNombre(String propietarioNombre) { this.propietarioNombre = propietarioNombre; }

    public Long getInquilinoId() { return inquilinoId; }
    public void setInquilinoId(Long inquilinoId) { this.inquilinoId = inquilinoId; }

    public String getInquilinoNombre() { return inquilinoNombre; }
    public void setInquilinoNombre(String inquilinoNombre) { this.inquilinoNombre = inquilinoNombre; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }

    public Integer getDuracionMeses() { return duracionMeses; }
    public void setDuracionMeses(Integer duracionMeses) { this.duracionMeses = duracionMeses; }

    public Double getMontoMensual() { return montoMensual; }
    public void setMontoMensual(Double montoMensual) { this.montoMensual = montoMensual; }

    public Integer getDiaPago() { return diaPago; }
    public void setDiaPago(Integer diaPago) { this.diaPago = diaPago; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public boolean isEliminado() { return eliminado; }
    public void setEliminado(boolean eliminado) { this.eliminado = eliminado; }
}
