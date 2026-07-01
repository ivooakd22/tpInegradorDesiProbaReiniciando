package tuti.desi.service;

import tuti.desi.entity.Inquilino;

import java.util.List;

public interface InquilinoService {

    List<Inquilino> findAll();

    Inquilino findById(Long id);
}
