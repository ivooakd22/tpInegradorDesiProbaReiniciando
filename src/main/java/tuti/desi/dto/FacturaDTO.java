package tuti.desi.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import tuti.desi.enums.EstadoFactura;
import tuti.desi.enums.MedioPago;

public class FacturaDTO {

    private Long id;

    private ContratoDTO contrato;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaEmision;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaVencimiento;
    
    private BigDecimal importe;
    
    private EstadoFactura estado;

    private boolean eliminada;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaPago;
    
    private MedioPago medioPago;
    
    private BigDecimal importePagado;
    
    private BigDecimal interes;
    
    private String concepto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ContratoDTO getContrato() {
		return contrato;
	}

	public void setContrato(ContratoDTO contrato) {
		this.contrato = contrato;
	}

	public LocalDate getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(LocalDate fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public LocalDate getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(LocalDate fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public BigDecimal getImporte() {
		return importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	public EstadoFactura getEstado() {
		return estado;
	}

	public void setEstado(EstadoFactura estado) {
		this.estado = estado;
	}

	public boolean getEliminada() {
		return eliminada;
	}

	public void setEliminada(boolean eliminada) {
		this.eliminada = eliminada;
	}

	public LocalDate getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(LocalDate fechaPago) {
		this.fechaPago = fechaPago;
	}

	public MedioPago getMedioPago() {
		return medioPago;
	}

	public void setMedioPago(MedioPago medioPago) {
		this.medioPago = medioPago;
	}

	public BigDecimal getImportePagado() {
		return importePagado;
	}

	public void setImportePagado(BigDecimal importePagado) {
		this.importePagado = importePagado;
	}

	public BigDecimal getInteres() {
		return interes;
	}

	public void setInteres(BigDecimal interes) {
		this.interes = interes;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

    
}
