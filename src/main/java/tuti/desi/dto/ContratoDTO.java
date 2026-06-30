package tuti.desi.dto;

import java.time.LocalDate;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ContratoDTO {

    private Long id;

    @NotNull(message = "Debe seleccionar una propiedad.")
    private Long propiedadId;

    private String propiedadDireccion;

    private String propietarioNombre;

    @NotNull(message = "Debe seleccionar un inquilino.")
    private Long inquilinoId;

    private String inquilinoNombre;

    @NotNull(message = "La fecha de inicio es requerida.")
    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    @NotNull(message = "La duración es requerida.")
    @Min(value = 1, message = "La duración en meses debe ser un número positivo.")
    private Integer duracionMeses;

    @NotNull(message = "El importe mensual es requerido.")
    @Min(value = 1, message = "El importe mensual debe ser un número positivo.")
    private Double montoMensual;

    @NotNull(message = "El día de vencimiento es requerido.")
    @Min(value = 1, message = "El día de vencimiento debe ser entre 1 y 31.")
    private Integer diaPago;

    @NotBlank(message = "La descripción no puede estar vacía.")
    private String descripcion;

    @NotBlank(message = "El estado del contrato es requerido.")
    private String estado;

   public ContratoDTO() {
        
    }

    public ContratoDTO(Long id, Long propiedadId, String propiedadDireccion, String propietarioNombre, Long inquilinoId, String inquilinoNombre, LocalDate fechaInicio, LocalDate fechaFin, Integer duracionMeses, Double montoMensual, Integer diaPago, String descripcion, String estado) {
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
    }

    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPropiedadId() {
        return propiedadId;
    }

    public void setPropiedadId(Long propiedadId) {
        this.propiedadId = propiedadId;
    }

    public String getPropiedadDireccion() {
        return propiedadDireccion;
    }

    public void setPropiedadDireccion(String propiedadDireccion) {
        this.propiedadDireccion = propiedadDireccion;
    }

    public String getPropietarioNombre() {
        return propietarioNombre;
    }

    public void setPropietarioNombre(String propietarioNombre) {
        this.propietarioNombre = propietarioNombre;
    }

    public Long getInquilinoId() {
        return inquilinoId;
    }

    public void setInquilinoId(Long inquilinoId) {
        this.inquilinoId = inquilinoId;
    }

    public String getInquilinoNombre() {
        return inquilinoNombre;
    }

    public void setInquilinoNombre(String inquilinoNombre) {
        this.inquilinoNombre = inquilinoNombre;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getDuracionMeses() {
        return duracionMeses;
    }

    public void setDuracionMeses(Integer duracionMeses) {
        this.duracionMeses = duracionMeses;
    }

    public Double getMontoMensual() {
        return montoMensual;
    }

    public void setMontoMensual(Double montoMensual) {
        this.montoMensual = montoMensual;
    }

    public Integer getDiaPago() {
        return diaPago;
    }

    public void setDiaPago(Integer diaPago) {
        this.diaPago = diaPago;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    private boolean eliminado;

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }
}
