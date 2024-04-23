package com.example.safapi.service;

import com.example.safapi.model.entity.Direcao;
import com.example.safapi.model.repository.DirecaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class DirecaoService {
    private DirecaoRepository repository;

    public DirecaoService(DirecaoRepository repository) {
        this.repository = repository;
    }

    public List<Direcao> getDirecoes() {
        return repository.findAll();
    }

    public Optional<Direcao> getDirecaoById(Long id) {
        return repository.findById(id);
    }
}
