package tuti.desi.service;

import tuti.desi.entity.Propietario;

import java.util.List;

public interface PropietarioService {

    List<Propietario> findAll();

    Propietario findById(Long id);
}
