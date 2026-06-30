package tuti.desi.dto;

import lombok.*;
import tuti.desi.enums.EstadoContrato;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContratoDTO {

    private Long id;

    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    private Double montoMensual;

    private Integer diaPago;

    private EstadoContrato estado;

    private Long propiedadId;

    private Long inquilinoId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public EstadoContrato getEstado() {
		return estado;
	}

	public void setEstado(EstadoContrato estado) {
		this.estado = estado;
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
    
    
}
