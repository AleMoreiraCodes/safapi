package com.example.safapi.service;

import com.example.safapi.model.entity.Filme;
import com.example.safapi.model.entity.FilmeGenero;
import com.example.safapi.model.repository.FilmeGeneroRepository;
import com.example.safapi.model.repository.FilmeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FilmeGeneroService {

    private FilmeGeneroRepository repository;

    public FilmeGeneroService(FilmeGeneroRepository repository) {
        this.repository = repository;
    }

    public List<FilmeGenero> getFilmeGeneros() {
        return repository.findAll();
    }

    public Optional<FilmeGenero> getFilmeGeneroById(Long id) {
        return repository.findById(id);
    }
}
