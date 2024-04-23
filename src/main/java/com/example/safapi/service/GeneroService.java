package com.example.safapi.service;

import com.example.safapi.model.entity.Genero;
import com.example.safapi.model.repository.GeneroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class GeneroService {
    private GeneroRepository repository;

    public GeneroService(GeneroRepository repository) {
        this.repository = repository;
    }

    public List<Genero> getGeneros() {
        return repository.findAll();
    }

    public Optional<Genero> getGeneroById(Long id) {
        return repository.findById(id);
    }
}
