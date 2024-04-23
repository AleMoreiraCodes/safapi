package com.example.safapi.service;

import com.example.safapi.model.entity.Avaliacao;
import com.example.safapi.model.entity.Direcao;
import com.example.safapi.model.repository.AvaliacaoRepository;
import com.example.safapi.model.repository.DirecaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class AvaliacaoService {
    private AvaliacaoRepository repository;

    public AvaliacaoService(AvaliacaoRepository repository) {
        this.repository = repository;
    }

    public List<Avaliacao> getAvaliacoes() {
        return repository.findAll();
    }

    public Optional<Avaliacao> getAvaliacaoById(Long id) {
        return repository.findById(id);
    }
}
