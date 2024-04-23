package com.example.safapi.service;

import com.example.safapi.model.entity.Filme;
import com.example.safapi.model.repository.FilmeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class FilmeService {
    private FilmeRepository repository;

    public FilmeService(FilmeRepository repository) {
        this.repository = repository;
    }

    public List<Filme> getFilmes() {
        return repository.findAll();
    }

    public Optional<Filme> getFilmeById(Long id) {
        return repository.findById(id);
    }
}
