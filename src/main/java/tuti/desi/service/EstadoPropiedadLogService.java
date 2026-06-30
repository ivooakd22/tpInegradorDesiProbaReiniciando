package tuti.desi.service;

import tuti.desi.entity.Propiedad;
import tuti.desi.enums.EstadoPropiedad;

public interface EstadoPropiedadLogService {

    void registrar(Propiedad propiedad, EstadoPropiedad estadoAnterior, EstadoPropiedad estadoNuevo);
}
