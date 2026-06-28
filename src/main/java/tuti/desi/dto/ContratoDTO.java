package tuti.desi.dto;

import java.time.LocalDate;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ContratoDTO {

    private Long id;

    @NotNull(message = "Debe seleccionar una propiedad.")
    private Long propiedadId;

    @NotNull(message = "Debe seleccionar un inquilino.")
    private Long inquilinoId;

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

    // Constructor vacío puro
    public ContratoDTO() {
    }

    // Getters y Setters tradicionales
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        // Corrección por si NetBeans se marea con id/Id
        this.id = id;
    }

    public Long getPropiedadId() {
        return propiedadId;
    }

    public void setPropiedadId(Long propiedadId) {
        this.propiedadId = propiedadId;
    }

    public Long getInquilinoId() {
        return inquilinoId;
    }

    public void setInquilinoId(Long inquilinoId) {
        this.inquilinoId = inquilinoId;
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
}
