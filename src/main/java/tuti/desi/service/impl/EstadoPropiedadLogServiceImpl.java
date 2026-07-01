package tuti.desi.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tuti.desi.entity.EstadoPropiedadLog;
import tuti.desi.entity.Propiedad;
import tuti.desi.enums.EstadoPropiedad;
import tuti.desi.repository.EstadoPropiedadLogRepository;
import tuti.desi.service.EstadoPropiedadLogService;

import java.time.LocalDateTime;

@Service
@Transactional
public class EstadoPropiedadLogServiceImpl implements EstadoPropiedadLogService {

    private final EstadoPropiedadLogRepository repository;

    public EstadoPropiedadLogServiceImpl(EstadoPropiedadLogRepository repository) {
        this.repository = repository;
    }

    @Override
    public void registrar(Propiedad propiedad, EstadoPropiedad estadoAnterior, EstadoPropiedad estadoNuevo) {
        EstadoPropiedadLog log = new EstadoPropiedadLog();
        log.setPropiedad(propiedad);
        log.setFechaCambio(LocalDateTime.now());
        log.setResponsable(null);
        log.setEstadoAnterior(estadoAnterior);
        log.setEstadoNuevo(estadoNuevo);
        repository.save(log);
    }
}
