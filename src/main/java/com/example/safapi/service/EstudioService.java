package com.example.safapi.service;

import com.example.safapi.model.entity.Estudio;
import com.example.safapi.model.entity.Favorito;
import com.example.safapi.model.repository.EstudioRepository;
import com.example.safapi.model.repository.FavoritoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class EstudioService {
    private EstudioRepository repository;

    public EstudioService(EstudioRepository repository) {
        this.repository = repository;
    }

    public List<Estudio> getEstudios() {
        return repository.findAll();
    }

    public Optional<Estudio> getEstudioById(Long id) {
        return repository.findById(id);
    }
}
