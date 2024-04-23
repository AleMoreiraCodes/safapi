package com.example.safapi.service;

import com.example.safapi.model.entity.Papel;
import com.example.safapi.model.repository.PapelRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class PapelService {
    private PapelRepository repository;

    public PapelService(PapelRepository repository) {
        this.repository = repository;
    }

    public List<Papel> getPapels() {
        return repository.findAll();
    }

    public Optional<Papel> getPapelById(Long id) {
        return repository.findById(id);
    }
}
