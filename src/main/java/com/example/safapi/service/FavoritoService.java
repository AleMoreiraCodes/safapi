package com.example.safapi.service;

import com.example.safapi.exception.RegraNegocioException;
import com.example.safapi.model.entity.Favorito;
import com.example.safapi.model.repository.FavoritoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
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

    @Transactional
    public Favorito salvar(Favorito favorito) {
        validar(favorito);
        return repository.save(favorito);
    }

    @Transactional
    public void excluir(Favorito favorito) {
        Objects.requireNonNull(favorito.getId());
        repository.delete(favorito);
    }

    public void validar(Favorito favorito) {
        if (favorito.getUsuario() == null || favorito.getUsuario().getId() == null || favorito.getUsuario().getId() == 0) {
            throw new RegraNegocioException("Usuário inválido");
        }
        if (favorito.getFilme() == null || favorito.getFilme() .getId() == null || favorito.getFilme().getId() == 0) {
            throw new RegraNegocioException("Filme inválido");
        }
    }
}
