package com.example.safapi.service;

import com.example.safapi.model.entity.Favorito;
import com.example.safapi.model.repository.FavoritoRepository;
import java.util.List;
import java.util.Optional;

public class FavoritoService {
    private FavoritoRepository repository;

    public FavoritoService(FavoritoRepository repository) {
        this.repository = repository;
    }

    public List<Favorito> getFavoritos() {
        return repository.findAll();
    }

    public Optional<Favorito> getFavoritoById(Long id) {
        return repository.findById(id);
    }
}
