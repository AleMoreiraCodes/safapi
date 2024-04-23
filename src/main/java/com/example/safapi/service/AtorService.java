package com.example.safapi.service;

import com.example.safapi.model.entity.Ator;
import com.example.safapi.model.repository.AtorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class AtorService {
    private AtorRepository repository;

    public AtorService(AtorRepository repository) {
        this.repository = repository;
    }

    public List<Ator> getAtores() {
        return repository.findAll();
    }

    public Optional<Ator> getAtorById(Long id) {
        return repository.findById(id);
    }
}
