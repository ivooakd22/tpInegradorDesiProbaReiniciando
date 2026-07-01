package tuti.desi.dto;

import java.time.LocalDate;
import tuti.desi.enums.EstadoFactura;

public class ListarFacturasRequestDTO {

    private EstadoFactura estado;
    private Integer contratoId;
    private Integer propiedadId;
    private Integer inquilinoId;
    private LocalDate fechaVencDesde;
    private LocalDate fechaVencHasta;
    
	public EstadoFactura getEstado() {
		return estado;
	}
	public void setEstado(EstadoFactura estado) {
		this.estado = estado;
	}
	public Integer getContratoId() {
		return contratoId;
	}
	public void setContratoId(Integer contratoId) {
		this.contratoId = contratoId;
	}
	public Integer getPropiedadId() {
		return propiedadId;
	}
	public void setPropiedadId(Integer propiedadId) {
		this.propiedadId = propiedadId;
	}
	public Integer getInquilinoId() {
		return inquilinoId;
	}
	public void setInquilinoId(Integer inquilinoId) {
		this.inquilinoId = inquilinoId;
	}
	public LocalDate getFechaVencDesde() {
		return fechaVencDesde;
	}
	public void setFechaVencDesde(LocalDate fechaVencDesde) {
		this.fechaVencDesde = fechaVencDesde;
	}
	public LocalDate getFechaVencHasta() {
		return fechaVencHasta;
	}
	public void setFechaVencHasta(LocalDate fechaVencHasta) {
		this.fechaVencHasta = fechaVencHasta;
	}
    
    

}
