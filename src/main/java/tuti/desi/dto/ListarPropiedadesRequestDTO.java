package tuti.desi.dto;

import lombok.*;
import tuti.desi.enums.EstadoPropiedad;
import tuti.desi.enums.TipoPropiedad;

@Getter
@Setter
public class ListarPropiedadesRequestDTO {
    private EstadoPropiedad estado;
    private TipoPropiedad tipo;
    private String ciudad;
    private String direccion;

    public EstadoPropiedad getEstado() { return estado; }
    public void setEstado(EstadoPropiedad estado) { this.estado = estado; }

    public TipoPropiedad getTipo() { return tipo; }
    public void setTipo(TipoPropiedad tipo) { this.tipo = tipo; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
}
