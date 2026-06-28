package tuti.desi.dto;

import tuti.desi.enums.EstadoPublicacion;

public class ListarPublicacionesRequestDTO {

    private EstadoPublicacion estado;

    private String ciudad;

    private Integer propiedadId;

    private Integer precioMin;

    private Integer precioMax;

    public EstadoPublicacion getEstado() {
        return estado;
    }

    public void setEstado(EstadoPublicacion estado) {
        this.estado = estado;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public Integer getPropiedadId() {
        return propiedadId;
    }

    public void setPropiedadId(Integer propiedadId) {
        this.propiedadId = propiedadId;
    }

    public Integer getPrecioMin() {
        return precioMin;
    }

    public void setPrecioMin(Integer precioMin) {
        this.precioMin = precioMin;
    }

    public Integer getPrecioMax() {
        return precioMax;
    }

    public void setPrecioMax(Integer precioMax) {
        this.precioMax = precioMax;
    }
}
