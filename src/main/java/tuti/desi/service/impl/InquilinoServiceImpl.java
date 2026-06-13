package tuti.desi.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tuti.desi.entity.Inquilino;
import tuti.desi.repository.InquilinoRepository;
import tuti.desi.service.InquilinoService;

import java.util.List;

@Service
@Transactional
public class InquilinoServiceImpl implements InquilinoService {

    private final InquilinoRepository repository;

    public InquilinoServiceImpl(InquilinoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Inquilino> findAll() {
        return repository.findAll();
    }

    @Override
    public Inquilino findById(Long id) {
        return repository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Inquilino no encontrado con id: " + id));
    }
}
