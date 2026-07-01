package tuti.desi.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tuti.desi.entity.Propietario;
import tuti.desi.repository.PropietarioRepository;
import tuti.desi.service.PropietarioService;

import java.util.List;

@Service
@Transactional
public class PropietarioServiceImpl implements PropietarioService {

    private final PropietarioRepository repository;

    public PropietarioServiceImpl(PropietarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Propietario> findAll() {
        return repository.findAll();
    }

    @Override
    public Propietario findById(Long id) {
        return repository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Propietario no encontrado con id: " + id));
    }
}
